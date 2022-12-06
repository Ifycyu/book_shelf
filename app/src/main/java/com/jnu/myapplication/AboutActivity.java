package com.jnu.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);

        RelativeLayout relativeLayout= (RelativeLayout) findViewById(R.id.relativeLayout);

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.jnu)//图片
                .setDescription("cyy的书架")//介绍
                .addItem(new Element().setTitle("Version 1.0"))
                .addEmail("872048129@qq.com","我的邮箱")//邮箱
                .create();

        relativeLayout.addView(aboutPage);

    }
}