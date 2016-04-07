package projetoes.com.floppyalarm;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RowAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Alarme> list;
    private Context context;
    Alarme alarmeSelecionado;

    public RowAdapter(ArrayList<Alarme> list, Context context) {
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
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        alarmeSelecionado = list.get(position);
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.content_main_row, null);
        }
        TextView listItemText = (TextView) view.findViewById(R.id.txt_alarmlbl);
        listItemText.setText(alarmeSelecionado.getLabel());

        listItemText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SettingsActivity.class);
                context.startActivity(intent);
            }
        });

        return view;
    }
}

