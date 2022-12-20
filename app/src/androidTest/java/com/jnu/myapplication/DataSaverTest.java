package com.jnu.myapplication;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;

import com.jnu.myapplication.data.BookItem;
import com.jnu.myapplication.data.DataSaver;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class DataSaverTest {
    DataSaver dataSaverBackup;
    ArrayList<BookItem> bookItemsBackup;

    @Before
    public void setUp() throws Exception {
        dataSaverBackup = new DataSaver();
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        bookItemsBackup = dataSaverBackup.Load(targetContext);
    }

    @After
    public void tearDown() throws Exception {
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        dataSaverBackup.save(targetContext,bookItemsBackup);
    }

    @Test
    public void saveAndLoad() {
        DataSaver dataSaver = new DataSaver();
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ArrayList<BookItem> bookItems = new ArrayList<>();
        BookItem bookItem = new BookItem("name","12","123","123","12","12","12",R.drawable.book_1,12);
        BookItem bookItem2 = new BookItem("name","12","123","123","12","12","12",R.drawable.book_1,12);
        bookItems.add(bookItem);
        bookItems.add(bookItem2);
        dataSaver.save(targetContext,bookItems);

        DataSaver dataSaverLoad = new DataSaver();

        ArrayList<BookItem> bookItemsLoad = dataSaverLoad.Load(targetContext);
        Assert.assertEquals(2,bookItemsLoad.size());
        Assert.assertEquals(bookItems.get(0).getTITLE(),"name");
    }

//    @Test
//    public void load() {
//        DataSaver dataSaver = new DataSaver();
//
//        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        ArrayList<BookItem> bookItems = dataSaver.Load(targetContext);
//        Assert.assertEquals(2,bookItems.size());
//        Assert.assertEquals(bookItems.get(0).getTITLE(),"name");
//    }

}