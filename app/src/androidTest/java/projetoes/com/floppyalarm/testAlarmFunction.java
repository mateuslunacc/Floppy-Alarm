package projetoes.com.floppyalarm;

import android.app.AlarmManager;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Switch;

import com.robotium.solo.Solo;

import java.util.Calendar;

import projetoes.com.floppyalarm.utils.AlarmServiceManager;

public class testAlarmFunction extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;

    public testAlarmFunction() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testAlarmNoRepeat() {
        ListView listView =(ListView)solo.getView(R.id.ListViewId);
        Calendar cal = Calendar.getInstance();
        solo.clickOnView(solo.getView(R.id.fab));
        solo.setTimePicker(0, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE) + 1);
        solo.clickOnText("OK");
        solo.waitForDialogToClose();

        View view= listView.getChildAt(listView.getChildCount() - 1);
        Switch switchActive =(Switch) view.findViewById(R.id.swi_active);
        solo.clickOnView(switchActive);

        int SAFE_MINUTE_DURATION = 60000;

        solo.waitForActivity(AlarmDisplayActivity.class, SAFE_MINUTE_DURATION);
        solo.finishOpenedActivities();
    }
}