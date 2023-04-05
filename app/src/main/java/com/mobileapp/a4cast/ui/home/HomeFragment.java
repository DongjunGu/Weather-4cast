package com.mobileapp.a4cast.ui.home;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mobileapp.a4cast.DatabaseItem;
import com.mobileapp.a4cast.GlobalData;
import com.mobileapp.a4cast.MainActivity;
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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class HomeFragment extends Fragment {
    //---BINDING---
    private FragmentHomeBinding binding;
    //---API WEATHER INFO---
    private static final String API_KEY = "dec0f72ce23604612032a38b00466f12";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";
    private static final String ONE_CALL_API_URL = "https://api.openweathermap.org/data/2.5";
    private static final String UNITS = "imperial";
    private static final String EXCLUDE = "minutely,daily,alerts";
    //---DATABASE---
    private SQLiteManager dbManager;
    //---LOCATION---
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private ActivityResultLauncher<String[]> requestPermissionLauncher;
    public LocationManager locationManager;
    public LocationListener locationListener;
    //---WEATHER DISPLAY VARS---
    double temperature = 0, feelsLike = 0, celsius = 0;
    int humidity;
    DecimalFormat df = new DecimalFormat("#.##");
    String mainDescription = "", description = "", cityName = "";
    List<DatabaseItem> conditions, temps;
    List<DatabaseItem> tempRecommendations, activityReco, foodReco, clothingReco;
    //---NAV VAR---
    BottomNavigationView bottomNavigationView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        bottomNavigationView = getActivity().findViewById(R.id.nav_view);

        // START --- MANUAL LOCATION ---
        binding.getWeatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityName = binding.enterCityTextEdit.getText().toString().trim();
                if (cityName.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter a city name", Toast.LENGTH_SHORT).show();
                    return;
                }

                String url = BASE_URL + "?q=" + cityName + "&appid=" + API_KEY;

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject main = response.getJSONObject("main");
                                    double temperature = main.getDouble("temp");
                                    double feelsLike = main.getDouble("feels_like");
                                    temperature = ((temperature - 273.15) * 9 / 5 + 32);
                                    feelsLike = ((feelsLike - 273.15) * 9 / 5 + 32);
                                    int humidity = main.getInt("humidity");
                                    JSONArray weather = response.getJSONArray("weather");
                                    String description = weather.getJSONObject(0).getString("description");
                                    String mainDescription = weather.getJSONObject(0).getString("main");

                                    String output =
                                            "Temperature: " + df.format(temperature) + "°F" + "\n"
                                            + "Feels like: " + df.format(feelsLike) + "°F" + "\n"
                                            + "Humidity: " + humidity + "%" + "\n"
                                            + "Description: " + description + "\n"
                                            + "Main condition: " + mainDescription;                                            ;

                                    binding.showWeatherData.setText(output);

                                    activityReco = new ArrayList<>();
                                    clothingReco = new ArrayList<>();
                                    foodReco = new ArrayList<>();
                                    tempRecommendations = dbManager.getItemsByTemp((int) temperature);
                                    Log.d("DEBUG", "SETTINGS FRAGMENT: LENGTH: " + tempRecommendations.size());
                                    for (int i = 0; i < tempRecommendations.size(); i++) {
                                        if (tempRecommendations.get(i).getType().equals("ACTIVITY")) {
                                            activityReco.add(tempRecommendations.get(i));
                                        } else if (tempRecommendations.get(i).getType().equals("CLOTHING")) {
                                            clothingReco.add(tempRecommendations.get(i));
                                        } else if (tempRecommendations.get(i).getType().equals("FOOD")) {
                                            foodReco.add(tempRecommendations.get(i));
                                        }
                                    }

                                    String temp = "-----ACTIVITIES-----\n";
                                    for (int i = 0; i < activityReco.size(); i++) {
                                        temp = temp + "\t" + activityReco.get(i).getName() + "\n";
                                    }
                                    temp += "-----FOOD-----\n";
                                    for (int i = 0; i < foodReco.size(); i++) {
                                        temp = temp + "\t" + foodReco.get(i).getName() + "\n";
                                    }
                                    temp += "-----CLOTHING-----\n";
                                    for (int i = 0; i < clothingReco.size(); i++) {
                                        temp = temp + "\t" + clothingReco.get(i).getName() + "\n";
                                    }
                                    binding.showRecom.setText(temp);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(binding.getRoot().getContext());
                requestQueue.add(jsonObjectRequest);
            }
        });
        // END --- MANUAL LOCATION ---

        // START --- SETUP DATABASE ---
        dbManager = new SQLiteManager(getContext());
        try { dbManager.createDataBase(); } catch (Exception e) { Log.d("DEBUG", "EXCEPTION: " + e); }
        try { dbManager.openDataBase(); } catch (SQLException e) { Log.d("DEBUG", "EXCEPTION: " + e); }
        SQLiteDatabase db1 = dbManager.getReadableDatabase();
        // END --- SETUP DATABASE ---

        // START --- SETUP LOCATION ---
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
            boolean allPermissionsGranted = true;
            for (Map.Entry<String, Boolean> entry : result.entrySet()) {
                if (!entry.getValue()) { allPermissionsGranted = false; break; }
            }
            if (allPermissionsGranted) { getLocation(); }
            else { Toast.makeText(getActivity(), "Location permission is required for this app", Toast.LENGTH_SHORT).show(); }
        });
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}); }
        else { getLocation(); }
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                getWeatherData(location);
                getHourlyForecastData(location);
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) { /*Nothing*/ }
            @Override
            public void onProviderEnabled(String provider) { /*Nothing*/ }
            @Override
            public void onProviderDisabled(String provider) { /*Nothing*/ }
        };
        // END --- SETUP LOCATION ---

        return root;
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            try { locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
            } catch (Exception e) { Log.d("DEBUG", "EXCEPTION: " + e); }
        }
    }

    // START --- GET WEATHER DATA ---
    //https://openweathermap.org/weather-conditions <-- List of conditions
    //SNOW, RAIN, DRIZZLE, THUNDERSTORM, CLEAR, CLOUDS <-- Main conditions
    private void getWeatherData(Location location) {
        //Make nav bar invisible while weather data is being gathered
        bottomNavigationView = getActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.GONE);
        //Get the long and lat of the location
        String latitude = String.valueOf(location.getLatitude());
        String longitude = String.valueOf(location.getLongitude());
        //Create the URL for the weather data
        String url = BASE_URL + "?lat=" + latitude + "&lon=" + longitude + "&appid=" + API_KEY;
        //Request weather data
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            cityName = response.getString("name");
                            JSONObject main = response.getJSONObject("main");
                            temperature = main.getDouble("temp");
                            feelsLike = main.getDouble("feels_like");
                            humidity = main.getInt("humidity");
                            JSONArray weather = response.getJSONArray("weather");
                            description = weather.getJSONObject(0).getString("description");
                            mainDescription = weather.getJSONObject(0).getString("main");
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
                            conditions = dbManager.getItemsByConditions(mainDescription.toUpperCase());
                            GlobalData.getInstance().setCurrentTemp(temperature);
                            GlobalData.getInstance().setCurrentConditions(mainDescription.toUpperCase());
                            temps = dbManager.getItemsByTemp((int) temperature + GlobalData.getInstance().getPersonalTemp());
                            GlobalData.getInstance().setTemps(temps);
                            GlobalData.getInstance().setConditions(conditions);
                            // weather icon change
                            binding.descriptionImage.setImageResource(getImageConditions(mainDescription));
                            bottomNavigationView = getActivity().findViewById(R.id.nav_view);
                            bottomNavigationView.setVisibility(View.VISIBLE);
                        } catch (JSONException e) { e.printStackTrace(); }
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
    //END --- GET WEATHER DATA ---

    //START --- HOURLY FORECAST ---
    private void getHourlyForecastData(Location location) {
        String latitude = String.valueOf(location.getLatitude());
        String longitude = String.valueOf(location.getLongitude());
        String url = ONE_CALL_API_URL + "/onecall?lat=" + latitude + "&lon=" + longitude + "&exclude=" + EXCLUDE + "&units=" + UNITS + "&appid=" + API_KEY;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray hourlyForecast = response.getJSONArray("hourly");
                            StringBuilder sb = new StringBuilder();
                            StringBuilder sb1 = new StringBuilder();
                            for (int i = 0; i < hourlyForecast.length() - 43; i++) {
                                JSONObject hourlyData = hourlyForecast.getJSONObject(i);
                                long timestamp = hourlyData.getLong("dt");
                                Date date = new Date(timestamp * 1000);
                                DateFormat format = new SimpleDateFormat("h:mm a", Locale.getDefault());
                                format.setTimeZone(TimeZone.getDefault());
                                String formattedDate = format.format(date);
                                double temp = hourlyData.getDouble("temp");
                                String main = hourlyData.getJSONArray("weather").getJSONObject(0).getString("main");
                                //sb.append(formattedDate).append(" ").append(main).append("                  ").append(temp).append("°F").append("\n\n");
                                sb.append(formattedDate).append("\n\n");
                                sb1.append(temp).append("°F").append("\n\n");

                               //Set Hourly Images
                                binding.hourlyImage1.setImageResource(getImageConditions(main));
                                binding.hourlyImage2.setImageResource(getImageConditions(main));
                                binding.hourlyImage3.setImageResource(getImageConditions(main));
                                binding.hourlyImage4.setImageResource(getImageConditions(main));
                                binding.hourlyImage5.setImageResource(getImageConditions(main));

                            }
                            binding.hourlyHour.setText(sb.toString());
                            binding.hourlyTemp.setText(sb1.toString());
                        } catch (JSONException e) {
                            Log.e(TAG, "Error parsing JSON response for hourly forecast", e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error getting hourly forecast data", error);
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);
    }
    //END --- HOURLY FORECAST ---

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public int getImageConditions(String cond) {
        switch (cond) {
            case "Clear":
                return R.drawable.sun;
            case "Clouds":
                return R.drawable.fewcloud;
            case "Drizzle":
                return R.drawable.shower;
            case "Rain":
                return R.drawable.rain;
            case "Thunderstorm":
                return R.drawable.storm;
            case "Snow":
                return R.drawable.snow;
            case "Mist":
                return R.drawable.mist;
        }
        return 0;
    }

    @Override
    public void onStart() {
        super.onStart();
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (locationManager != null && locationListener != null) {
            locationManager.removeUpdates(locationListener);
        }
    }
}

