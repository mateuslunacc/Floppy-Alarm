package projetoes.com.floppyalarm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import projetoes.com.floppyalarm.utils.PersistenceManager;
import projetoes.com.floppyalarm.utils.TimeStringFormat;

public class SettingsActivity extends AppCompatActivity {
    private Alarm alarm;
    private List<Alarm> alarmList;
    private Integer alarmPosition;
    private TextView dayText;
    private TextView timeText;
    private Switch isVibrate;
    private TextView ringtone;
    private TextView ringtoneName;
    private String formattedTime;
    private boolean is24h;
    private TextView labelButton;
    private TextView labelText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main_edit_menu);
        //verifica se sistema está no formato 24h
        is24h = DateFormat.is24HourFormat(SettingsActivity.this);

        //recupera lista de alarmes persistida
        alarmList = PersistenceManager.retrieveAlarms(getApplicationContext());

        //carrega alarme passado pelo RowAdapter e sua posição da lista de alarmes
        alarm = intent.getParcelableExtra("alarm");
        alarmPosition = (Integer) intent.getExtras().get("alarmPosition");

        //carrega status de vibrate e exibe na activity
        isVibrate = (Switch) findViewById(R.id.swi_vibrate);
        isVibrate.setChecked(alarm.isVibrate());

        //carrega tempo do alarme e exibe na activity
        timeText = (TextView) findViewById(R.id.txt_timeContent);
        formattedTime = TimeStringFormat.formataString(alarm.getHour(), alarm.getMinute(), is24h);
        timeText.setText(formattedTime);

        //carrega nome e caixa para modificar nome
        labelButton = (TextView) findViewById(R.id.txt_label);
        labelText = (TextView) findViewById(R.id.txt_labelview);
        labelText.setText(alarm.getLabel());

        //texto botão para a repetição de dias
        dayText = (TextView) findViewById(R.id.txt_day);

        //estado de Vibrate é mudado na activity e salvo no sharedpreferences
        isVibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean status = alarm.isVibrate();
                isVibrate.setChecked(!status);
                alarm.setVibrate(!status);
                alarmList.set(alarmPosition, alarm);
                PersistenceManager.saveAlarms(getApplicationContext(), alarmList);
            }
        });

        //muda para activity de repetição de dias
        dayText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectDayActivity.class);
                intent.putExtra("alarm", alarm);
                intent.putExtra("alarmPosition", alarmPosition);
                startActivity(intent);
            }
        });

        labelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(SettingsActivity.this);
                final EditText edittext = new EditText(SettingsActivity.this);
                alert.setMessage("Change your alarm label");
                alert.setView(edittext);

                alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String returnedText = edittext.getText().toString();
                        labelText.setText(returnedText);
                        alarm.setLabel(returnedText);
                        alarmList.set(alarmPosition, alarm);
                        PersistenceManager.saveAlarms(getApplicationContext(), alarmList);
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                alert.show();
            }
        });
    }

    @Override
    protected void onRestart() {
        alarmList = PersistenceManager.retrieveAlarms(getApplicationContext());
        alarm = alarmList.get(alarmPosition);
        super.onRestart();
    }

}
