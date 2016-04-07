package projetoes.com.floppyalarm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

public class SelectDayActivity extends AppCompatActivity {

    final Integer FULL_WEEK = 7;
    final Integer WEEK_DAYS = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_select_menu);

        RadioGroup dayRadioGroup = (RadioGroup) findViewById(R.id.dayRadioGroup);

        dayRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RelativeLayout layout = (RelativeLayout) findViewById(R.id.layoutDay);
                setDisabled(layout);
                if (checkedId == R.id.everyDayButton) {
                    uncheckAll(layout);
                    selectAllDays(layout);
                } else if (checkedId == R.id.weekDaysButton) {
                    uncheckAll(layout);
                    selectWeekDays(layout);
                } else if (checkedId == R.id.weekendButton) {
                    uncheckAll(layout);
                    selectWeekend(layout);
                } else if (checkedId == R.id.selectDaysButton) {
                    uncheckAll(layout);
                    userSelect(layout);
                }
            }
        });

    }

    private void setDisabled(RelativeLayout layout) {
        for (int i = 0; i < FULL_WEEK; i++) {
            layout.getChildAt(i).setEnabled(false);
        }
    }

    private void uncheckAll(RelativeLayout layout) {
        for (int i = 0; i < FULL_WEEK; i++) {
            ((CheckBox) layout.getChildAt(i)).setChecked(false);
        }
    }

    private void selectAllDays(RelativeLayout layout) {
        for (int i = 0; i < FULL_WEEK; i++) {
            ((CheckBox) layout.getChildAt(i)).setChecked(true);
        }
    }

    private void selectWeekDays(RelativeLayout layout) {
        for (int i = 0; i < WEEK_DAYS; i++) {
            ((CheckBox) layout.getChildAt(i)).setChecked(true);
        }
    }

    private void selectWeekend(RelativeLayout layout) {
        for (int i = 0; i < FULL_WEEK; i++) {
            if (i >= WEEK_DAYS) {
                ((CheckBox) layout.getChildAt(i)).setChecked(true);
            }
        }
    }

    private void userSelect(RelativeLayout layout) {
        for (int i = 0; i < FULL_WEEK; i++) {
            layout.getChildAt(i).setEnabled(true);
        }
    }
}
