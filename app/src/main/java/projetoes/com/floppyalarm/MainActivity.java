package projetoes.com.floppyalarm;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ArrayList<String> list = new ArrayList<String>();
        list.add("teste");
        list.add("item2");

        RowAdapter adapter = new RowAdapter(list, this);

        ListView listView = (ListView) findViewById(R.id.ListViewId);
        listView.setAdapter(adapter);
    }

    //java do time picker
    private TimePicker tPkr_TimePicker;
    private Button btn_timeConfirm;
    private Button btn_timeCancel;

    public void timeCancel() {}; //funcao para cancelar a escolha do tempo
    public void timeConfirm() {}; //funcao para confirmar a escolha do tempo

}