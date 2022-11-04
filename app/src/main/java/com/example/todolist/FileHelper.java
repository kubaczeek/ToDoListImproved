package com.example.todolist;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Locale;

public class FileHelper {

    public static final String FILENAME = "listinfo1.dat";
    public static final String FILENAME2 = "listinfo1.dat";

    public static void writeData(ArrayList<String> item, Context context){
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME,Context.MODE_PRIVATE);
            ObjectOutputStream oas = new ObjectOutputStream(fos);
            oas.writeObject(item);
            oas.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeData2(Integer todo, Context context){
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME2,Context.MODE_PRIVATE);
            ObjectOutputStream oas = new ObjectOutputStream(fos);
            oas.writeObject(todo);
            oas.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> readData(Context context){
        ArrayList<String> itemList = null;


        FileInputStream fis = null;
        try {
            fis = context.openFileInput(FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            itemList = (ArrayList<String>) ois.readObject();
        } catch (FileNotFoundException e) {
            itemList = new ArrayList<>();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return itemList;
    }
    public static Integer readData2(Context context){
        Integer todo = null;


        FileInputStream fis = null;
        try {
            fis = context.openFileInput(FILENAME2);
            ObjectInputStream ois = new ObjectInputStream(fis);
            todo = (Integer) ois.readObject();
        } catch (FileNotFoundException e) {
            todo = new Integer(0);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return todo;
    }
}
