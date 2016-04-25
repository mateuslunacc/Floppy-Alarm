package projetoes.com.floppyalarm;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.format.DateFormat;
import android.view.MotionEvent;
import android.view.View;
import android.support.v7.widget.GridLayout;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashSet;

import projetoes.com.floppyalarm.puzzle.floppy.FloppyCube;
import projetoes.com.floppyalarm.puzzle.floppy.Orientation;
import projetoes.com.floppyalarm.utils.TimeStringFormat;

/*
* Activity that shows the floppy puzzle.
* By Mateus Luna
*
* */

public class WakeUpActivity extends Activity {

    private final static Integer DIMENSION = 3;

    private TextView alarmTime;
    private TextView alarmLabel;

    private FloppyCube cube;
    private GridLayout faceColors;
    private LinearLayout topSideColors;
    private LinearLayout bottomSideColors;
    private LinearLayout rightSideColors;
    private LinearLayout leftSideColors;
    private HashSet<View> layerU;
    private HashSet<View> layerE;
    private HashSet<View> layerD;
    private HashSet<View> layerL;
    private HashSet<View> layerM;
    private HashSet<View> layerR;
    private HashSet<View> piecesTouched;
    private Alarm alarm;
    private MediaPlayer player;
    private Vibrator vibrator;

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
        alarm = extras.getParcelable("alarm");
        setContentView(R.layout.activity_wake_up);

        //Alarm data
        alarmTime = (TextView) findViewById(R.id.puzzleAlarmLabel);
        alarmLabel = (TextView) findViewById(R.id.puzzleTimeLabel);

        //Set alarm data
        alarmTime.setText(TimeStringFormat.formataString(alarm.getHour(), alarm.getMinute(), DateFormat.is24HourFormat(getApplicationContext())));
        alarmLabel.setText(alarm.getLabel());

        //Puzzle objects
        this.cube = new FloppyCube(
                R.drawable.white_sticker,
                R.drawable.yellow_sticker,
                R.drawable.green_sticker,
                R.drawable.blue_sticker,
                R.drawable.orange_sticker,
                R.drawable.red_sticker);

        //Faces
        this.faceColors = (GridLayout) findViewById(R.id.faceColors);
        this.topSideColors = (LinearLayout) findViewById(R.id.topSideColors);
        this.bottomSideColors = (LinearLayout) findViewById(R.id.bottomSideColors);
        this.rightSideColors = (LinearLayout) findViewById(R.id.rightSideColors);
        this.leftSideColors = (LinearLayout) findViewById(R.id.leftSideColors);

        //Layers
        this.layerU = new HashSet<>();
        layerU.add(findViewById(R.id.faceColor00));
        layerU.add(findViewById(R.id.faceColor01));
        layerU.add(findViewById(R.id.faceColor02));

        this.layerE = new HashSet<>();
        layerE.add(findViewById(R.id.faceColor10));
        layerE.add(findViewById(R.id.faceColor11));
        layerE.add(findViewById(R.id.faceColor12));

        this.layerD = new HashSet<>();
        layerD.add(findViewById(R.id.faceColor20));
        layerD.add(findViewById(R.id.faceColor21));
        layerD.add(findViewById(R.id.faceColor22));

        this.layerL = new HashSet<>();
        layerL.add(findViewById(R.id.faceColor00));
        layerL.add(findViewById(R.id.faceColor10));
        layerL.add(findViewById(R.id.faceColor20));

        this.layerM = new HashSet<>();
        layerM.add(findViewById(R.id.faceColor01));
        layerM.add(findViewById(R.id.faceColor11));
        layerM.add(findViewById(R.id.faceColor21));

        this.layerR = new HashSet<>();
        layerR.add(findViewById(R.id.faceColor02));
        layerR.add(findViewById(R.id.faceColor12));
        layerR.add(findViewById(R.id.faceColor22));

        //Initializing puzzle solution
        this.piecesTouched = new HashSet<>();
        this.cube.scramble(15);
        this.updateFloppyPuzzle();

        this.faceColors.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        for (int position = 0; position < faceColors.getChildCount(); position++) {
                            View view = faceColors.getChildAt(position);
                            Rect outRect = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
                            if (outRect.contains((int) event.getX(), (int) event.getY())) {
                                if (!piecesTouched.contains(view)) {
                                    piecesTouched.add(view);
                                }
                            }
                        }
                        return true;
                    default:
                        cube.turn(getMove(piecesTouched));
                        updateFloppyPuzzle();
                        piecesTouched.clear();
                        if (cube.isSolved()) {
                            Toast finishMessage = Toast.makeText(getApplicationContext(), "Nice Job!", Toast.LENGTH_SHORT);
                            finishMessage.show();
                            onDestroy();
                        }
                        return false;
                }
            }
        });

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        Uri notification = Uri.parse(alarm.getRingtoneUriString());
        player = MediaPlayer.create(this, notification);
        player.setLooping(true);
        player.start();

        if (alarm.isVibrate()) {
            long[] pattern = {0, 500, 500};
            vibrator.vibrate(pattern, 0);
        }
    }

    private Character getMove(HashSet<View> pieces) {
        Character move = 'X';
        if (pieces.size() == DIMENSION) {
            if (layerU.equals(pieces)) {
                move = 'U';
            } else if (layerE.equals(pieces)) {
                move = 'E';
            } else if (layerD.equals(pieces)) {
                move = 'D';
            } else if (layerL.equals(pieces)) {
                move = 'L';
            } else if (layerM.equals(pieces)) {
                move = 'M';
            } else if (layerR.equals(pieces)) {
                move = 'R';
            }
        }
        return move;
    }

    private void updateFloppyPuzzle() {
        int position;
        for(int row = 0; row < DIMENSION; row++) {
            for(int column = 0; column < DIMENSION; column++) {
                position = row * 3 + column;
                View v = faceColors.getChildAt(position);
                v.setBackgroundResource(cube.getPiece(row, column).getFaceColor());
            }
        }
        for (int topSideColor = 0; topSideColor < DIMENSION; topSideColor++) {
            topSideColors.getChildAt(topSideColor+1).setBackgroundResource(
                    cube.getPiece(0, topSideColor).getSideColor(Orientation.TOP));
        }
        for (int bottomSideColor = 0; bottomSideColor < DIMENSION; bottomSideColor++) {
            bottomSideColors.getChildAt(bottomSideColor+1).setBackgroundResource(
                    cube.getPiece(2, bottomSideColor).getSideColor(Orientation.BOTTOM));
        }
        for (int rightSideColor = 0; rightSideColor < DIMENSION; rightSideColor++) {
            rightSideColors.getChildAt(rightSideColor).setBackgroundResource(
                    cube.getPiece(rightSideColor, 2).getSideColor(Orientation.RIGHT));
        }
        for (int leftSideColor = 0; leftSideColor < DIMENSION; leftSideColor++) {
            leftSideColors.getChildAt(leftSideColor).setBackgroundResource(
                    cube.getPiece(leftSideColor, 0).getSideColor(Orientation.LEFT));
        }
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