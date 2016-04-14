package projetoes.com.floppyalarm;

import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class alarmDisplay extends AppCompatActivity {

    private Button btn_snooze;
    private Button btn_puzzle;
    private TextView txt_alarmTime;
    private TextView txt_alarmLabel;
    private Vibrator vibrator;
    private Alarm alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_display);

        //se o modo vibrar estiver ativo
        if (alarm.isVibrate()) {
            vibrator = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);
            // Start without a delay
            // Vibrate for 500 milliseconds
            // Sleep for 500 milliseconds
            long[] pattern = {0, 500, 500}; //definir esse padrao
            // The '0' here means to repeat indefinitely
            // '0' is actually the index at which the pattern keeps repeating from (the start)
            // To repeat the pattern from any other point, you could increase the index, e.g. '1'
            vibrator.vibrate(pattern, 0);
        }
    }

    public void snooze() { //funcao para ativar a soneca
        //ativa a soneca pelo tempo determinado
        vibrator.cancel();
    }

    public void puzzle() { //funcao para ativar o puzzle
        //chama a activity do puzzle
        vibrator.cancel();
    }
}
