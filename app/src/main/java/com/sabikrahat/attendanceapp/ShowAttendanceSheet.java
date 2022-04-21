package com.sabikrahat.attendanceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class ShowAttendanceSheet extends AppCompatActivity {

    private WebView webView;
    private TextView tv;
    private String URL = "http://www.muthosoft.com/univ/attendance/report.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_attendance_sheet);

        webView = findViewById(R.id.webViewId);
        tv = findViewById(R.id.textView_title);
        Intent intent = getIntent();
        String crsCode = intent.getStringExtra("courseCode");

        tv.setText(crsCode);

        findViewById(R.id.backBtn).setOnClickListener(v -> finish());

        String[] keys = {"CSE489-Lab", "year", "semester", "course", "section", "sid"};
        String[] values = {"true", "2022", "1", crsCode, "2", "2019160256"};
        httpRequest(keys, values);
    }

    private void httpRequest(final String keys[], final String values[]) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    for (int i = 0; i < keys.length; i++) {
                        System.out.println(keys[i] + " " + values[i]);
                        params.add(new BasicNameValuePair(keys[i], values[i]));
                    }
                    String data = JSONParser.getInstance().makeHttpRequest(URL, "POST", params);
                    System.out.println(data);
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
                        webView.loadDataWithBaseURL(null, data, "text/html", "UTF-8", null);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }.execute();
    }
}