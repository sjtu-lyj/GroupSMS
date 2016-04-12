package com.home.groupsms;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.home.groupsms.Model.Contact;
import com.home.groupsms.Model.Message;
import com.home.groupsms.Model.Recipient;

import java.util.Enumeration;

/**
 * Created by Administrator on 11/26/2015.
 */
public class ComposeSMSActivity extends AppCompatActivity {

    int screenOnTime=0;
    private Toolbar mToolbar;
    private Menu mMenu;
    long clockTime;

    private void setupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
        // Show menu icon
        final ActionBar ab = getSupportActionBar();
        //ab.setHomeAsUpIndicator(R.drawable.ic_home);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(R.string.compose_sms);
    }

    private void saveSMS() {
        DBManager dbManager = null;
        EditText editText;
        Contact contact;
        Recipient recipient;
        String message;
        String key;
        Enumeration<String> enumKey;
        int messageId;

        editText = (EditText) findViewById(R.id.edit);
        //editText.setEnabled(false);
        message = editText.getText().toString();
        if (message.length() == 0 || message == null) {
            message="我刚刚玩手机了";
        }
        //mMenu.getItem(0).setVisible(false);

        dbManager = new DBManager(this, true);
        messageId = dbManager.addMessage(new Message(-1, message, Common.getCurrentDateTime()));

        enumKey = MainActivity.HashtableSelectedContacts.keys();
        while (enumKey.hasMoreElements()) {
            key = enumKey.nextElement();
            contact = MainActivity.HashtableSelectedContacts.get(key);

            recipient = new Recipient(-1, messageId, null,
                    new Contact(contact.id, contact.title, contact.phone1, contact.phone1Type));
            dbManager.addRecipient(recipient);
        }

        Intent intent = new Intent(this, SMSStatusActivity.class);
        intent.putExtra("MESSAGE_ID", messageId);
        startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_sms);

        setupToolbar();
        SharedPreferences preferences=getSharedPreferences("clock",0);
        clockTime=preferences.getLong("clocktime",0);
        Toast.makeText(getApplicationContext(),clockTime+"",Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),System.currentTimeMillis()+"",Toast.LENGTH_SHORT).show();
        System.out.println(clockTime);
        System.out.println(System.currentTimeMillis());
        ScreenListener screenListener=new ScreenListener(getApplicationContext());
        screenListener.begin(new ScreenListener.ScreenStateListener() {
            @Override
            public void onScreenOn() {
                screenOnTime+=1;
                System.out.println(screenOnTime);
                if (screenOnTime > 4 && System.currentTimeMillis()<clockTime) {
                    saveSMS();
                } else if(System.currentTimeMillis()>clockTime){
                    Toast.makeText(getApplicationContext(),"dangerous "+ screenOnTime,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onScreenOff() {

            }

            @Override
            public void onUserPresent() {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_compose_sms, menu);
        mMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_send_sms:
                saveSMS();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
