package com.example.todolist;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText editText;
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    public Integer counter = arrayList.size();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        listView = findViewById(R.id.ListView);

        arrayList = FileHelper.readData(this);

        arrayAdapter = new ArrayAdapter<>(this, R.layout.list_view_layout, arrayList);
        listView.setAdapter(arrayAdapter);

        counter = arrayList.size();
        button.setOnClickListener(new View.OnClickListener() {
            View parentLayout = findViewById(android.R.id.content);

            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: " + getTimestamp());
                String itemName = editText.getText().toString().toUpperCase();
                if (itemName.length() > 0) {
                    arrayList.add(getString(R.string.not_done_24)+ " " + itemName);
                    editText.setText("");
                    arrayAdapter.notifyDataSetChanged();
                    showSnackbar(parentLayout, itemName + " " + getString(R.string.item_added));
                    counter++;
                } else {
                    showSnackbar(parentLayout, getString(R.string.task_cant_empty));
                }

            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long arg3) {
                View parentLayout = findViewById(android.R.id.content);
                Snackbar snackbar = Snackbar
                        .make(parentLayout, "Do you want delete:\"" + arrayList.get(position) + "\"?", Snackbar.LENGTH_LONG * 5)
                        .setAction("YES", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this, "Item \"" + arrayList.get(position) + "\" has been deleted!",
                                        Toast.LENGTH_LONG).show();
                                arrayAdapter.remove(arrayList.get(position));
                                arrayAdapter.notifyDataSetChanged();
                            }
                        });
                snackbar.show();
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            View parentLayout = findViewById(android.R.id.content);

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d(TAG, "onItemClick: " + getTimestamp());
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle(getString(R.string.del_btn));
                alert.setMessage(getString(R.string.really_delete));
                alert.setCancelable(false);
                alert.setNegativeButton(getString(R.string.no_btn), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.i(TAG, "onClick: " + getTimestamp());
                        String itemClicked = arrayList.get(position);
                        arrayList.add(getString(R.string.done_24) + " " + itemClicked.substring(2));
                        showSnackbar(parentLayout, arrayList.get(position).substring(2) + getString(R.string.item_removed_info));
                        arrayList.remove(arrayList.get(position));
                        arrayAdapter.notifyDataSetChanged();
                        dialogInterface.cancel();

                    }
                });
                alert.setPositiveButton(getString(R.string.yes_btn), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.i(TAG, "onClick: " + getTimestamp());
                        showSnackbar(parentLayout, arrayList.get(position).substring(2) + " " + getString(R.string.item_removed_info));
                        counter--;
                        arrayAdapter.remove(arrayList.get(position));
                        arrayAdapter.notifyDataSetChanged();
                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });

    }
    public static void setLocale(Activity activity, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        View parentLayout = findViewById(android.R.id.content);

        int id = item.getItemId();
        if (id == R.id.idOptionMenu1) {
            showSnackbar(parentLayout, getString(R.string.how_to_add));
            return true;
        } else if (id == R.id.idOptionMenu2) {
            showSnackbar(parentLayout, getString(R.string.how_to_delete));
            return true;
        } else if (id == R.id.idOptionMenu3) {
            Intent intent = new Intent(MainActivity.this, Logs.class);
            this.startActivity(intent);
            return true;
        }
        if (id == R.id.PL) {
            setLocale(MainActivity.this, "pl");
            finish();
            startActivity(getIntent());
        }
        if (id == R.id.EN) {
            setLocale(MainActivity.this, "en");
            finish();
            startActivity(getIntent());
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSnackbar(View parentLayout, String text) {
        Snackbar.make(parentLayout, text, Snackbar.LENGTH_LONG)
                .setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                .show();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop: " + getTimestamp());
        super.onStop();
        FileHelper.writeData(arrayList, getApplicationContext());
    }

    public static String getTimestamp() {
        Long ts = System.currentTimeMillis() / 1000;
        return ts.toString();
    }



}