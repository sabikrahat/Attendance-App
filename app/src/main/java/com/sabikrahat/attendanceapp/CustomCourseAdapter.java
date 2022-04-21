package com.sabikrahat.attendanceapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class CustomCourseAdapter extends ArrayAdapter<Course> {

    private final Context context;
    private final ArrayList<Course> values;


    public CustomCourseAdapter(@NonNull Context context, @NonNull ArrayList<Course> objects) {
        super(context, -1, objects);
        this.context = context;
        this.values = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.layout_grid_item, parent, false);

        TextView crsCode = rowView.findViewById(R.id.courseCode);
        TextView crsTitle = rowView.findViewById(R.id.courseTitle);
        TextView crsInstructor = rowView.findViewById(R.id.courseInstructor);

        crsCode.setText(values.get(position).getCourseCode());
        crsTitle.setText(values.get(position).getCourseTitle());
        crsInstructor.setText(values.get(position).getInstructor());

        return rowView;
    }
}