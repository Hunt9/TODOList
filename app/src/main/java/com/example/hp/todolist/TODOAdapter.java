package com.example.hp.todolist;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 1/14/2017.
 */

public class TODOAdapter extends ArrayAdapter<TODO> {
    public TODOAdapter(Context context, int resource, List<TODO> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.listtodo,parent,false);

        }

        TextView LName = (TextView)convertView.findViewById(R.id.Name);
        TextView LDate = (TextView)convertView.findViewById(R.id.Date);
        TextView LDescription = (TextView)convertView.findViewById(R.id.Description);
        TextView LUsername = (TextView)convertView.findViewById(R.id.Username);

        TODO todos = getItem(position);

        LName.setText(todos.getName());
        LDate.setText(todos.getDate());
        LDescription.setText(todos.getDescription());
        LUsername.setText(todos.getUsername());


        return convertView;
    }


}
