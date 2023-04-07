package com.mobileapp.a4cast.ui.settings;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.AddressComponent;
import com.google.android.libraries.places.api.model.AddressComponents;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.mobileapp.a4cast.DatabaseItem; //Database item class
import com.mobileapp.a4cast.GlobalData;
import com.mobileapp.a4cast.R;
import com.mobileapp.a4cast.SQLiteManager; //Database helper class
import com.mobileapp.a4cast.databinding.FragmentSettingsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private SQLiteManager dbManager;

    List<DatabaseItem> tempRecommendations, activityReco, foodReco, clothingReco;
    EditText enterCityTextEdit;
    Button getWeatherButton;
    TextView showRecom, showWeatherData, personalTempText, selectedCityText;
    Switch fToCSwitch, manualCitySwitch;
    SeekBar hotColdSeekBar;
    AutocompleteSupportFragment autocompleteFragment;

    double temp;
    int seekBarInt;
    String descriptionMain, manualCity;

    private static final String API_KEY = "dec0f72ce23604612032a38b00466f12";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";
    //https://openweathermap.org/weather-conditions <-- List of conditions
    DecimalFormat df = new DecimalFormat("#.##");

    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        hotColdSeekBar = (SeekBar) binding.hotColdSeekBar;
        hotColdSeekBar.setMax(40);
        hotColdSeekBar.setProgress(20);
        personalTempText = binding.personalTempTextView;
        autocompleteFragment = (AutocompleteSupportFragment) getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        selectedCityText = binding.currentSelectedCity;

        if(!GlobalData.getInstance().getManualCity()) {
            autocompleteFragment.getView().setVisibility(View.GONE);
            selectedCityText.setVisibility(View.GONE);
        } else {
            selectedCityText.setVisibility(View.VISIBLE);
            selectedCityText.setText("Current Selected City: " + GlobalData.getInstance().getLocationManual());
            autocompleteFragment.getView().setVisibility(View.VISIBLE);
        }


        // SETUP FOR DATABASE
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

        //UI ELEMENTS
        fToCSwitch = binding.fToC;
        manualCitySwitch = binding.CitySwitch;


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
        Log.d("DEBUG", "DATA: " + "\n" + stringBuilder);

        hotColdSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress - 20;
                Log.d("DEBUG", "SETTING SEEKBAR: " + progress);
                if(progress >= 0) {
                    personalTempText.setText("+"+Integer.toString(progress));
                } else {
                    personalTempText.setText(Integer.toString(progress));
                }

                GlobalData.getInstance().setPersonalTemp(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

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


        manualCitySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    GlobalData.getInstance().setManualCity(true);
                    autocompleteFragment.getView().setVisibility(View.VISIBLE);
                    selectedCityText.setVisibility(View.VISIBLE);
                    selectedCityText.setText("Current Selected City: " + GlobalData.getInstance().getLocationManual());
                    Log.d("DEBUG", "SETTINGS FRAGMENT: Manual On");
                } else {
                    GlobalData.getInstance().setManualCity(false);

                    autocompleteFragment.getView().setVisibility(View.GONE);
                    selectedCityText.setVisibility(View.GONE);
                    Log.d("DEBUG", "SETTINGS FRAGMENT: Manual Off");
                }
            }
        });
        if (autocompleteFragment != null) {
            autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.LAT_LNG, Place.Field.ADDRESS, Place.Field.ADDRESS_COMPONENTS));
            autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(@NonNull Place place) {
                    //Log.d("DEBUG", "SETTINGS FRAGMENT: PLACE: " + place.getLatLng().latitude);
                    AddressComponents addressComponents = place.getAddressComponents();
                    List<Double> latLngList = new ArrayList<>();
                    latLngList.add(place.getLatLng().latitude);
                    latLngList.add(place.getLatLng().longitude);
                    GlobalData.getInstance().setLatLong(latLngList);
                    manualCity = null;

                    if (addressComponents != null) {
                        for (AddressComponent component : addressComponents.asList()) {
                            List<String> types = component.getTypes();
                            if (types.contains("locality")) {
                                manualCity = component.getName();
                                selectedCityText.setText("Current Selected City: " + component.getName());
                                GlobalData.getInstance().setLocationManual(component.getName());
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
        // GETS TEMP DATA AND OUTPUTS TO TEXTVIEW
        return root;
    }

    @Override
    public void onDestroyView() {
        List<DatabaseItem> conditions;
        List<DatabaseItem> temps;
        conditions = dbManager.getItemsByConditions(GlobalData.getInstance().getCurrentConditions());
        temps = dbManager.getItemsByTemp((int) GlobalData.getInstance().getCurrentTemp() + GlobalData.getInstance().getPersonalTemp());
        GlobalData.getInstance().setConditions(conditions);
        GlobalData.getInstance().setTemps(temps);
        //if (autocompleteFragment != null) {
        //    requireActivity().getSupportFragmentManager().beginTransaction().remove(autocompleteFragment).commit();
        //}
        super.onDestroyView();
        binding = null;
    }
}

