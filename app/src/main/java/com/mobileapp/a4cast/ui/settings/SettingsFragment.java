package com.mobileapp.a4cast.ui.settings;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobileapp.a4cast.DatabaseItem; //Database item class
import com.mobileapp.a4cast.GlobalData;
import com.mobileapp.a4cast.SQLiteManager; //Database helper class
import com.mobileapp.a4cast.databinding.FragmentSettingsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.List;


public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private SQLiteManager dbManager;

    List<DatabaseItem> recommendations;
    EditText enterCityTextEdit;
    Button getWeatherButton;
    TextView showRecomTextView,showTempTextView;
    Switch fToCSwitch;

    double temp,feelsLike;
    float pressure;
    int humidity;
    String description,wind,clouds,countryName,cityName;

    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appid = "dec0f72ce23604612032a38b00466f12";
    //https://openweathermap.org/weather-conditions <-- List of conditions
    DecimalFormat df = new DecimalFormat("#.##");

    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // SETUP FOR DATABASE
        dbManager = new SQLiteManager(getContext());
        try { dbManager.createDataBase(); } catch (Exception e) { Log.d("DEBUG", "EXCEPTION: " + e); }
        try { dbManager.openDataBase(); } catch (SQLException e) { Log.d("DEBUG", "EXCEPTION: " + e); }

        SQLiteDatabase db1;
        db1 = dbManager.getReadableDatabase();

        //UI ELEMENTS
        fToCSwitch = binding.fToC;
        getWeatherButton = binding.getWeatherButton;
        enterCityTextEdit = binding.enterCityTextEdit;
        showRecomTextView = binding.showRecomTextView;
        showTempTextView = binding.showTempTextView;

        // GETS ALL DATA AND OUTPUTS TO LOG
        String query = "SELECT * FROM " + SQLiteManager.TABLE_NAME;
        Cursor cursor = db1.rawQuery(query, null);
        StringBuilder stringBuilder = new StringBuilder();
        while (cursor.moveToNext()) {
            String type = cursor.getString(0);
            String name = cursor.getString(1);
            int minTemp = cursor.getInt(2);
            int maxTemp = cursor.getInt(3);
            String conditions = cursor.getString(4);
            String link = cursor.getString(5);

            stringBuilder.append(type).append(", ").append(name).append(", ").append(minTemp)
                    .append(", ").append(maxTemp).append(", ").append(conditions).append(", ").append(link).append("\n");
        }
        Log.d("DEBUG", "DATA: " + "\n" +stringBuilder);

        fToCSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    GlobalData.getInstance().setFahrenheit(false);
                    Log.d("DEBUG", "SETTINGS FRAGMENT: Celsius On");
                } else {
                    GlobalData.getInstance().setFahrenheit(true);
                    Log.d("DEBUG", "SETTINGS FRAGMENT: Fahrenheit On");
                }
            }
        });


        // GETS TEMP DATA AND OUTPUTS TO TEXTVIEW
        getWeatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG", "update button clicked");
                String tempUrl = "";
                if(enterCityTextEdit.getText().toString() != "") {
                    String city = enterCityTextEdit.getText().toString().trim();
                    tempUrl = url + "?q=" + city + "&appid=" + appid;
                }
                StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String output = "";
                        Log.d("response",response);
                        try {
                            //GET WEATHER INFO
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                            description = jsonObjectWeather.getString("description");
                            JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                            temp = (jsonObjectMain.getDouble("temp") - 273.15) * 9/5 + 32;
                            feelsLike = (jsonObjectMain.getDouble("feels_like") - 273.15) * 9/5 +32;
                            pressure = jsonObjectMain.getInt("pressure");
                            humidity = jsonObjectMain.getInt("humidity");
                            JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                            wind = jsonObjectWind.getString("speed");
                            JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
                            clouds = jsonObjectClouds.getString("all");
                            JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                            countryName = jsonObjectSys.getString("country");
                            cityName = jsonResponse.getString("name");
                            showTempTextView.setTextColor(Color.WHITE);
                            showTempTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                            output += "Current weather of " + cityName + " (" + countryName + ")"
                                    + "\n Temp: " + df.format(temp) + " °F"
                                    + "\n Feels Like: " + df.format(feelsLike) + " °F"
                                    + "\n Humidity: " + humidity + "%"
                                    + "\n Description: " + description
                                    + "\n Wind Speed: " + wind + "m/s (meters per second)"
                                    + "\n Cloudiness: " + clouds + "%"
                                    + "\n Pressure: " + pressure + " hPa";
                            showTempTextView.setText(output);

                            //SHOW RECOMMENDATIONS:
                            recommendations = dbManager.getItemsByTemp((int)temp);
                            String temp = "";
                            for(int i = 0; i < recommendations.size(); i++) {
                                Log.d("DEBUG", "RECOMMENDATION " + (i + 1) + ":\n" + recommendations.get(i).printItemInfo());
                                temp = temp + recommendations.get(i).getName() + "\n";
                            }
                            showRecomTextView.setText(temp);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(binding.getRoot().getContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(binding.getRoot().getContext());
                requestQueue.add(stringRequest);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}

