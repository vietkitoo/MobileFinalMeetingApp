 package com.example.v_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Random;
import java.util.UUID;

 public class MainActivity extends AppCompatActivity {


    TextInputEditText meetingIdInput, nameInput;
    MaterialButton joinBtn, createBtn;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("name_pref", MODE_PRIVATE);

        meetingIdInput = findViewById(R.id.meeting_id_input);
        nameInput = findViewById(R.id.name_input);
        joinBtn = findViewById(R.id.join_btn);
        createBtn = findViewById(R.id.create_btn);
        nameInput.setText(sharedPreferences.getString("name",""));


        joinBtn.setOnClickListener((v) -> {
            String meetingID = meetingIdInput.getText().toString();
            if (meetingID.length()!=8){
                meetingIdInput.setError("Invalid meeting ID");
                meetingIdInput.requestFocus();
                return;
            }

            String name = nameInput.getText().toString();
            if(name.isEmpty()){
                nameInput.setError("Name is required to join the meeting");
                nameInput.requestFocus();
                return;
            }
            startMeeting(meetingID,name);
        });

        createBtn.setOnClickListener(v -> {
            String name = nameInput.getText().toString();
            if(name.isEmpty()){
                nameInput.setError("Name is required to create the meeting");
                nameInput.requestFocus();
                return;
            }
            startMeeting(getRandomMeetingID(),name);
        });

    }

    void startMeeting (String meetingID, String name){
        sharedPreferences.edit().putString("name",name).apply();
        String userID = UUID.randomUUID().toString();

        Intent intent = new Intent(MainActivity.this, ConferenceActivity.class);
        intent.putExtra("meeting_ID", meetingID);
        intent.putExtra("name", name);
        intent.putExtra("user_id", userID);
        startActivity(intent);
    }

    String getRandomMeetingID(){
        StringBuilder id = new StringBuilder();
        while (id.length()!=8){
            int random = new Random().nextInt(8);
            id.append(random);
        }
        return id.toString();
    }
}