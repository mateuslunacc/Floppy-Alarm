package projetoes.com.floppyalarm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;


public class TutorialActivity extends AppCompatActivity {

    private ImageView notationImage;
    private ImageView step1Image;
    private ImageView step2Image;
    private ImageView step3Image;
    private Bitmap notationBitmap;
    private Bitmap step1Bitmap;
    private Bitmap step3Bitmap;
    private Bitmap step2Bitmap;

    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_tutorial);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        notationImage = (ImageView) findViewById(R.id.notationImage);
        step1Image = (ImageView) findViewById(R.id.step1Image);
        step2Image = (ImageView) findViewById(R.id.step2Image);
        step3Image = (ImageView) findViewById(R.id.step3Image);

        notationBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.notation);
        step1Bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.step1);
        step2Bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.step2);
        step3Bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.step3);


        notationImage.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.notation));
        step1Image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.step1));
        step2Image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.step2));
        step3Image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.step2));
    }

    @Override
    protected void onDestroy() {
        notationBitmap.recycle();
        step1Bitmap.recycle();
        step2Bitmap.recycle();
        step3Bitmap.recycle();

        notationImage.setImageBitmap(null);
        step1Image.setImageBitmap(null);
        step2Image.setImageBitmap(null);
        step3Image.setImageBitmap(null);

        super.onDestroy();
    }
}
