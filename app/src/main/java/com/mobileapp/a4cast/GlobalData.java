package com.mobileapp.a4cast;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class GlobalData {
    private static GlobalData instance = null;
    private boolean fahrenheit = true;
    private List<DatabaseItem> conditions;
    private List<DatabaseItem> temps;

    private GlobalData() {
        // private constructor
    }

    public static GlobalData getInstance() {
        if (instance == null) {
            instance = new GlobalData();
        }
        return instance;
    }

    public boolean getFahrenheit() {
        return fahrenheit;
    }

    public void setFahrenheit(boolean fOrC) { //True is Fahrenheit || False is Celsius
        this.fahrenheit = fOrC;
        Log.d("DEBUG", "GLOBAL DATA FRAGMENT: Temp Unit Data Saved");
    }

    public List<DatabaseItem> getConditions() {
        if (conditions == null) {
            conditions = new ArrayList<>();
        }
        return conditions;
    }

    public void setConditions(List<DatabaseItem> conditions) {

        this.conditions = conditions;
        Log.d("DEBUG", "GLOBAL DATA FRAGMENT: Conditions Data Saved");
    }

    public List<DatabaseItem> getTemps() {
        if (temps == null) {
            temps = new ArrayList<>();
        }
        return temps;
    }

    public void setTemps(List<DatabaseItem> temps) {
        this.temps = temps;
        Log.d("DEBUG", "GLOBAL DATA FRAGMENT: Temperature Data Saved");
    }
}