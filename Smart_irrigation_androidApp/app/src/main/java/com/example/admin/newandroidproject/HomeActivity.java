package com.example.admin.newandroidproject;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class HomeActivity extends AppCompatActivity {
    Button btnaddfarm, btnNotifation,btnManualOn;
    ImageButton signOutbtn;
    NotificationCompat.Builder notification;
    private static final int uniqueId=45612;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);
        btnaddfarm = (Button) findViewById(R.id.addfarmbtn);
        btnaddfarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addfarmIntent = new Intent(HomeActivity.this,FarmDetailsActivity.class);
                startActivity(addfarmIntent);

            }
        });

        btnManualOn=(Button)findViewById(R.id.ManualOnbtn);
        btnManualOn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent Viewintent =new Intent(HomeActivity.this,ViewFarmActivity.class);
                startActivity(Viewintent);
            }

        });
      /*  btnWateringHistory=(Button)findViewById(R.id.btnHistory);
        btnWateringHistory.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent myHistoryintent =new Intent(HomeActivity.this,HistoryActivity.class);
                startActivity(myHistoryintent);
            }

        });

*/

        signOutbtn=(ImageButton)findViewById(R.id.imgbtnSignOut);
        signOutbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent mySignOutintent =new Intent(HomeActivity.this,MainActivity.class);
                startActivity(mySignOutintent);
            }

        });
    }

    public void buckyButtonClick(View view)
    {
        notification.setSmallIcon(R.drawable.noti);
        notification.setTicker("Notification For Irrigation");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("crop:1");
        notification.setContentText("Irrigation On For crop1");

        Intent notifyintent=new Intent(this,MainActivity.class);
        PendingIntent pintent=PendingIntent.getActivity(this,0,notifyintent,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pintent);

        NotificationManager nm=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqueId,notification.build());
    }
}
