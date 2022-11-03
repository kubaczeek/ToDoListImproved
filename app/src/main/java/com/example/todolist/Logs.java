package com.example.todolist;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Logs extends AppCompatActivity {
    List<String> appLifeCycle;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs);
        TextView textOnCreate = findViewById(R.id.textView4);
        textOnCreate.setText(getTimestamp() + " - onCreate()\n");

    }

    @Override
    protected void onResume() {
        super.onResume();

        TextView textOnCreate = findViewById(R.id.textView2);
        textOnCreate.setText(getTimestamp() + " - onResume()\n");
    }

    @Override
    protected void onStop() {
        super.onStop();
        TextView textOnCreate = findViewById(R.id.textView1);
        textOnCreate.setText(getTimestamp() + " - onStop()\n");
    }

    public static String getTimestamp() {
        Long ts = System.currentTimeMillis()/1000;
        return ts.toString();
    }



}