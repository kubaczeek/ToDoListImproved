package com.example.todolist;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Todo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo);
        Bundle extras = getIntent().getExtras();

        @SuppressLint({"ResourceType", "MissingInflatedId", "LocalSuppress"}) TextView textView = (TextView) findViewById(R.layout.todo);
        textView.setText(getString(R.string.isDone) + " " + extras.getString("count"));

    }

}
