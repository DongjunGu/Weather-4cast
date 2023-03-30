package com.mobileapp.a4cast.ui.home;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mobileapp.a4cast.DatabaseItem;
import com.mobileapp.a4cast.GlobalData;
import com.mobileapp.a4cast.R;
import com.mobileapp.a4cast.SQLiteManager;
import com.mobileapp.a4cast.databinding.FragmentHomeBinding;
import com.mobileapp.a4cast.ui.home.HomeViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private static final String API_KEY = "dec0f72ce23604612032a38b00466f12";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    private SQLiteManager dbManager;
    private static final int REQUEST_LOCATION_PERMISSION = 1;

    private LocationManager locationManager;
    private LocationListener locationListener;
    double temperature = 0, feelsLike = 0, celsius = 0;
    String mainDescription = "";
    List<DatabaseItem> conditions, temps;
    BottomNavigationView bottomNavigationView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.fragment_home, container, false);
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        bottomNavigationView = getActivity().findViewById(R.id.nav_view);

        //HIDE NAV BAR

        //Setup DB Manager
        dbManager = new SQLiteManager(getContext());
        try {
            dbManager.createDataBase();
        } catch (Exception e) {
            Log.d("DEBUG", "EXCEPTION: " + e);
        }
        try {
            dbManager.openDataBase();
        } catch (SQLException e) {
            Log.d("DEBUG", "EXCEPTION: " + e);
        }
        SQLiteDatabase db1;
        db1 = dbManager.getReadableDatabase();
        //----------

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                getWeatherData(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            getLocation();
        }

        return root;
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    5000, 10, locationListener);
        }
    }

    private void getWeatherData(Location location) {
        bottomNavigationView = getActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.GONE);
        String latitude = String.valueOf(location.getLatitude());
        String longitude = String.valueOf(location.getLongitude());


        String url = BASE_URL + "?lat=" + latitude + "&lon=" + longitude + "&appid=" + API_KEY;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //https://openweathermap.org/weather-conditions <-- List of conditions
                            //SNOW, RAIN, DRIZZLE, THUNDERSTORM, CLEAR, CLOUDS <-- Main conditions
                            String cityName = response.getString("name");
                            JSONObject main = response.getJSONObject("main");
                            temperature = main.getDouble("temp");

                            feelsLike = main.getDouble("feels_like");
                            int humidity = main.getInt("humidity");
                            JSONArray weather = response.getJSONArray("weather");
                            String description = weather.getJSONObject(0).getString("description");
                            String mainDescription = weather.getJSONObject(0).getString("main");
                            binding.textCityName.setText(cityName);
                            //TEMPS MATH
                            temperature = ((temperature - 273.15) * 9 / 5 + 32);
                            feelsLike = ((feelsLike - 273.15) * 9 / 5 + 32);
                            if (!(GlobalData.getInstance().getFahrenheit())) {
                                celsius = ((temperature - 32) * (0.55556));
                                feelsLike = ((feelsLike - 32) * (0.55556));
                                binding.textTemperature.setText(String.format(Locale.getDefault(), "%.2f°C", celsius));
                                binding.textFeelslike.setText(String.format(Locale.getDefault(), "%.2f°C", feelsLike));
                            } else {
                                binding.textTemperature.setText(String.format(Locale.getDefault(), "%.2f°F", temperature));
                                binding.textFeelslike.setText(String.format(Locale.getDefault(), "%.2f°F", feelsLike));
                            }
                            binding.textDescription.setText(description);

                            binding.textHumidity.setText(String.format(Locale.getDefault(), "%d%%", humidity));

                            //Log.d("DEBUG", "JSON Data: " + response.toString(4));
                            conditions = dbManager.getItemsByConditions(mainDescription.toUpperCase());
                            GlobalData.getInstance().setCurrentTemp(temperature);
                            GlobalData.getInstance().setCurrentConditions(mainDescription.toUpperCase());
                            temps = dbManager.getItemsByTemp((int) temperature + GlobalData.getInstance().getPersonalTemp());
                            GlobalData.getInstance().setTemps(temps);
                            GlobalData.getInstance().setConditions(conditions);

                            // weather icon change
                            if (mainDescription.equals("Clear"))
                                binding.descriptionImage.setImageResource(R.drawable.sun);
                            else if (mainDescription.equals("Clouds"))
                                binding.descriptionImage.setImageResource(R.drawable.fewcloud);
                            else if (mainDescription.equals("Drizzle"))
                                binding.descriptionImage.setImageResource(R.drawable.shower);
                            else if (mainDescription.equals("Rain"))
                                binding.descriptionImage.setImageResource(R.drawable.rain);
                            else if (mainDescription.equals("Thunderstorm"))
                                binding.descriptionImage.setImageResource(R.drawable.storm);
                            else if (mainDescription.equals("Snow"))
                                binding.descriptionImage.setImageResource(R.drawable.snow);
                            else if (mainDescription.equals("Mist"))
                                binding.descriptionImage.setImageResource(R.drawable.mist);

                            bottomNavigationView = getActivity().findViewById(R.id.nav_view);
                            bottomNavigationView.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                bottomNavigationView = getActivity().findViewById(R.id.nav_view);
                bottomNavigationView.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Error retrieving weather data", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(binding.getRoot().getContext());
        requestQueue.add(jsonObjectRequest);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

