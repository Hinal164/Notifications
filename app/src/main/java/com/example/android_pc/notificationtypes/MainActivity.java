package com.example.android_pc.notificationtypes;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private Button simple, inbox, bigText, bigPicture,schedule;

    private NotificationHelper helper;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        simple = (Button) findViewById(R.id.simple);
        inbox = (Button) findViewById(R.id.inbox);
        bigText = (Button) findViewById(R.id.bigText);
        bigPicture = (Button) findViewById(R.id.bigPicture);
        schedule=(Button)findViewById(R.id.schedule);

        helper = new NotificationHelper(this);

        simple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                helper.createNotification("Simple Notification", "This is simple notification");

            }
        });

        inbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                helper.createInboxStyleNotification("Inbox style notification", "This is test of inbox style notification.");

            }
        });

        bigText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                helper.createBigTextStyleNotification("Big Text notification", "This is test of big text style notification.");

            }
        });

        bigPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                helper.createBigPictureNotification("Big picture notification", "This is test of big picture notification.");

            }
        });

       /* schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                helper.createScheduleNotification();
            }
        });*/

        helper.createScheduleNotification();
    }

}
