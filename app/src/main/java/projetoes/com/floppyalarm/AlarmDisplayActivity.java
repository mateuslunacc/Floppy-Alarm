package projetoes.com.floppyalarm;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import projetoes.com.floppyalarm.utils.AlarmServiceManager;
import projetoes.com.floppyalarm.utils.TimeStringFormat;

public class AlarmDisplayActivity extends AppCompatActivity {

    private Button btn_snooze;
    private Button btn_dismiss;
    private TextView txt_alarmTime;
    private TextView txt_alarmLabel;
    private Vibrator vibrator;
    private Alarm alarm;
    private MediaPlayer player;
    private Integer alarmPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //seta janela para aparecer na frente de todas as telas e ativa
        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);

        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        alarmPosition = (Integer) extras.get("alarmPosition");
        alarm = extras.getParcelable("alarm");
        setContentView(R.layout.activity_alarm_display);

        txt_alarmLabel = (TextView) findViewById(R.id.txt_alarmLabel);
        txt_alarmLabel.setText(alarm.getLabel());

        txt_alarmTime = (TextView) findViewById(R.id.txt_alarmTime);
        txt_alarmTime.setText(TimeStringFormat.formataString(alarm.getHour(), alarm.getMinute(), DateFormat.is24HourFormat(getApplicationContext())));
        btn_snooze = (Button) findViewById(R.id.btb_snooze);
        btn_dismiss = (Button) findViewById(R.id.btn_dismiss);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        Uri notification = Uri.parse(alarm.getRingtoneUriString());
        player = MediaPlayer.create(this, notification);
        player.setLooping(true);
        player.start();

        if (alarm.isVibrate()) {
            long[] pattern = {0, 500, 500};
            vibrator.vibrate(pattern, 0);
        }

        btn_snooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmServiceManager.alarmSnoozeStart(AlarmDisplayActivity.this, alarm, alarmPosition);
                stopEvents();
                finish();
            }
        });

        btn_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopEvents();
                finish();
            }
        });

    }


    public void puzzle() { //funcao para ativar o puzzle
        vibrator.cancel();
    }

    private void stopEvents() {
        player.release();
        vibrator.cancel();
    }

    @Override
    protected void onDestroy() {
        stopEvents();
        finish();
        super.onDestroy();
    }
}
