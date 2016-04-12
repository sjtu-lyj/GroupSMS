package com.home.groupsms.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.home.groupsms.MainActivity;
import com.home.groupsms.R;

import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import picker.ugurtekbas.com.Picker.Picker;

public class ChooseTimeActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.amPicker)
    Picker amPicker;
    @Bind(R.id.button1)
    Button button1;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_time);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

    }

    @OnClick(R.id.button1)
    void showTime() {
        Calendar calendar= Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,amPicker.getCurrentHour());
        calendar.set(Calendar.MINUTE, amPicker.getCurrentMin());
        Date data=calendar.getTime();
        long mills=data.getTime();

        SharedPreferences preferences=getSharedPreferences("clock",0);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putLong("clocktime",mills);
        editor.commit();

        Intent intent=new Intent();
        intent.setClass(ChooseTimeActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
