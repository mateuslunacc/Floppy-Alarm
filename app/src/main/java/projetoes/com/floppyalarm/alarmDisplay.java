package projetoes.com.floppyalarm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class alarmDisplay extends AppCompatActivity {

    private Button btn_snooze;
    private Button btn_puzzle;

    private TextView txt_alarmTime;
    private TextView txt_alarmLabel;

    public void snooze() {} //funcao para ativar a soneca
    public void puzzle() {} //funcao para ativar o puzzle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_display);
    }
}
