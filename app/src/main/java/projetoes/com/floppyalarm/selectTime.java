package projetoes.com.floppyalarm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TimePicker;

public class selectTime extends AppCompatActivity {

    //java do time picker
    private TimePicker tPkr_TimePicker;
    private Button btn_timeConfirm;
    private Button btn_timeCancel;

    public void timeCancel() {}; //funcao para cancelar a escolha do tempo
    public void timeConfirm() {}; //funcao para confirmar a escolha do tempo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_time);
    }
}
