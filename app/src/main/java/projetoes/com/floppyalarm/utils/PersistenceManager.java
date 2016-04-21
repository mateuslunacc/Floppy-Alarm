package projetoes.com.floppyalarm.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import projetoes.com.floppyalarm.Alarm;

public class PersistenceManager {
    private static final String PREFS_NAME = "ALARM_APP";
    private static final String ALARM_LIST = "Alarm_List";

    //salvar alarmes no sharedpreferences
    public static void saveAlarms(Context context, List<Alarm> alarmList) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(alarmList);
        editor.putString(ALARM_LIST, jsonFavorites);
        editor.apply();
    }

    //recuperar alarmes no sharedpreferences
    public static ArrayList<Alarm> retrieveAlarms(Context context) {
        SharedPreferences settings;
        List<Alarm> alarms;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(ALARM_LIST)) {
            String jsonFavorites = settings.getString(ALARM_LIST, null);
            Gson gson = new Gson();
            Alarm[] alarmsList = gson.fromJson(jsonFavorites,
                    Alarm[].class);

            alarms = Arrays.asList(alarmsList);
            alarms = new ArrayList<>(alarms);
        } else
            return new ArrayList<>();
        return (ArrayList<Alarm>) alarms;
    }

}
