package projetoes.com.floppyalarm;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import projetoes.com.floppyalarm.utils.AlarmServiceManager;
import projetoes.com.floppyalarm.utils.PersistenceManager;
import projetoes.com.floppyalarm.utils.TimeStringFormat;

import static android.media.RingtoneManager.ACTION_RINGTONE_PICKER;
import static android.media.RingtoneManager.EXTRA_RINGTONE_EXISTING_URI;
import static android.media.RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT;
import static android.media.RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT;
import static android.media.RingtoneManager.EXTRA_RINGTONE_TITLE;
import static android.media.RingtoneManager.EXTRA_RINGTONE_TYPE;
import static android.media.RingtoneManager.TYPE_ALARM;
import static android.media.RingtoneManager.getActualDefaultRingtoneUri;

public class SettingsActivity extends AppCompatActivity {
    private Alarm alarm;
    private List<Alarm> alarmList;
    private List<Integer> repeatDays;
    private Integer alarmPosition;
    private TextView dayText;
    private TextView timeText;
    private Switch isVibrate;
    private TextView ringtone;
    private TextView ringtoneName;
    private String formattedTime;
    private boolean is24h;
    private TextView labelButton;
    private TextView labelText;
    private Switch switchRepeat;
    private TextView repeatDaysText;
    private Switch puzzleSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main_edit_menu);
        //verifica se sistema está no formato 24h
        is24h = DateFormat.is24HourFormat(SettingsActivity.this);

        //recupera lista de alarmes persistida
        alarmList = PersistenceManager.retrieveAlarms(getApplicationContext());

        //carrega alarme passado pelo RowAdapter e sua posição da lista de alarmes
        alarm = intent.getParcelableExtra("alarm");
        repeatDays = alarm.getSelectedDays();
        alarmPosition = (Integer) intent.getExtras().get("alarmPosition");

        //carrega status de vibrate e exibe na activity
        isVibrate = (Switch) findViewById(R.id.swi_vibrate);
        isVibrate.setChecked(alarm.isVibrate());

        //carrega tempo do alarme e exibe na activity
        timeText = (TextView) findViewById(R.id.txt_timeContent);
        formattedTime = TimeStringFormat.formataString(alarm.getHour(), alarm.getMinute(), is24h);
        timeText.setText(formattedTime);

        //carrega nome do Ringtone atual
        Ringtone tempRing = RingtoneManager.getRingtone(this, Uri.parse(alarm.getRingtoneUriString()));
        String title = tempRing.getTitle(this);
        ringtoneName = (TextView) findViewById(R.id.ringtoneName);
        ringtoneName.setText(title);
        ringtone = (TextView) findViewById(R.id.txt_ringtone);

        //carrega nome e caixa para modificar nome
        labelButton = (TextView) findViewById(R.id.txt_label);
        labelText = (TextView) findViewById(R.id.txt_labelview);
        labelText.setText(alarm.getLabel());

        //texto botão para a repetição de dias
        dayText = (TextView) findViewById(R.id.txt_day);
        switchRepeat = (Switch) findViewById(R.id.swi_day);
        repeatDaysText = (TextView) findViewById(R.id.txt_repeatdays);
        switchRepeat.setChecked(alarm.isRepeat());

        //carrega botão de puzzle
        puzzleSwitch = (Switch) findViewById(R.id.swi_puzzle);
        puzzleSwitch.setChecked(alarm.isPuzzle());

        //checa estado dos dias repetidos
        loadRepeatedDays();

        //estado de Vibrate é mudado na activity e salvo no sharedpreferences
        isVibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean status = alarm.isVibrate();
                isVibrate.setChecked(!status);
                alarm.setVibrate(!status);
                alarmList.set(alarmPosition, alarm);
                PersistenceManager.saveAlarms(getApplicationContext(), alarmList);
            }
        });

        //muda para activity de repetição de dias
        dayText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectDayActivity.class);
                intent.putExtra("alarm", alarm);
                intent.putExtra("alarmPosition", alarmPosition);
                startActivity(intent);
            }
        });

        //abre escolha de ringtone
        ringtone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                final Uri currentTone = getActualDefaultRingtoneUri(SettingsActivity.this, TYPE_ALARM);
                Intent intent = new Intent(ACTION_RINGTONE_PICKER);
                intent.putExtra(EXTRA_RINGTONE_TYPE, TYPE_ALARM);
                intent.putExtra(EXTRA_RINGTONE_TITLE, "Select Tone");
                intent.putExtra(EXTRA_RINGTONE_EXISTING_URI, currentTone);
                intent.putExtra(EXTRA_RINGTONE_SHOW_SILENT, false);
                intent.putExtra(EXTRA_RINGTONE_SHOW_DEFAULT, true);
                startActivityForResult(intent, 5);
            }
        });

        //abre timepicker para mudança de horário
        timeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(SettingsActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            boolean firstShown = true;
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                if (firstShown) {
                                    firstShown = false;
                                    alarm.setTime(hourOfDay, minute);
                                    alarm.setActive(true);
                                    alarmList.set(alarmPosition, alarm);
                                    PersistenceManager.saveAlarms(getApplicationContext(), alarmList);
                                    formattedTime = TimeStringFormat.formataString(hourOfDay, minute, is24h);
                                    timeText = (TextView) findViewById(R.id.txt_timeContent);
                                    timeText.setText(formattedTime);
                                    AlarmServiceManager.cancelAlarmService(alarmPosition, SettingsActivity.this, alarm);
                                    AlarmServiceManager.createAlarmService(alarmPosition, SettingsActivity.this, alarm);                                }
                            }
                        }, 0, 0, is24h);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        labelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(SettingsActivity.this);
                final EditText edittext = new EditText(SettingsActivity.this);
                alert.setMessage("Change your alarm label");
                alert.setView(edittext);

                alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String returnedText = edittext.getText().toString();
                        labelText.setText(returnedText);
                        alarm.setLabel(returnedText);
                        alarmList.set(alarmPosition, alarm);
                        PersistenceManager.saveAlarms(getApplicationContext(), alarmList);
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                alert.show();
            }
        });

        switchRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean status = alarm.isRepeat();
                switchRepeat.setChecked(!status);
                alarm.setRepeat(!status);
                alarmList.set(alarmPosition, alarm);
                PersistenceManager.saveAlarms(getApplicationContext(), alarmList);
            }
        });

        puzzleSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean status = alarm.isPuzzle();
                puzzleSwitch.setChecked(!status);
                alarm.setPuzzle(!status);
                alarmList.set(alarmPosition, alarm);
                PersistenceManager.saveAlarms(getApplicationContext(), alarmList);
            }
        });
    }

    //confere o resultado do ringtone e seta no alarme
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            Ringtone ringtone = RingtoneManager.getRingtone(this, uri);
            String title = ringtone.getTitle(this);
            ringtoneName.setText(title);
            alarm.setRingtoneUriString(uri.toString());
            alarmList.set(alarmPosition, alarm);
            PersistenceManager.saveAlarms(getApplicationContext(), alarmList);
        }
    }

    @Override
    protected void onRestart() {
        alarmList = PersistenceManager.retrieveAlarms(getApplicationContext());
        alarm = alarmList.get(alarmPosition);
        loadRepeatedDays();
        super.onRestart();
    }


    //carrega string com os dias selecionados
    private void loadRepeatedDays() {
        Calendar cal = Calendar.getInstance();
        String tempString = "";
        repeatDays = alarm.getSelectedDays();
        if (repeatDays.size() > 0) {
            switchRepeat.setChecked(alarm.isRepeat());
            switchRepeat.setEnabled(true);
            if (repeatDays.size() == 7) {
                tempString = "Full week";
            } else if (repeatDays.size() == 2 && repeatDays.contains(Calendar.SATURDAY) && repeatDays.contains(Calendar.SUNDAY)) {
                tempString = "Weekend";
            } else if (repeatDays.size() == 5 && repeatDays.containsAll(Arrays.asList(Calendar.MONDAY, Calendar.TUESDAY,
                    Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY))) {
                tempString = "Week days";
            } else {
                for (int i : repeatDays) {
                    cal.set(Calendar.DAY_OF_WEEK, i);
                    tempString = tempString.concat(cal.getDisplayName(cal.DAY_OF_WEEK, Calendar.LONG, Locale.US) + " ");
                }
            }
            repeatDaysText.setText(tempString);
        } else {
            repeatDaysText.setText(tempString);
            switchRepeat.setChecked(false);
            switchRepeat.setEnabled(false);
        }
    }

}
