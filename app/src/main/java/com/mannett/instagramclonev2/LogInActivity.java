package com.mannett.instagramclonev2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {
    //UI Components
    private EditText edtEmailLogIn, edtPasswordLogIn;
    private Button btnLogIn_logIn_activity, btnSignUp_logIn_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        setTitle(R.string.title_log_in_activity);

        edtEmailLogIn = findViewById(R.id.edtEmailLogIn);
        edtPasswordLogIn = findViewById(R.id.edtPasswordLogIn);
        btnLogIn_logIn_activity = findViewById(R.id.btnLogIn_logIn_activity);
        btnSignUp_logIn_activity = findViewById(R.id.btnSignUp_logIn_activity);

        btnLogIn_logIn_activity.setOnClickListener(LogInActivity.this);
        btnSignUp_logIn_activity.setOnClickListener(LogInActivity.this);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnLogIn_logIn_activity:

                ParseUser.logInInBackground(edtEmailLogIn.getText().toString(),
                        edtPasswordLogIn.getText().toString(), new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {

                                if (user != null && e == null){
                                    FancyToast.makeText(LogInActivity.this,  user.getUsername()
                                            +" is logged in", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                                    //Intent intent = new Intent(LogInActivity.this, WelcomeActivity.class);
                                    //startActivity(intent);

                                }else{
                                    FancyToast.makeText(LogInActivity.this, "There was an error: "+ e.getMessage(),
                                            FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();

                                }
                            }
                        });

                break;
            case R.id.btnSignUp_logIn_activity:

                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);

                break;
        }
    }
}