package com.example.facetoface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.internal.NavigationMenu;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.URL;

public class profileActivity extends AppCompatActivity {
    EditText meetingcode;
    Button joinbtn,sharebtn;
    NavigationMenu mainNavigationbar;
    ClipboardManager clipboardManager;
    public void exit(){
        startActivity(new Intent(profileActivity.this,login_activity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        meetingcode= findViewById(R.id.meetingcode);
        joinbtn= findViewById(R.id.joinbtn);
        sharebtn= findViewById(R.id.sharebtn);
        URL serverURL;
        try {
            serverURL = new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions defaultOption = new JitsiMeetConferenceOptions.Builder().setServerURL(serverURL).setWelcomePageEnabled(false).build();
            JitsiMeet.setDefaultConferenceOptions(defaultOption);
        }catch(Exception e){
            e.printStackTrace();
        }
        joinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JitsiMeetConferenceOptions option = new JitsiMeetConferenceOptions.Builder().setRoom(meetingcode.getText().toString()).setWelcomePageEnabled(false).build();
                JitsiMeetActivity.launch(profileActivity.this,option);
            }
        });
        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        String code = meetingcode.getText().toString();
        final String shareMeetingCode = "Hi,\n\tJoin Meeting with this code: " + code;


        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipData data = ClipData.newPlainText("text",shareMeetingCode);
                clipboardManager.setPrimaryClip(data);
                ClipData.Item item = clipboardManager.getPrimaryClip().getItemAt(0);
                String finalCode =item.getText().toString();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,finalCode);
                intent.setType("text/plain");
                intent = Intent.createChooser(intent,"Share code by");
                startActivity(intent);
            }
        });
    }
}