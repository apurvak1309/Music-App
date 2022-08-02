package com.ak.madproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class Adapter extends ArrayAdapter<String> {
    List<String> items,items1;
    Context context;
    int resource;

    public Adapter(Context context, int resource, List<String> items, List<String> items1) {
        super(context, resource, items);
        this.context = context;
        this.resource = resource;
        this.items = items;
        this.items1 = items1;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.activity_second, null);
        TextView t1 = (TextView) view.findViewById(R.id.textView);
        TextView t2 = (TextView) view.findViewById(R.id.textView1);
        t1.setText(items.get(position));
        t2.setText(items1.get(position));
        return view;
    }
}
