package com.example.kanoe_android_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ArrayAdapterProject extends ArrayAdapter<String> {
  private final Context context;
  private final String[] values;

  public ArrayAdapterProject(Context context, String[] values) {
    super(context, R.layout.rowlayoutproject, values);
    this.context = context;
    this.values = values;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.rowlayoutproject, parent, false);
    TextView textView = (TextView)rowView.findViewById(R.id.projectname);
    textView.setText(values[position]);

    return rowView;
  }
  public String getItem(int position){
	  return values[position];
	  }
} 