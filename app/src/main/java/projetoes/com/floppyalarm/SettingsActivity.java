package projetoes.com.floppyalarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import projetoes.com.floppyalarm.utils.PersistenceManager;

public class SettingsActivity extends AppCompatActivity {
    private Alarm alarm;
    private ArrayList<Alarm> alarmList;
    private Integer alarmPosition;
    private TextView dayText;
    private TextView timeText;
    private Switch isVibrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main_edit_menu);

        //recupera lista de alarmes persistida
        alarmList = PersistenceManager.retrieveAlarms(getApplicationContext());

        //carrega alarme passado pelo RowAdapter e sua posição da lista de alarmes
        alarm = intent.getParcelableExtra("alarm");
        alarmPosition = (Integer) intent.getExtras().get("alarmPosition");

        //carrega status de vibrate e exibe na activity
        isVibrate = (Switch) findViewById(R.id.swi_vibrate);
        isVibrate.setChecked(alarm.isVibrate());

        //carrega tempo do alarme e exibe na activity
        int hour = alarm.getHour();
        int minute = alarm.getMinute();
        String time = String.format("%02d:%02d", hour, minute);
        timeText = (TextView) findViewById(R.id.txt_timeContent);
        timeText.setText(time);

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
                startActivity(intent);
            }
        });
    }
}
