package com.josjos.aseloe.api19.Prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;

public class AppPref {

    private SharedPreferences prefs;
    private Preference.OnPreferenceChangeListener context;
    private String DR_Switch = "DR_Switch";
    private String RT_Switch = "RT_Switch";

    public AppPref(Preference.OnPreferenceChangeListener context) {
        prefs = PreferenceManager.getDefaultSharedPreferences((Context) context);
        this.context = context;
    }

    public void setDR_Switch(Boolean input) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(DR_Switch, input);
        editor.commit();
    }

    public Boolean getDR_Switch() {
        return prefs.getBoolean(DR_Switch, false);
    }

    public void setRT_Switch(boolean input){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(RT_Switch, input);
        editor.apply();
    }

    public Boolean getRT_Switch() {
        return prefs.getBoolean(RT_Switch, false);
    }


}
