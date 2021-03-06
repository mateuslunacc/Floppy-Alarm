package projetoes.com.floppyalarm;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import projetoes.com.floppyalarm.utils.PersistenceManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private List<Alarm> alarmList;
    private Alarm alarm;
    private RowAdapter adapter;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

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
            Calendar now = Calendar.getInstance();
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
                                alarm.setRingtoneUriString(Settings.System.DEFAULT_RINGTONE_URI.toString());
                                alarmList.add(alarm);
                                PersistenceManager.saveAlarms(getApplicationContext(), alarmList);
                                adapter.notifyDataSetChanged();
                                Toast toast = Toast.makeText(getApplicationContext(), "Alarm added", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    }, now.get(Calendar.HOUR_OF_DAY) , now.get(Calendar.MINUTE), DateFormat.is24HourFormat(this));
            timePickerDialog.setTitle("Select Time");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_tutorial) {
            Intent intent = new Intent(MainActivity.this, TutorialActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 0) {
            finish();
        }
    }
}
