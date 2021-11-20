package com.example.musicvideoalarm;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.musicvideoalarm.databinding.ActivityMainBinding;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.android.youtube.player.YouTubePlayer;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding activityMainBinding;
    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    String selectedAlarmDate;
    String selectedAlarmTime;
    String selectedAlarmDateTime=null;
    String genreSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        activityMainBinding.setCurrentTime(setCurrentTime());
        Date d= new Date();
        activityMainBinding.calendarView.setDate(System.currentTimeMillis());
        activityMainBinding.timePicker.setHour(d.getHours());
        activityMainBinding.timePicker.setMinute(d.getMinutes());

        createNotificationChannel();

        setSelectedAlarmTime();
        setSelectedAlarmDate();

        activityMainBinding.calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                selectedAlarmDate=i+"/"+i1+"/"+i2+" ";
            }
        });
        activityMainBinding.timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                selectedAlarmTime=i+":"+i1+":00";
            }
        });
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.genres, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityMainBinding.musicgenrespinner.setAdapter(adapter);

        activityMainBinding.musicgenrespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                genreSelected=adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

       activityMainBinding.setAlarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               if (b){
                   setAlarm();
               }else{
                   cancelAlarm();
               }
           }
       });


    }

    private void cancelAlarm() {
        Intent intent= new Intent(this,AlarmReceiver.class);

        pendingIntent= PendingIntent.getBroadcast(this,0,intent,0);

        if(alarmManager==null){
            alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }
        alarmManager.cancel(pendingIntent);
        Toast.makeText(MainActivity.this, "Alarm cancelled", Toast.LENGTH_SHORT).show();
    }

    private long getSelectedDateTime(){
        selectedAlarmDate=selectedAlarmDate+selectedAlarmTime;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(selectedAlarmDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long millis = date.getTime();
        return millis;
    }

    private void setSelectedAlarmDate(){
        selectedAlarmDate=new SimpleDateFormat("yyyy/MM/dd").format(activityMainBinding.calendarView.getDate())+" ";
        Toast.makeText(MainActivity.this, selectedAlarmDate+"", Toast.LENGTH_SHORT).show();
    }
    private void setSelectedAlarmTime(){
        selectedAlarmTime=activityMainBinding.timePicker.getHour()+":"+activityMainBinding.timePicker.getMinute()+":00";
    }
    private void setAlarm(){

        alarmManager= (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent= new Intent(this,AlarmReceiver.class);
        intent.putExtra("genre",genreSelected);

        pendingIntent= PendingIntent.getBroadcast(this,0,intent,0);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,getSelectedDateTime(),AlarmManager.INTERVAL_DAY,pendingIntent);

        Toast.makeText(MainActivity.this, "Alarm set succesfully", Toast.LENGTH_SHORT).show();

    }


    private String setCurrentTime(){
        String time= new SimpleDateFormat("HH:mm a").format(new Date());
        return time;
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name= "Alarm channel";
            String description= "channel for alarm";
            int importance= NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("Alarm",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager= getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


     /*
    private void  showTimePicker(){
        picker= new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Set Alarm time")
                .build();
        picker.show(getSupportFragmentManager(),"alarm");

        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (picker.getHour()>12){
                    activityMainBinding.setCurrentTime(String.format("%02d",(picker.getHour()-12))+" : "+String.format("%02d",(picker.getMinute()+"PM")));
                }else{
                    activityMainBinding.setCurrentTime(String.format("%02d",(picker.getHour()))+" : "+String.format("%02d",(picker.getMinute()+"AM")));
                }
            }
        });

    }*/
}