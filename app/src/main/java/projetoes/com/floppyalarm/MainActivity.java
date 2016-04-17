package projetoes.com.floppyalarm;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;

import projetoes.com.floppyalarm.utils.PersistenceManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ArrayList<Alarm> alarmList;
    private Alarm alarm;
    private RowAdapter adapter;
    private FloatingActionButton fab;
    private boolean is24h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        is24h = DateFormat.is24HourFormat(MainActivity.this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        loadAdapter();
    }

    @Override
    public void onClick(View v) {

        //adiciona novo alarme usando o Floating Button
        if(v == fab) {
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        boolean firstShown = true;
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            if(firstShown) {
                                firstShown = false;
                                alarm = new Alarm();
                                alarm.setTime(hourOfDay, minute);
                                alarmList.add(alarm);
                                PersistenceManager.saveAlarms(getApplicationContext(), alarmList);
                                adapter.notifyDataSetChanged();
                                Toast toast = Toast.makeText(getApplicationContext(), "Alarm added", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    }, 0, 0, is24h);
            timePickerDialog.show();
        }
    }

    //quando a activity é recarregada
    @Override
    protected void onRestart() {
        loadAdapter();
        super.onRestart();
    }

    //recarregar adapter
    private void loadAdapter() {
        alarmList = PersistenceManager.retrieveAlarms(getApplicationContext());
        adapter = new RowAdapter(alarmList, this);
        ListView listView = (ListView) findViewById(R.id.ListViewId);
        listView.setAdapter(adapter);
    }

}
