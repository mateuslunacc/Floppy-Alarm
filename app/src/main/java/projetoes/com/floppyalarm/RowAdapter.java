package projetoes.com.floppyalarm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RowAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Alarm> list;
    private Context context;
    private Alarm selectedAlarm;

    public RowAdapter(ArrayList<Alarm> list, Context context)
    {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
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

        int hour = selectedAlarm.getHour();
        int minute = selectedAlarm.getMinute();
        String time = String.format("%02d:%02d", hour, minute);


        listItemText.setText(selectedAlarm.getLabel());
        listItemText.setTag(Integer.valueOf(position));
        textTime.setText(time);
        textTime.setTag(Integer.valueOf(position));

        listItemText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer realPosition = (Integer) v.getTag();
                selectedAlarm = (Alarm) getItem(realPosition);
                Intent intent = new Intent(context, SettingsActivity.class);
                intent.putExtra("alarm", selectedAlarm);
                context.startActivity(intent);
            }
        });

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
                                list.remove(getItem(realPosition));
                                Toast toast = Toast.makeText(context, "Alarm deleted", Toast.LENGTH_SHORT);
                                toast.show();
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });
        return view;
    }
}

