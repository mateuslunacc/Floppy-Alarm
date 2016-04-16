package projetoes.com.floppyalarm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.widget.ImageView;
import android.widget.TextView;

import projetoes.com.floppyalarm.puzzle.floppy.FaceColor;
import projetoes.com.floppyalarm.puzzle.floppy.FloppyCube;
import projetoes.com.floppyalarm.puzzle.floppy.SideColor;

/*
* Activity that shows the floppy puzzle.
* By Mateus Luna
*
* */

public class WakeUpActivity extends AppCompatActivity {

    private final static Integer DIMENSION = 3;

    private TextView alarmTime;
    private TextView alarmLabel;

    private FloppyCube cube;
    private ImageView[][] faceColors;
    private ImageView[] topSideColors;
    private ImageView[] bottomSideColors;
    private ImageView[] rightSideColors;
    private ImageView[] leftSideColors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wake_up);

        alarmTime = (TextView) findViewById(R.id.txt_alarmTime);
        alarmLabel = (TextView) findViewById(R.id.txt_alarmLabel);

        this.cube = new FloppyCube(FaceColor.WHITE,
                SideColor.GREEEN, SideColor.BLUE, SideColor.ORANGE, SideColor.RED);

        this.faceColors = new ImageView[DIMENSION][DIMENSION];
        faceColors[0][0] = (ImageView) findViewById(R.id.faceColor00);
        faceColors[0][1] = (ImageView) findViewById(R.id.faceColor01);
        faceColors[0][2] = (ImageView) findViewById(R.id.faceColor02);
        faceColors[1][0] = (ImageView) findViewById(R.id.faceColor10);
        faceColors[1][1] = (ImageView) findViewById(R.id.faceColor11);
        faceColors[1][2] = (ImageView) findViewById(R.id.faceColor12);
        faceColors[2][0] = (ImageView) findViewById(R.id.faceColor20);
        faceColors[2][1] = (ImageView) findViewById(R.id.faceColor21);
        faceColors[2][2] = (ImageView) findViewById(R.id.faceColor22);

        this.topSideColors = new ImageView[DIMENSION];
        topSideColors[0] = (ImageView) findViewById(R.id.topSideColor0);
        topSideColors[1] = (ImageView) findViewById(R.id.topSideColor1);
        topSideColors[2] = (ImageView) findViewById(R.id.topSideColor2);

        this.bottomSideColors = new ImageView[DIMENSION];
        bottomSideColors[0] = (ImageView) findViewById(R.id.bottomSideColor0);
        bottomSideColors[1] = (ImageView) findViewById(R.id.bottomSideColor1);
        bottomSideColors[2] = (ImageView) findViewById(R.id.bottomSideColor2);

        this.rightSideColors = new ImageView[DIMENSION];
        rightSideColors[0] = (ImageView) findViewById(R.id.rightSideColor0);
        rightSideColors[1] = (ImageView) findViewById(R.id.rightSideColor1);
        rightSideColors[2] = (ImageView) findViewById(R.id.rightSideColor2);

        this.leftSideColors = new ImageView[DIMENSION];
        leftSideColors[0] = (ImageView) findViewById(R.id.leftSideColor0);
        leftSideColors[1] = (ImageView) findViewById(R.id.leftSideColor1);
        leftSideColors[2] = (ImageView) findViewById(R.id.leftSideColor2);
    }


}
