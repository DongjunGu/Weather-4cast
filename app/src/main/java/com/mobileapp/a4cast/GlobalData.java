package com.mobileapp.a4cast;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class GlobalData {
    private static GlobalData instance = null;
    private boolean fahrenheit = true;
    private int personalTemp = 0;
    private double currentTemp, locationTemp;
    private String currentConditions, locationCity;
    private List<DatabaseItem> conditions;
    private List<DatabaseItem> temps;
    private List<Double> latLong;

    private GlobalData() {
        // private constructor
    }

    public static GlobalData getInstance() {
        if (instance == null) {
            instance = new GlobalData();
        }
        return instance;
    }
    //-----------------FAHRENHEIT-------------------
    public boolean getFahrenheit() {
        return fahrenheit;
    }

    public void setFahrenheit(boolean fOrC) { //True is Fahrenheit || False is Celsius
        this.fahrenheit = fOrC;
        Log.d("DEBUG", "GLOBAL DATA FRAGMENT: Temp Unit Data Saved");
    }
    //-----------------FAHRENHEIT-------------------

    //-----------------PERSONALTEMP-------------------
    public int getPersonalTemp() {
        return personalTemp;
    }

    public void setPersonalTemp(int temp) { //True is Fahrenheit || False is Celsius
        this.personalTemp = temp;
        Log.d("DEBUG", "GLOBAL DATA FRAGMENT: Temp Unit Data Saved");
    }
    //-----------------PERSONALTEMP-------------------

    //-----------------CURRENTTEMP-------------------
    public double getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(double temp) { //True is Fahrenheit || False is Celsius
        this.currentTemp = temp;
        Log.d("DEBUG", "GLOBAL DATA FRAGMENT: Current Temp Unit Data Saved");
    }
    //-----------------CURRENTTEMP-------------------

    //-----------------CURRENTCONDITIONS-------------------
    public String getCurrentConditions() {
        return currentConditions;
    }

    public void setCurrentConditions(String conditions) {
        this.currentConditions = conditions;
        Log.d("DEBUG", "GLOBAL DATA FRAGMENT: Current Conditions Unit Data Saved");
    }
    //-----------------CURRENTCONDITIONS-------------------

    //-----------------CONDITONSLIST-------------------
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
    //-----------------CONDITONSLIST-------------------

    //-----------------TEMPSLIST-------------------
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
    //-----------------TEMPSLIST-------------------

    //-----------------MANUALCITY-------------------
    public List<Double> getLatLong() {
        if (latLong == null) {
            latLong = new ArrayList<>();
        }
        return latLong;
    }

    public void setLatLong(List<Double> latLong) {
        this.latLong = latLong;
        Log.d("DEBUG", "GLOBAL DATA FRAGMENT: LatLong Data Saved");
    }

    public String getLocationCity() {
        return locationCity;
    }

    public void setLocationCity(String locationCity) {
        this.locationCity = locationCity;
        Log.d("DEBUG", "GLOBAL DATA FRAGMENT: ManualCity Data Saved");
    }

    public double getLocationTemp() {
        return locationTemp;
    }

    public void setLocationTemp(double locationTemp) {
        this.locationTemp = locationTemp;
        Log.d("DEBUG", "GLOBAL DATA FRAGMENT: ManualCity Data Saved");
    }
    //-----------------MANUALCITY-------------------
}