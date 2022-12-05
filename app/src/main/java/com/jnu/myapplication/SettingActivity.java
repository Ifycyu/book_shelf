package com.jnu.myapplication;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import com.jnu.myapplication.data.BookItem;
import com.jnu.myapplication.data.DataBackUp;
import com.jnu.myapplication.data.DataSaver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Objects;


public class SettingActivity extends AppCompatActivity {
    ActionBarDrawerToggle toggle;
    private ArrayList<BookItem> bookItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        back_up();

        Button button = findViewById(R.id.button_backup_load);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(SettingActivity.this, "无法获取SD卡读写权限", Toast.LENGTH_SHORT).show();
                int checkPermissionResult = CheckPermission.verifyStoragePermissions(SettingActivity.this);
                if(checkPermissionResult == PackageManager.PERMISSION_GRANTED){
                    DataSaver dataSaver = new DataSaver();
                    SDCard2app("book_data_back_up");
//                    write2SDCard("book_data_back_up1",bookItems.toString());
                    dataSaver.save(SettingActivity.this,bookItems);

                }
            }
        });

    }




    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //权限已成功申请

//                    DataSaver dataSaver = new DataSaver();
//                    bookItems = dataSaver.Load(SettingActivity.this);
//                    write2SDCard("book_data.txt",bookItems);
                } else {
                    //用户拒绝授权
                    Toast.makeText(this, "无法获取SD卡读写权限", Toast.LENGTH_SHORT).show();

                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * 写入SD卡私有方法
     * @param fileName
     * @param content
     */
    private void write2SDCard(String fileName,ArrayList<BookItem>  content){
        //1、判断sd卡是否可用
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){

            //sd卡可用
            //2、获取sd卡路径
            try{
                File sdFile = findSDCardRoot(getExternalFilesDir(null));

                File packageSDDir = new File(sdFile,"/Download/");

                if(!packageSDDir.exists() || !packageSDDir.isDirectory()){
                    boolean res = packageSDDir.mkdirs();//

                }

                File filePath = new File(packageSDDir,"book_data_back_up");//sd卡下面的a.txt文件 参数 前面 是目录 后面是文件
                if(!filePath.exists()){
                    filePath.createNewFile();
                }

                Toast.makeText(SettingActivity.this, "写入文件到"+filePath, Toast.LENGTH_SHORT).show();

                new DataBackUp().save(filePath,content);

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this,"error`"+e,Toast.LENGTH_SHORT);
            }
        }


    }
    private static File findSDCardRoot(File externalFilesDir) {
        File parent;
        boolean equals = (Objects.requireNonNull(parent = externalFilesDir.getParentFile())).getName().equals("0");
        if (!equals) {
            return findSDCardRoot(parent);
        } else {
            return parent;
        }

    }
    private void back_up()
    {
        Button button = findViewById(R.id.button_backup);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(SettingActivity.this, "无法获取SD卡读写权限", Toast.LENGTH_SHORT).show();
                int checkPermissionResult = CheckPermission.verifyStoragePermissions(SettingActivity.this);
                if(checkPermissionResult == PackageManager.PERMISSION_GRANTED){
                    DataSaver dataSaver = new DataSaver();
                    bookItems = dataSaver.Load(SettingActivity.this);
                    write2SDCard("book_data_back_up",bookItems);
                }
            }
        });
    }
    private void SDCard2app(String fileName){//还原
        //1、判断sd卡是否可用
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //sd卡可用
            //2、获取sd卡路径
            try{

                File sdFile = findSDCardRoot(getExternalFilesDir(null));
                File packageSDDir = new File(sdFile,"/Download/");
                File filePath = new File(packageSDDir,fileName);//sd卡下面的a.txt文件 参数 前面 是目录 后面是文件
                if (!filePath.exists()) {
                    Toast.makeText(SettingActivity.this, filePath+"还没备份过", Toast.LENGTH_SHORT).show();
                    return;
                }

                DataBackUp dataBackUp = new DataBackUp();
                bookItems = dataBackUp.Load(filePath);//sd 加载数据
                new DataSaver().save(SettingActivity.this,bookItems);
                Toast.makeText(SettingActivity.this, filePath+"恢复成功", Toast.LENGTH_SHORT).show();


            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this,"error`"+e,Toast.LENGTH_SHORT);
            }
        }


    }
}