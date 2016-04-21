package projetoes.com.floppyalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import projetoes.com.floppyalarm.utils.AlarmServiceManager;
import projetoes.com.floppyalarm.utils.PersistenceManager;

public class BootReceiver extends BroadcastReceiver {

    //seta todos os alarmes no boot do celular
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            ArrayList<Alarm> alarmList = PersistenceManager.retrieveAlarms(context);
            for (Alarm a : alarmList) {
                if (a.isActive()) {
                    AlarmServiceManager.createAlarmService(alarmList.indexOf(a), context, a);
                }
            }

        }
    }
}