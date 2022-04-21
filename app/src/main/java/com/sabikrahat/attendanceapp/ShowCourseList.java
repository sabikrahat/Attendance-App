package com.sabikrahat.attendanceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class ShowCourseList extends AppCompatActivity {

    private GridView gridView;

    private String URL = "https://muthosoft.com/univ/attendance/report.php";
    private ArrayList<Course> courseList;
    private CustomCourseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_course_list);

        gridView = findViewById(R.id.gridViewId);
        String[] keys = {"my_courses", "sid"};
        String[] values = {"true", "2019160256"};
        httpRequest(keys, values);

        gridView.setOnItemClickListener((adapterView, view, i, l) -> {
            Course course = courseList.get(i);
            Intent intent = new Intent(ShowCourseList.this, ShowAttendanceSheet.class);
            intent.putExtra("courseCode", course.courseCode);
            startActivity(intent);
        });

        findViewById(R.id.exitBtn).setOnClickListener(v -> finish());
    }


    private void httpRequest(final String keys[], final String values[]) {
        courseList = new ArrayList<>();
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    for (int i = 0; i < keys.length; i++) {
                        System.out.println("Rahat In For Loop" + keys[i] + " " + values[i]);
                        params.add(new BasicNameValuePair(keys[i], values[i]));
                    }
                    String data = JSONParser.getInstance().makeHttpRequest(URL, "POST", params);
                    System.out.println("Rahat OutSide For Loop: " + data);
                    System.out.println("Rahat OutSide For Loop: " + data.split(",")[0]);
                    System.out.println("Rahat OutSide For Loop: " + data.split(",")[1]);
                    System.out.println("Rahat OutSide For Loop: " + data.split(",")[2]);
                    courseList.add(new Course(data.split(",")[0], data.split(",")[1], data.split(",")[2]));
                    return data;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String data) {
                if (data != null) {
                    try {
                        System.out.println("Final data Html Body: " + data);
                        adapter = new CustomCourseAdapter(ShowCourseList.this, courseList);
                        gridView.setAdapter(adapter);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }.execute();
    }
}