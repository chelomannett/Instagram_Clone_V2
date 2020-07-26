package com.mannett.instagramclonev2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UsersPosts extends AppCompatActivity {

    private LinearLayout linearLayout_UsersPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_posts);

        linearLayout_UsersPosts = findViewById(R.id.linearLayout_UsersPosts);

        Intent receivedIntentObject = getIntent();
        final String receivedUsername = receivedIntentObject.getStringExtra("username");
        FancyToast.makeText(this,receivedUsername ,
                Toast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();


        setTitle(receivedUsername+"'s posts");

        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Photo");
        parseQuery.whereEqualTo("username", receivedUsername);
        //if this line is deleted, its going to show all the photos that all users have posted to server
        parseQuery.orderByDescending("createdAt");

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading "+receivedUsername + "'s posts...");
        dialog.show();

        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (objects.size() > 0 && e == null)  {

                    for (ParseObject post : objects)  {

                        final TextView postDescription = new TextView(UsersPosts.this);
                        final TextView postDate = new TextView(UsersPosts.this);
                          if(post.get("image_des") == null) {
                                   postDescription.setText("\n");
                                }else{
                                postDescription.setText(post.get("image_des")+"");
                          }

                        Date date = post.getCreatedAt();
                        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                        String reportDate = df.format(date);
                        postDate.setText("Posted on "+reportDate);


                        ParseFile postPicture = (ParseFile) post.get("picture");
                        postPicture.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {

                                if (data != null && e == null)  {

                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                    ImageView postImageView = new ImageView(UsersPosts.this);
                                    LinearLayout.LayoutParams imageView_params =
                                            new LinearLayout.LayoutParams(
                                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                                    ViewGroup.LayoutParams.WRAP_CONTENT);
                                    imageView_params.setMargins(5,5,5,5);
                                    postImageView.setLayoutParams(imageView_params);
                                    postImageView.setBackgroundColor(Color.rgb(160,160,160));
                                    postImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    postImageView.setImageBitmap(bitmap);

                                    LinearLayout.LayoutParams des_params = new LinearLayout.LayoutParams(
                                            ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT);
                                    des_params.setMargins(5,5,5,35);
                                    postDescription.setLayoutParams(des_params);
                                    postDescription.setGravity(Gravity.CENTER);
                                    postDescription.setBackgroundColor(Color.BLUE);
                                    postDescription.setTextColor(Color.WHITE);
                                    postDescription.setTextSize(30f);

                                    LinearLayout.LayoutParams date_params = new LinearLayout.LayoutParams(
                                            ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT);
                                    date_params.setMargins(5,5,5,35);
                                    postDate.setLayoutParams(des_params);
                                    postDate.setGravity(Gravity.RIGHT);
                                    postDate.setTextColor(Color.BLACK);
                                    postDate.setTextSize(15f);

                                    linearLayout_UsersPosts.addView(postDate);
                                    linearLayout_UsersPosts.addView(postImageView);
                                    linearLayout_UsersPosts.addView(postDescription);



                                }

                            }
                        });

                    }


                } else {
                    FancyToast.makeText(UsersPosts.this,
                            receivedUsername+" doesn't have any posts",
                            Toast.LENGTH_SHORT,FancyToast.INFO,true).show();
                    finish();
                }
            dialog.dismiss();
            }
        });



    }
}