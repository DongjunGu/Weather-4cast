/*
 * Weather4cast
 * Robert Russell | Dongjun Gu
 * April/2023
 */
package com.mobileapp.a4cast.ui.settings;

import android.annotation.SuppressLint;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.mobileapp.a4cast.DatabaseItem; //Database item class
import com.mobileapp.a4cast.GlobalData;
import com.mobileapp.a4cast.SQLiteManager; //Database helper class
import com.mobileapp.a4cast.databinding.FragmentSettingsBinding;

import java.util.List;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private SQLiteManager dbManager;

    TextView personalTempText;
    SwitchCompat fToCSwitch;
    SeekBar hotColdSeekBar;

    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        hotColdSeekBar = (SeekBar) binding.hotColdSeekBar;
        hotColdSeekBar.setMax(40);
        hotColdSeekBar.setProgress(20);
        personalTempText = binding.personalTempTextView;

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

        //UI ELEMENTS
        fToCSwitch = binding.fToC;

        hotColdSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress - 20;
                Log.d("DEBUG", "SETTING SEEKBAR: " + progress);
                if (progress >= 0) {
                    personalTempText.setText("+" + Integer.toString(progress) + "℉");
                } else {
                    personalTempText.setText(Integer.toString(progress) + "℉");
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

        return root;
    }

    @Override
    public void onDestroyView() {
        List<DatabaseItem> conditions;
        List<DatabaseItem> temps;
        conditions = dbManager.getItemsByConditions(GlobalData.getInstance().getCurrentConditions(), true);
        temps = dbManager.getItemsByTemp((int) GlobalData.getInstance().getCurrentTemp() + GlobalData.getInstance().getPersonalTemp());
        GlobalData.getInstance().setConditions(conditions);
        GlobalData.getInstance().setTemps(temps);

        super.onDestroyView();
        binding = null;
    }
}

