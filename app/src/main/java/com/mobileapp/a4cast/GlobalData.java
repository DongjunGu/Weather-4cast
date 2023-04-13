package com.mobileapp.a4cast;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class GlobalData {
    private static GlobalData instance = null;
    private boolean fahrenheit = true, manualSwitch = false;
    private int personalTemp = 0;
    private double currentTemp, locationTemp;
    private String currentConditions, locationCity;
    private List<DatabaseItem> conditions;
    private List<DatabaseItem> temps;
    private List<String> manualURLs;

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
    }
    //-----------------FAHRENHEIT-------------------

    //-----------------PERSONALTEMP-------------------
    public int getPersonalTemp() {
        return personalTemp;
    }

    public void setPersonalTemp(int temp) { //True is Fahrenheit || False is Celsius
        this.personalTemp = temp;
    }
    //-----------------PERSONALTEMP-------------------

    //-----------------CURRENTTEMP-------------------
    public double getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(double temp) { //True is Fahrenheit || False is Celsius
        this.currentTemp = temp;
    }
    //-----------------CURRENTTEMP-------------------

    //-----------------CURRENTCONDITIONS-------------------
    public String getCurrentConditions() {
        return currentConditions;
    }

    public void setCurrentConditions(String conditions) {
        this.currentConditions = conditions;
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
    }
    //-----------------TEMPSLIST-------------------

    //-----------------MANUALCITY-------------------
    public boolean getManualSwitch() {
        return manualSwitch;
    }

    public void setManualSwitch(boolean manualSwitch) {
        this.manualSwitch = manualSwitch;
    }

    public String getLocationCity() {
        return locationCity;
    }

    public void setLocationCity(String locationCity) {
        this.locationCity = locationCity;
    }

    public double getLocationTemp() {
        return locationTemp;
    }

    public void setLocationTemp(double locationTemp) {
        this.locationTemp = locationTemp;
    }

    public List<String> getManualURsL() {
        return manualURLs;
    }

    public void setManualURLs(List<String> manualURLs) {
        Log.d("DEBUG", "URLS SET");
        this.manualURLs = manualURLs;
    }
    //-----------------MANUALCITY-------------------
}