package projetoes.com.floppyalarm;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;


import com.robotium.solo.Solo;


public class SettingsActivityTest extends ActivityInstrumentationTestCase2<MainActivity>{

    private Solo solo;

    public SettingsActivityTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    private void addAlarm() {
        solo.clickOnView(solo.getView(R.id.fab));
        solo.setTimePicker(0, 0, 0);
        solo.clickOnText("OK");
        solo.waitForDialogToClose();
    }

    private void changeActivity() {
        ListView listView = (ListView)solo.getView(R.id.ListViewId);
        View view = listView.getChildAt(0);
        solo.clickOnView(view);
    }

    public void testChangeOfActivity() {
        addAlarm();
        changeActivity();
        solo.assertCurrentActivity("current activity", SettingsActivity.class);
    }

    public void testVibrateSwitch() {
        addAlarm();
        changeActivity();
        Switch switchActive =(Switch) solo.getView(R.id.swi_vibrate);
        boolean statusAnterior = switchActive.isChecked();
        solo.clickOnView(switchActive);
        solo.waitForView(R.id.swi_vibrate);
        assertEquals(switchActive.isChecked(), !statusAnterior);
    }

    public void testPuzzleSwitch() {
        addAlarm();
        changeActivity();
        Switch switchActive =(Switch) solo.getView(R.id.swi_snooze);
        boolean statusAnterior = switchActive.isChecked();
        solo.clickOnView(switchActive);
        solo.waitForView(R.id.swi_vibrate);
        assertEquals(switchActive.isChecked(), !statusAnterior);
    }

    public void testLabelChange() {
        addAlarm();
        changeActivity();
        TextView view =(TextView) solo.getView(R.id.txt_label);
        solo.clickLongOnView(view);
        solo.waitForDialogToOpen();
        solo.enterText(0, "Label Teste");
        solo.clickOnText("Confirm");
        solo.waitForDialogToClose();
        solo.searchText("Label Teste");
    }
}