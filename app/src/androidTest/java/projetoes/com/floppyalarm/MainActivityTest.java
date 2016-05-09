package projetoes.com.floppyalarm;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ListView;
import android.widget.Switch;

import com.robotium.solo.Solo;


public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity>{

    private Solo solo;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }


    public void testAddAlarm() {
        ListView listView = (ListView)solo.getView(R.id.ListViewId);
        int pastCount = listView.getChildCount();
        solo.clickOnView(solo.getView(R.id.fab));
        solo.setTimePicker(0, 0, 0);
        solo.clickOnText("OK");
        solo.waitForDialogToClose();
        solo.searchText("Alarm added");
        assertEquals(listView.getChildCount(), pastCount + 1);
    }

   public void testChangeSwitchActive() {
        ListView listView =(ListView)solo.getView(R.id.ListViewId);
        View view= listView.getChildAt(0);
        Switch switchActive =(Switch) view.findViewById(R.id.swi_active);
        boolean statusAnterior = switchActive.isChecked();
        solo.clickOnView(switchActive);
        solo.waitForText("Alarm setted to");
        assertEquals(switchActive.isChecked(), !statusAnterior);
    }

    public void testDeleteAlarm() {
        ListView listView = (ListView)solo.getView(R.id.ListViewId);
        int pastCount = listView.getChildCount();
        View view = listView.getChildAt(0);
        solo.clickLongOnView(view);
        solo.waitForDialogToOpen();
        solo.clickOnText("Yes");
        solo.waitForDialogToClose();
        solo.searchText("Alarm deleted");
        assertEquals(listView.getChildCount(), pastCount - 1);
    }

}
