package projetoes.com.floppyalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import projetoes.com.floppyalarm.utils.AlarmServiceManager;
import projetoes.com.floppyalarm.utils.PersistenceManager;

public class AlarmReceiver extends BroadcastReceiver {
    private Alarm alarm;
    private Integer alarmPosition;
    private ArrayList<Alarm> alarms;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i;
        //carrega o estado mais atual do alarme
        Bundle extras = intent.getExtras();
        alarmPosition = (Integer) extras.get("alarmPosition");
        alarms = PersistenceManager.retrieveAlarms(context);
        alarm = alarms.get(alarmPosition);
        if (alarm.isPuzzle()) {
            i = new Intent(context, WakeUpActivity.class);
        } else {
            i = new Intent(context, AlarmDisplayActivity.class);
        }
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("alarmPosition", alarmPosition);
        i.putExtra("alarm", alarm);

        //verifica se alarme tem repeticoes e cria novo servico para ativar para o proximo dia
        if (alarm.isRepeat()) {
            AlarmServiceManager.createAlarmService(alarmPosition, context, alarm);
        }
        //se o alarme nao estiver com repeticao ativa ele cancela o alarme e desativa o alarme
        if (!alarm.isRepeat()) {
            AlarmServiceManager.cancelAlarmService(alarmPosition, context, alarm);
            List<Alarm> listAlarm = PersistenceManager.retrieveAlarms(context);
            alarm.setActive(false);
            listAlarm.set(alarmPosition, alarm);
            PersistenceManager.saveAlarms(context, listAlarm);
        }
        AlarmServiceManager.refreshNotifications(context);
        context.startActivity(i);
    }

}
