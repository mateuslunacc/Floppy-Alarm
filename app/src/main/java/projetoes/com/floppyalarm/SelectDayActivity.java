package projetoes.com.floppyalarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import projetoes.com.floppyalarm.utils.PersistenceManager;

public class SelectDayActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    private static final int SUNDAY = 0;
    private static final int MONDAY = 1;
    private static final int SATURDAY = 6;
    private RelativeLayout layout;
    private final Integer FULL_WEEK = 7;
    private final Integer WEEK_DAYS = 5;
    private Alarm alarm;
    private Integer alarmPosition;
    private List<Integer> selectedDays;
    private List<Alarm> alarmList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_select_menu);

        layout = (RelativeLayout) findViewById(R.id.layoutDay);

        //carrega lista de alarmes, alarme passado e sua posição
        alarmList = PersistenceManager.retrieveAlarms(getApplicationContext());
        alarm = intent.getParcelableExtra("alarm");
        alarmPosition = (Integer) intent.getExtras().get("alarmPosition");

        //carrega lista de dias do alarme
        selectedDays = alarm.getSelectedDays();

        initializeCheckboxesListener();
        checkLayout();

        RadioGroup dayRadioGroup = (RadioGroup) findViewById(R.id.dayRadioGroup);

        //para cada opcao ele carrega os dias ja selecionados
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
                    userSelect(layout);
                }
                alarm.setSelectedDays(selectedDays);
                alarmList.set(alarmPosition, alarm);
                PersistenceManager.saveAlarms(getApplicationContext(), alarmList);
            }
        });
    }

    //carrega os listeners dos checkboxes
    private void initializeCheckboxesListener() {
        for (int i = 0; i < FULL_WEEK; i++) {
            if (layout.getChildAt(i) instanceof CheckBox) {
                CheckBox check = (CheckBox) layout.getChildAt(i);
                check.setOnCheckedChangeListener(this);
            }
        }
    }

    //carrega layout inicial
    private void checkLayout() {
        if(selectedDays.size() == FULL_WEEK) {
            uncheckAll(layout);
            RadioButton btn = (RadioButton) findViewById(R.id.everyDayButton);
            btn.setChecked(true);
            selectAllDays(layout);
        } else if (selectedDays.size() == FULL_WEEK-WEEK_DAYS && selectedDays.containsAll(Arrays.asList(FULL_WEEK, FULL_WEEK))) {
            uncheckAll(layout);
            RadioButton btn = (RadioButton) findViewById(R.id.weekendButton);
            btn.setChecked(true);
            selectWeekend(layout);
        } else if(selectedDays.size() == WEEK_DAYS && !selectedDays.contains(FULL_WEEK)) {
            uncheckAll(layout);
            RadioButton btn = (RadioButton) findViewById(R.id.weekDaysButton);
            btn.setChecked(true);
            selectWeekDays(layout);
        } else {
            userSelect(layout);
            RadioButton btn = (RadioButton) findViewById(R.id.selectDaysButton);
            btn.setChecked(true);
            List<Integer> newList = selectedDays;
            uncheckAll(layout);
            for (int i = 1; i <= FULL_WEEK; i++) {
                if(newList.contains(i)) {
                    ((CheckBox) layout.getChildAt(i-1)).setChecked(true);
                }
            }
        }
    }

    //desativa todas checkboxes
    private void setDisabled(RelativeLayout layout) {
        for (int i = 0; i < FULL_WEEK; i++) {
            layout.getChildAt(i).setEnabled(false);
        }
    }

    //desmarca todas checkboxes
    private void uncheckAll(RelativeLayout layout) {
        selectedDays = new ArrayList<>();
        for (int i = 0; i < FULL_WEEK; i++) {
            ((CheckBox) layout.getChildAt(i)).setChecked(false);
        }
    }

    //seleciona todos os dias da semana
    private void selectAllDays(RelativeLayout layout) {
        for (int i = 0; i < FULL_WEEK; i++) {
            ((CheckBox) layout.getChildAt(i)).setChecked(true);
        }
    }

    //seleciona todos os dias uteis
    private void selectWeekDays(RelativeLayout layout) {
        for (int i = MONDAY; i < SATURDAY; i++) {
            ((CheckBox) layout.getChildAt(i)).setChecked(true);
        }
    }

    //seleciona o final de semana
    private void selectWeekend(RelativeLayout layout) {
        ((CheckBox) layout.getChildAt(SUNDAY)).setChecked(true);
        ((CheckBox) layout.getChildAt(SATURDAY)).setChecked(true);
    }

    //deixa checkboxes livres para o usuário escolher
    private void userSelect(RelativeLayout layout) {
        for (int i = 0; i < FULL_WEEK; i++) {
            layout.getChildAt(i).setEnabled(true);
        }
    }

    //salva os dias no alarme e o alarme na persistencia
    private void saveDays () {
        alarm.setSelectedDays(selectedDays);
        alarmList.set(alarmPosition, alarm);
        PersistenceManager.saveAlarms(getApplicationContext(), alarmList);
    }

    //escuta mudanças de checkboxes e adiciona ou remove na lista e salva a lista
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Integer valorDia;
        if (buttonView instanceof CheckBox) {
            valorDia = layout.indexOfChild(buttonView) + 1;
            if (isChecked) {
                selectedDays.add(valorDia);
                saveDays();
            } else {
                selectedDays.remove(valorDia);
                saveDays();
            }
        }
    }
}
