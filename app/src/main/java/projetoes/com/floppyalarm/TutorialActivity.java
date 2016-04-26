package projetoes.com.floppyalarm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Casa on 22/04/2016.
 */
public class TutorialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
    }
}
