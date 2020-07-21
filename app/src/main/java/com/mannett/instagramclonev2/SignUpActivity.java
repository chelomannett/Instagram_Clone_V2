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

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    //UI Components
    private EditText edtEmailSignUp, edtUsernameSignUp, edtPasswordSignUp;
    private Button btnSignUp_signUp_activity, btnLogIn_signUp_activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);

        setTitle(R.string.title_sign_up_activity);


        edtEmailSignUp = findViewById(R.id.edtEmailSignUp);
        edtUsernameSignUp = findViewById(R.id.edtUsernameSignUp);
        edtPasswordSignUp = findViewById(R.id.edtPasswordSignUp);
        btnSignUp_signUp_activity = findViewById(R.id.btnSignUp_signUp_activity);
        btnLogIn_signUp_activity = findViewById(R.id.btnLogIn_signUp_activity);

        edtPasswordSignUp.setOnKeyListener(new View.OnKeyListener() { //to implement that enter key accepts.
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){

                    onClick(btnSignUp_signUp_activity);
                }

                return false;
            }
        });

        btnSignUp_signUp_activity.setOnClickListener(SignUpActivity.this);
        btnLogIn_signUp_activity.setOnClickListener(SignUpActivity.this);

        if (ParseUser.getCurrentUser() != null){
           // ParseUser.logOut();
            transitionToSocialMediaActivity();
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnSignUp_signUp_activity:

                if (edtEmailSignUp.getText().toString().equals("") ||
                        edtUsernameSignUp.getText().toString().equals("") ||
                        edtPasswordSignUp.getText().toString().equals("")){
                    FancyToast.makeText(SignUpActivity.this,
                            "Email, Username and Password are required",
                            FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();

                }else {

                    final ParseUser appUser = new ParseUser();
                    appUser.setUsername(edtUsernameSignUp.getText().toString());
                    appUser.setEmail(edtEmailSignUp.getText().toString());
                    appUser.setPassword(edtPasswordSignUp.getText().toString());

                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Signing up " + edtUsernameSignUp.getText().toString());
                    progressDialog.show();

                    appUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(SignUpActivity.this,
                                        appUser.get("username") + " is signed up successfully",
                                        FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                                        transitionToSocialMediaActivity();
                            } else {
                                FancyToast.makeText(SignUpActivity.this, "There was an error: " + e.getMessage(),
                                        FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();


                            }
                            progressDialog.dismiss();
                        }
                    });

                }

            break;
            case R.id.btnLogIn_signUp_activity:

                Intent intent = new Intent(SignUpActivity.this,LogInActivity.class);
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

        Intent intent = new Intent(SignUpActivity.this,SocialMediaActivity.class);
        startActivity(intent);
    }
}