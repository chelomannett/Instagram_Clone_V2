package com.mannett.instagramclonev2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class WelcomeActivity extends AppCompatActivity {

    private TextView txtWelcome;
    private Button btnLogOut_welcome_activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);



        txtWelcome = findViewById(R.id.txtWelcome);
        btnLogOut_welcome_activity = findViewById(R.id.btnLogOut);

        txtWelcome.setText("Welcome "+ ParseUser.getCurrentUser().getUsername() +"!");

        btnLogOut_welcome_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FancyToast.makeText(WelcomeActivity.this,
                        ParseUser.getCurrentUser().getUsername() +" is logged out",
                        FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                ParseUser.logOut();
                finish();
            }
        });
    }
}