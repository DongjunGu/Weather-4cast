/*
 * Weather4cast
 * Robert Russell | Dongjun Gu
 * April/2023
 */
package com.mobileapp.a4cast.ui.home;

import static android.content.ContentValues.TAG;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.AddressComponent;
import com.google.android.libraries.places.api.model.AddressComponents;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mobileapp.a4cast.DatabaseItem;
import com.mobileapp.a4cast.GlobalData;
import com.mobileapp.a4cast.R;
import com.mobileapp.a4cast.SQLiteManager;
import com.mobileapp.a4cast.databinding.FragmentHomeBinding;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class HomeFragment extends Fragment {
    //---BINDING---
    private FragmentHomeBinding binding;
    //---API WEATHER INFO---
    // Constants for the API endpoint, parameters, and units
    private static final String API_KEY = "dec0f72ce23604612032a38b00466f12";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";
    private static final String ONE_CALL_API_URL = "https://api.openweathermap.org/data/2.5";
    private static final String UNITS = "imperial";
    private static final String EXCLUDE = "minutely,daily,alerts";
    //---DATABASE---
    // Instance of SQLiteManager for managing the local database
    private SQLiteManager dbManager;
    //---LOCATION---
    // Constant for requesting location permission
    // ActivityResultLauncher for handling permission requests
    private ActivityResultLauncher<String[]> requestPermissionLauncher;
    // LocationManager and LocationListener for getting the user's location
    public LocationManager locationManager;
    public LocationListener locationListener;
    //---WEATHER DISPLAY VARS---
    // Variables for storing weather data
    double temperature = 0, feelsLike = 0, celsius = 0;
    int humidity;
    DecimalFormat df = new DecimalFormat("#");
    String mainDescription = "", description = "", cityName = "", latitude, longitude = "";
    // Lists for storing weather-related recommendations
    List<DatabaseItem> conditions, temps;
    //---NAV VAR---
    // BottomNavigationView for navigating between app screens
    BottomNavigationView bottomNavigationView;
    //---MANUAL VARS---
    // AutocompleteSupportFragment for allowing manual city selection
    AutocompleteSupportFragment autocompleteFragment;
    // Switch for toggling manual city selection
    SwitchCompat manualCitySwitch;
    // TextViews for displaying selected city and manual city selection hint
    TextView selectedCityText, manualCityText;
    // Boolean for checking if manual city selection is active
    Boolean manual = false;
    // CardView for displaying weather-related recommendations
    CardView cardView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        bottomNavigationView = getActivity().findViewById(R.id.nav_view);

        autocompleteFragment = (AutocompleteSupportFragment) getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        selectedCityText = binding.currentSelectedCity;
        manualCitySwitch = binding.CitySwitch;
        manualCityText = binding.manualCityText;

        autocompleteFragment.getView().setVisibility(View.GONE);
        selectedCityText.setVisibility(View.GONE);
        manualCitySwitch.setVisibility(View.GONE);
        manualCityText.setVisibility(View.GONE);

        cardView = binding.cardView;
        manualCitySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.d("DEBUG", "HOME FRAGMENT: Manual ON");
                    manualCityText.setVisibility(View.VISIBLE);
                    manualCitySwitch.setVisibility(View.VISIBLE);
                    autocompleteFragment.getView().setVisibility(View.VISIBLE);
                    selectedCityText.setVisibility(View.VISIBLE);
                    cityName = null;
                    GlobalData.getInstance().setManualSwitch(true);
                    selectedCityText.setText("Current Selected City: " + GlobalData.getInstance().getLocationCity());
                    manual = true;
                } else {
                    autocompleteFragment.getView().setVisibility(View.GONE);
                    selectedCityText.setVisibility(View.GONE);
                    Log.d("DEBUG", "HOME FRAGMENT: Manual Off");
                    manual = false;
                    GlobalData.getInstance().setManualSwitch(false);
                    locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                    String regularURL = BASE_URL + "?lat=" + latitude + "&lon=" + longitude + "&appid=" + API_KEY;
                    String hourlyURL = ONE_CALL_API_URL + "/onecall?lat=" + latitude + "&lon=" + longitude + "&exclude=" + EXCLUDE + "&units=" + UNITS + "&appid=" + API_KEY;
                    //ADDED
                    getWeatherData(regularURL);
                    getHourlyForecastData(hourlyURL);

                }
            }
        });
        if (autocompleteFragment != null) {
            autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.LAT_LNG, Place.Field.ADDRESS, Place.Field.ADDRESS_COMPONENTS));
            autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(@NonNull Place place) {
                    AddressComponents addressComponents = place.getAddressComponents();

                    String regularURL = BASE_URL + "?lat=" + place.getLatLng().latitude + "&lon=" + place.getLatLng().longitude + "&appid=" + API_KEY;
                    String hourlyURL = ONE_CALL_API_URL + "/onecall?lat=" + place.getLatLng().latitude + "&lon=" + place.getLatLng().longitude + "&exclude=" + EXCLUDE + "&units=" + UNITS + "&appid=" + API_KEY;
                    getWeatherData(regularURL);
                    getHourlyForecastData(hourlyURL);
                    List<String> urls = new ArrayList<String>();
                    urls.add(regularURL);
                    urls.add(hourlyURL);
                    GlobalData.getInstance().setManualURLs(urls);

                    cityName = null;
                    if (addressComponents != null) {
                        for (AddressComponent component : addressComponents.asList()) {
                            List<String> types = component.getTypes();
                            if (types.contains("locality")) {
                                cityName = component.getName();
                                selectedCityText.setText("Current Selected City: " + component.getName());
                                break;
                            }
                        }
                    }
                }

                @Override
                public void onError(@NonNull Status status) {
                    // Handle error
                }
            });
        } else {
            Log.d("DEBUG", "AutocompleteSupportFragment not found");
        }

        // START --- SETUP DATABASE ---
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
        // END --- SETUP DATABASE ---

        // START --- SETUP LOCATION ---
        if (!GlobalData.getInstance().getManualSwitch() || GlobalData.getInstance().getManualURsL() == null) {
            manualCitySwitch.setChecked(false);
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                boolean allPermissionsGranted = true;
                for (Map.Entry<String, Boolean> entry : result.entrySet()) {
                    if (!entry.getValue()) {
                        allPermissionsGranted = false;
                        break;
                    }
                }
                if (allPermissionsGranted) {
                    getLocation();
                } else {
                    Toast.makeText(getActivity(), "Location permission is required for this app", Toast.LENGTH_SHORT).show();
                }
            });
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(new String[]{Manifest.permission.ACCESS_FINE_LOCATION});
            } else {
                getLocation();
            }
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if (!GlobalData.getInstance().getManualSwitch()) {
                        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                        //ADDED
                        latitude = String.valueOf(location.getLatitude());
                        longitude = String.valueOf(location.getLongitude());
                        //Create the URL for the weather data
                        String regularURL = BASE_URL + "?lat=" + latitude + "&lon=" + longitude + "&appid=" + API_KEY;
                        String hourlyURL = ONE_CALL_API_URL + "/onecall?lat=" + latitude + "&lon=" + longitude + "&exclude=" + EXCLUDE + "&units=" + UNITS + "&appid=" + API_KEY;
                        //ADDED
                        getWeatherData(regularURL);
                        getHourlyForecastData(hourlyURL);
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) { /*Nothing*/ }

                @Override
                public void onProviderEnabled(String provider) { /*Nothing*/ }

                @Override
                public void onProviderDisabled(String provider) { /*Nothing*/ }
            };
        } else {
            List<String> urls = GlobalData.getInstance().getManualURsL();
            getWeatherData(urls.get(0));
            getHourlyForecastData(urls.get(1));
        }
        return root;
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            try {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
                bottomNavigationView = getActivity().findViewById(R.id.nav_view);
                bottomNavigationView.setVisibility(View.GONE);
            } catch (Exception e) {
                Log.d("DEBUG", "EXCEPTION: " + e);
            }
        }
    }

    // START --- GET WEATHER DATA ---
    //https://openweathermap.org/weather-conditions <-- List of conditions
    //SNOW, RAIN, DRIZZLE, THUNDERSTORM, CLEAR, CLOUDS <-- Main conditions
    private void getWeatherData(String url) { //Location location

        autocompleteFragment = (AutocompleteSupportFragment) getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        selectedCityText = binding.currentSelectedCity;
        manualCitySwitch = binding.CitySwitch;
        manualCityText = binding.manualCityText;

        manualCitySwitch.setVisibility(View.GONE);
        manualCityText.setVisibility(View.GONE);

        //Make nav bar invisible while weather data is being gathered
        bottomNavigationView = getActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.GONE);

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
                            GlobalData.getInstance().setLocationTemp(temperature);
                            GlobalData.getInstance().setLocationCity(cityName);
                            if (!(GlobalData.getInstance().getFahrenheit())) {
                                celsius = ((temperature - 32) * (0.55556));
                                feelsLike = ((feelsLike - 32) * (0.55556));
                                binding.textTemperature.setText(String.format(Locale.getDefault(), "%.0f°C", celsius));
                                binding.textFeelslike.setText(String.format(Locale.getDefault(), "%.0f°C", feelsLike));
                            } else {
                                binding.textTemperature.setText(String.format(Locale.getDefault(), "%.0f°F", temperature));
                                binding.textFeelslike.setText(String.format(Locale.getDefault(), "%.0f°F", feelsLike));
                            }
                            binding.textDescription.setText(description);
                            binding.textHumidity.setText(String.format(Locale.getDefault(), "%d%%", humidity));
                            conditions = dbManager.getItemsByConditions(mainDescription.toUpperCase(), true);
                            GlobalData.getInstance().setCurrentTemp(temperature);
                            GlobalData.getInstance().setCurrentConditions(mainDescription.toUpperCase());
                            temps = dbManager.getItemsByTemp((int) temperature + GlobalData.getInstance().getPersonalTemp());
                            GlobalData.getInstance().setTemps(temps);
                            GlobalData.getInstance().setConditions(conditions);
                            // weather icon change
                            binding.descriptionImage.setImageResource(getImageConditions(mainDescription));
                            bottomNavigationView = getActivity().findViewById(R.id.nav_view);
                            bottomNavigationView.setVisibility(View.VISIBLE);

                            //autocompleteFragment.getView().setVisibility(View.VISIBLE);
                            //selectedCityText.setVisibility(View.VISIBLE);
                            manualCitySwitch.setVisibility(View.VISIBLE);
                            manualCityText.setVisibility(View.VISIBLE);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                bottomNavigationView = getActivity().findViewById(R.id.nav_view);
                bottomNavigationView.setVisibility(View.VISIBLE);
                manualCitySwitch.setVisibility(View.VISIBLE);
                manualCityText.setVisibility(View.VISIBLE);

                Toast.makeText(getActivity(), "Error retrieving weather data", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(binding.getRoot().getContext());
        requestQueue.add(jsonObjectRequest);
    }
    //END --- GET WEATHER DATA ---

    //START --- HOURLY FORECAST ---
    private void getHourlyForecastData(String url) {
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
                                if (!(GlobalData.getInstance().getFahrenheit())) {
                                    double celsius = ((temp - 32) * (0.55556));
                                    sb1.append(df.format(celsius)).append("°C").append("\n\n");
                                } else {
                                    sb1.append(df.format(temp)).append("°F").append("\n\n");
                                }
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
            case "Haze":
            case "Fog":
                return R.drawable.mist;
            case "Smoke":
                return R.drawable.sky;
            case "Dust":
                return R.drawable.dust;
            case "Sand":
                return R.drawable.sand;
            case "Ash":
                return R.drawable.ash;
            case "Squall":
                return R.drawable.squall;
            case "Tornado":
                return R.drawable.tornado;
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

