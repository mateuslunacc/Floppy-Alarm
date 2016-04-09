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
    private TextView dayText;
    private TextView timeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main_edit_menu);

        //carrega alarme passado pelo RowAdapter e sua posição da lista de alarmes
        alarm = intent.getParcelableExtra("alarm");

        //carrega tempo do alarme e exibe na activity
        int hour = alarm.getHour();
        int minute = alarm.getMinute();
        String time = String.format("%02d:%02d", hour, minute);
        timeText = (TextView) findViewById(R.id.txt_timeContent);
        timeText.setText(time);

        //texto botão para a repetição de dias
        dayText = (TextView) findViewById(R.id.txt_day);

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
