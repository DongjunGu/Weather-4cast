package com.mobileapp.a4cast;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class GlobalData {
    private static GlobalData instance = null;
    private List<DatabaseItem> extra;
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

    public List<DatabaseItem> getExtra() {
        if (extra == null) {
            extra = new ArrayList<>();
        }
        return extra;
    }

    public void setExtra(List<DatabaseItem> items) {
        this.extra = items;
        Log.d("DEBUG", "GLOBAL DATA FRAGMENT: Extra Data Saved");
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