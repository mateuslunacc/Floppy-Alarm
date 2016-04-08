package projetoes.com.floppyalarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Alarm alarm = (Alarm) intent.getParcelableExtra("alarm");

        int hour = alarm.getHour();
        int minute = alarm.getMinute();
        String time = String.format("%02d:%02d", hour, minute);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main_edit_menu);

        TextView dayText = (TextView) findViewById(R.id.txt_day);
        TextView timeText = (TextView) findViewById(R.id.txt_timeContent);
        timeText.setText(time);


        dayText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectDayActivity.class);
                startActivity(intent);
            }
        });
    }
}
