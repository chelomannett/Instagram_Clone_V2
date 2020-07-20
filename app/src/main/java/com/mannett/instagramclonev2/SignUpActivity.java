package com.mannett.instagramclonev2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpActivity extends AppCompatActivity {

    private EditText edtEmailSignUp, edtUsernameSignUp, edtPasswordSignUp;
    private Button btnSignUp, btnLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);

        edtEmailSignUp = findViewById(R.id.edtEmailSignUp);
        edtUsernameSignUp = findViewById(R.id.edtUsernameSignUp);
        edtPasswordSignUp = findViewById(R.id.edtPasswordSignUp);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogIn = findViewById(R.id.btnLogIn);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ParseUser appUser = new ParseUser();
                appUser.setUsername(edtUsernameSignUp.getText().toString());
                appUser.setEmail(edtEmailSignUp.getText().toString());
                appUser.setPassword(edtPasswordSignUp.getText().toString());

                appUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null){
                            FancyToast.makeText(SignUpActivity.this,  appUser.get("username")+" is signed up successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                            Intent intent = new Intent(SignUpActivity.this,WelcomeActivity.class);
                            startActivity(intent);
                        }else {
                            FancyToast.makeText(SignUpActivity.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();


                        }
                    }
                });


            }
        });

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,LogInActivity.class);
                startActivity(intent);
            }
        });
    }
}