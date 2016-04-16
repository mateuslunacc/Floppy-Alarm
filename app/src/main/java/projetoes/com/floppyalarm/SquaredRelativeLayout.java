package projetoes.com.floppyalarm;

import android.content.Context;
import android.widget.RelativeLayout;

public class SquaredRelativeLayout extends RelativeLayout {

    public SquaredRelativeLayout(Context context){
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if(width > height) {
            super.onMeasure(heightMeasureSpec, heightMeasureSpec);
        } else {
            super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        }
    }
}