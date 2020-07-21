package com.mannett.instagramclonev2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

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

        edtPasswordLogIn.setOnKeyListener(new View.OnKeyListener() { //to implement that enter key accepts.
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){

                    onClick(btnLogIn_logIn_activity);
                }

                return false;
            }
        });


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnLogIn_logIn_activity:
                if (edtEmailLogIn.getText().toString().equals("") || edtPasswordLogIn.getText().toString().equals("")){
                    FancyToast.makeText(LogInActivity.this,
                            "Email and Password are required",
                            FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
                }else {
                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Attempting to log in... ");
                    progressDialog.show();

                    ParseUser.logInInBackground(edtEmailLogIn.getText().toString(),
                            edtPasswordLogIn.getText().toString(), new LogInCallback() {

                                @Override
                                public void done(ParseUser user, ParseException e) {

                                    if (user != null && e == null) {
                                        FancyToast.makeText(LogInActivity.this, user.getUsername()
                                                + " is logged in", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();

                                            transitionToSocialMediaActivity();

                                    } else {
                                        FancyToast.makeText(LogInActivity.this, "There was an error: " + e.getMessage(),
                                                FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();

                                    }
                                    progressDialog.dismiss();
                                }
                            });
                }
                break;
            case R.id.btnSignUp_logIn_activity:

                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);

                break;
        }
    }

    public void rootLayoutTapped(View view){ //HIDES KEYBOARD WHEN TAPPED ON OTHER AREA
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private void transitionToSocialMediaActivity(){

        Intent intent = new Intent(LogInActivity.this,SocialMediaActivity.class);
        startActivity(intent);
    }

}