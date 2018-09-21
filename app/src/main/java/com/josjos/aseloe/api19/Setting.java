package com.josjos.aseloe.api19;
import android.content.Context;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.os.Bundle;
import android.view.MenuItem;
import com.josjos.aseloe.api19.Service.AlarmBrodcastReceiver;

import static com.josjos.aseloe.api19.Service.AlarmBrodcastReceiver.NOTIF_ID_DAILY;
import static com.josjos.aseloe.api19.Service.AlarmBrodcastReceiver.NOTIF_ID_TODAY;


public class Setting extends AppCompatPreferenceActivity  {

    public static AlarmBrodcastReceiver alarmBrodcastReceiver;
    private static String DR_SWITCH = "DailyReminderSwitch";
    private static String RT_SWTICH = "ReleaseTodaySwitch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alarmBrodcastReceiver = new AlarmBrodcastReceiver();
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();
    }

    public static class MainPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        SwitchPreference DrSwitchPreference,RtSwitchPreference;


       public Context context;
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.setting_fr);

             DrSwitchPreference = (SwitchPreference) findPreference(DR_SWITCH);
            DrSwitchPreference.setOnPreferenceChangeListener(this);
             RtSwitchPreference = (SwitchPreference) findPreference(RT_SWTICH);
            RtSwitchPreference.setOnPreferenceChangeListener(this);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object o) {


            if (preference.getKey().equals(DR_SWITCH)) {
                if(DrSwitchPreference.isChecked()){
                    DrSwitchPreference.setChecked(false);
                    alarmBrodcastReceiver.cancelAlarm(getActivity(),NOTIF_ID_DAILY);
                } else {
                    DrSwitchPreference.setChecked(true);
                    alarmBrodcastReceiver.setRepeatingAlarm(getActivity(),NOTIF_ID_DAILY);
                }
            } else if(preference.getKey().equals(RT_SWTICH)){
                if(RtSwitchPreference.isChecked()){
                    RtSwitchPreference.setChecked(false);
                    alarmBrodcastReceiver.cancelAlarm(getActivity(),NOTIF_ID_TODAY);
                } else {
                    RtSwitchPreference.setChecked(true);
                    alarmBrodcastReceiver.setRepeatingAlarm(getActivity(),NOTIF_ID_TODAY);
                }
            }

            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


}
