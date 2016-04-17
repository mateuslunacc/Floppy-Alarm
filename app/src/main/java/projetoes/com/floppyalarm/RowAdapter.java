package projetoes.com.floppyalarm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import projetoes.com.floppyalarm.utils.PersistenceManager;
import projetoes.com.floppyalarm.utils.TimeStringFormat;

public class RowAdapter extends BaseAdapter implements ListAdapter {
    private List<Alarm> alarmList;
    private Context context;
    private Alarm selectedAlarm;
    private Switch swiActive;
    private String time;

    public RowAdapter(List<Alarm> alarmList, Context context)
    {
        this.alarmList = alarmList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return alarmList.size();
    }

    @Override
    public Object getItem(int pos) {
        return alarmList.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        selectedAlarm = (Alarm) getItem(position);
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.content_main_row, parent, false);
        }
        TextView listItemText = (TextView) view.findViewById(R.id.txt_alarmlbl);
        TextView textTime = (TextView) view.findViewById(R.id.txt_timelbl);
        swiActive = (Switch) view.findViewById(R.id.swi_active);

        //carrega o estado de ativo do switch
        swiActive.setChecked(selectedAlarm.isActive());
        swiActive.setTag(Integer.valueOf(position));

        //recupera e formata horário de alarme
        //carrega tempo do alarme e exibe na activity
        boolean is24h = DateFormat.is24HourFormat(context);
        time = TimeStringFormat.formataString(selectedAlarm.getHour(), selectedAlarm.getMinute(), is24h);

        //mostra o horário de cada alarme abaixo de seu label
        listItemText.setText(selectedAlarm.getLabel());
        listItemText.setTag(Integer.valueOf(position));
        textTime.setText(time);
        textTime.setTag(Integer.valueOf(position));

        //passa informações do alarme para a tela de settings
        listItemText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer realPosition = (Integer) v.getTag();
                selectedAlarm = (Alarm) getItem(realPosition);
                Intent intent = new Intent(context, SettingsActivity.class);
                intent.putExtra("alarm", selectedAlarm);
                intent.putExtra("alarmPosition", realPosition);
                context.startActivity(intent);
            }
        });

        //quando o usuário clica por um tempo no alarme aparece a opção de deletar
        listItemText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete alarm")
                        .setMessage("Are you sure you want to delete this alarm?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Integer realPosition = (Integer) v.getTag();
                                alarmList.remove(getItem(realPosition));
                                Toast toast = Toast.makeText(context, "Alarm deleted", Toast.LENGTH_SHORT);
                                PersistenceManager.saveAlarms(context, alarmList);
                                toast.show();
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });

        swiActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer realPosition = (Integer) v.getTag();
                selectedAlarm = (Alarm) getItem(realPosition);
                boolean status = selectedAlarm.isActive();
                swiActive.setChecked(!status);
                selectedAlarm.setActive(!status);
                PersistenceManager.saveAlarms(context, alarmList);
                notifyDataSetChanged();
            }
        });
        return view;
    }
}
