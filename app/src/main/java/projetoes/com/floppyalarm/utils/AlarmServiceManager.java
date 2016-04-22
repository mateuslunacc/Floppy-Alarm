package projetoes.com.floppyalarm.utils;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import projetoes.com.floppyalarm.Alarm;
import projetoes.com.floppyalarm.AlarmReceiver;
import projetoes.com.floppyalarm.MainActivity;
import projetoes.com.floppyalarm.R;

public class  AlarmServiceManager {

    private static final int SNOOZE_MINUTES = 5;
    private static Notification notification;
    private static NotificationManager notificationManager;

    public static void createAlarmService(int requestCode, Context context, Alarm alarm) {
        refreshNotifications(context);
        //prepara alarm e intent
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("alarm", alarm);
        intent.putExtra("alarmPosition", requestCode);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //checa dia atual e dias do alarme em ordem
        List<Integer> sortedDays = alarm.getSelectedDays();
        Collections.sort(sortedDays);

        Calendar now = Calendar.getInstance();
        Calendar settedCalendar = Calendar.getInstance();

        //checa se alarme é repetido e seta para o proximo dia ativo
        if (alarm.isRepeat()) {
            for (Integer day : sortedDays) {
                settedCalendar.set(Calendar.DAY_OF_WEEK, day);
                settedCalendar.set(Calendar.HOUR_OF_DAY, alarm.getHour());
                settedCalendar.set(Calendar.MINUTE, alarm.getMinute());
                settedCalendar.set(Calendar.SECOND, 0);
                if (settedCalendar.getTimeInMillis() > now.getTimeInMillis()) {
                    break;
                }
                if (day == 7) {
                    settedCalendar.set(Calendar.DAY_OF_YEAR, settedCalendar.get(Calendar.DAY_OF_YEAR) + 1);
                }
            }
            if (now.getTimeInMillis() > settedCalendar.getTimeInMillis()) {
                settedCalendar.set(Calendar.DAY_OF_WEEK, sortedDays.get(0));
                settedCalendar.set(Calendar.WEEK_OF_YEAR, settedCalendar.get(Calendar.WEEK_OF_YEAR) + 1);
            }
        } else {
            settedCalendar.set(Calendar.HOUR_OF_DAY, alarm.getHour());
            settedCalendar.set(Calendar.MINUTE, alarm.getMinute());
            settedCalendar.set(Calendar.SECOND, 0);
            if (now.getTimeInMillis() > settedCalendar.getTimeInMillis()) {
                settedCalendar.set(Calendar.DAY_OF_YEAR, settedCalendar.get(Calendar.DAY_OF_YEAR) + 1);
            }
        }

        //checa versao android e seta o comando correto
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmMgr.setExact(AlarmManager.RTC_WAKEUP, settedCalendar.getTimeInMillis(), alarmIntent);
        } else {
            alarmMgr.set(AlarmManager.RTC_WAKEUP, settedCalendar.getTimeInMillis(), alarmIntent);
        }
        String diffString = formattedDiference(settedCalendar);
        Toast toast = Toast.makeText(context, diffString, Toast.LENGTH_SHORT);
        showNotification(context);
        toast.show();
    }

    //formata string que mostra quando o alame será ativado
    private static String formattedDiference(Calendar settedCalendar) {
        Calendar now = Calendar.getInstance();

        long diffInMilli = settedCalendar.getTimeInMillis() - now.getTimeInMillis();

        TimeUnit timeUnit = TimeUnit.SECONDS;
        long difference = timeUnit.convert(diffInMilli, TimeUnit.MILLISECONDS);

        long days = difference / (24 * 60 * 60);
        long restDays = difference - (days * 24 * 60 * 60);
        long hrs = restDays / (60 * 60);
        long restHours = restDays - (hrs * 60 * 60);
        long min = restHours / 60;

        String diffString = "Alarm setted to ";
        if (days > 0) {
            diffString = diffString.concat(String.valueOf(days) + " day(s) ");
        }
        if (hrs > 0) {
            diffString = diffString.concat(String.valueOf(hrs) + " hour(s) ");
        }
        if (min > 0 ) {
            diffString = diffString.concat(String.valueOf(min) + " minute(s)");
        } if (min <= 0 ) {
            diffString = diffString.concat("less than one minute");
        }
        return diffString + " from now.";
    }

    //cancela um servico de alarme
    public static void cancelAlarmService(int requestCode, Context context, Alarm alarm) {
        refreshNotifications(context);
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("alarm", alarm);
        intent.putExtra("alarmPosition", requestCode);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmMgr.cancel(alarmIntent);
    }

    //inicia um alarme de soneca com 5 minutos a mais
    public static void alarmSnoozeStart(Context context, Alarm alarm, int position) {
        Alarm snoozeAlarm = new Alarm();
        snoozeAlarm.setTime(alarm.getHour(), alarm.getMinute() + SNOOZE_MINUTES);
        createAlarmService(position, context, snoozeAlarm);
    }

    private static void showNotification(Context context) {
        PendingIntent pi = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);
        notification = new NotificationCompat.Builder(context)
                .setTicker("Alarm")
                .setSmallIcon(R.drawable.ic_notification_icon)
                .setContentTitle("Floppy Alarm")
                .setContentIntent(pi)
                .setOngoing(true)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("There is at least one active alarm right now. Click here to check."))
                .build();
        notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }

    public static void refreshNotifications(Context context) {
        boolean activeAlarm = false;
        List<Alarm> alarmList = PersistenceManager.retrieveAlarms(context);
        for (Alarm a : alarmList) {
            if(a.isActive()) {
                activeAlarm = true;
            }
        }
        if (!activeAlarm) {
            notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            notificationManager.cancel(0);
        }
    }

}
