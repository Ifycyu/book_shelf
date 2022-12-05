package com.jnu.myapplication.data;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DataBackUp {

    public void save(File filePath, ArrayList<BookItem> data) {
        try {

            FileOutputStream dataStream= new FileOutputStream(filePath);
            ObjectOutputStream out = new ObjectOutputStream(dataStream);
            out.writeObject(data);
            out.close();
            dataStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;

        }

    }
    @NonNull
    public ArrayList<BookItem> Load(File filePath)
    {
        ArrayList<BookItem> data = new ArrayList<> ();
        try
        {
            FileInputStream fileIn =new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            data = (ArrayList<BookItem> ) in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return  data;
    }
}
