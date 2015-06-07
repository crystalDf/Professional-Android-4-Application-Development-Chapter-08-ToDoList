package com.star.todolist;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ToDoItemAdapter extends ArrayAdapter<ToDoItem> {

    private int resource;

    public ToDoItemAdapter(Context context, int resource, List<ToDoItem> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        LinearLayout toDoView;
//
//        ToDoItem toDoItem = getItem(position);
//
//        String taskString = toDoItem.getTask();
//        Date createdDate = toDoItem.getCreated();
//
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
//        String dateString = simpleDateFormat.format(createdDate);
//
//        if (convertView == null) {
//            toDoView = new LinearLayout(getContext());
//
//            LayoutInflater layoutInflater = (LayoutInflater)
//                    getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//            layoutInflater.inflate(resource, toDoView, true);
//        } else {
//            toDoView = (LinearLayout) convertView;
//        }
//
//        TextView dateView = (TextView) toDoView.findViewById(R.id.rowDate);
//        TextView taskView = (TextView) toDoView.findViewById(R.id.row);
//
//        dateView.setText(dateString);
//        taskView.setText(taskString);
//
//        return toDoView;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater)
                    getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(resource, null);
        }

        ToDoItem toDoItem = getItem(position);

        String taskString = toDoItem.getTask();
        Date createdDate = toDoItem.getCreated();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
        String dateString = simpleDateFormat.format(createdDate);

        TextView dateView = (TextView) convertView.findViewById(R.id.rowDate);
        TextView taskView = (TextView) convertView.findViewById(R.id.row);

        dateView.setText(dateString);
        taskView.setText(taskString);

        return convertView;
    }
}
