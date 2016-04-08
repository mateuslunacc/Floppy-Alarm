package projetoes.com.floppyalarm;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ArrayList<Alarm> list;
    private Alarm alarmList;
    private RowAdapter adapter;
    private FloatingActionButton fab;
    private static final String PREFS_NAME = "ALARM_APP";
    private static final String ALARM_LIST = "Alarm_List";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //recuperar alarmes
        list = retrieveAlarms(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        adapter = new RowAdapter(list, this);
        ListView listView = (ListView) findViewById(R.id.ListViewId);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if(v == fab) {
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            alarmList = new Alarm();
                            alarmList.setTime(hourOfDay, minute);
                            list.add(alarmList);
                            adapter.notifyDataSetChanged();
                            Toast toast = Toast.makeText(getApplicationContext(), "Alarm added", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }, 0, 0, true);
            timePickerDialog.show();
        }
    }

    //salvar alarmes no sharedpreferences
    private void saveAlarms(Context context, List<Alarm> alarmList) {
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
    private ArrayList<Alarm> retrieveAlarms(Context context) {
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
            alarms = new ArrayList<Alarm>(alarms);
        } else
            return new ArrayList<Alarm>();
        return (ArrayList<Alarm>) alarms;
    }

    //salver alarmes quanto tela estiver desativada
    @Override
    protected void onPause() {
        super.onPause();
        saveAlarms(getApplicationContext(), list);
    }
}