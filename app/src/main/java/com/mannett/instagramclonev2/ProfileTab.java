package com.mannett.instagramclonev2;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileTab extends Fragment {

    private EditText edtProfile_ProfileName, edtProfile_Bio, edtProfile_Profession,
            edtProfile_Hobbies, edtProfile_FavSports;
    private Button btnProfile_UpdateInfo;
    private TextView txtProfile_CurrentUser;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileTab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileTab.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileTab newInstance(String param1, String param2) {
        ProfileTab fragment = new ProfileTab();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);
        //THIS FUNCTIONS AS THE setContentView in activities

        edtProfile_ProfileName = view.findViewById(R.id.edtProfile_profileName);
        edtProfile_Bio = view.findViewById(R.id.edtProfile_bio);
        edtProfile_Profession = view.findViewById(R.id.edtProfile_profession);
        edtProfile_Hobbies = view.findViewById(R.id.edtProfile_Hobbies);
        edtProfile_FavSports = view.findViewById(R.id.edtProfile_FavSports);
        btnProfile_UpdateInfo = view.findViewById(R.id.btnProfile_UpdateInfo);
        txtProfile_CurrentUser = view.findViewById(R.id.txtProfile_CurrentUser);

        final ParseUser parseUser = ParseUser.getCurrentUser(); //With this line we have a reference of the current user
        txtProfile_CurrentUser.setText(parseUser.getUsername().toString()+" is logged in.");


        //GETTING THE DATA FROM THE SERVER
        if (parseUser.get("profileName") == null){
           // parseUser.put("profileName", "");
            edtProfile_ProfileName.setText("");
        }else{
        edtProfile_ProfileName.setText(parseUser.get("profileName").toString());}
        if (parseUser.get("profileBio") == null){
           // parseUser.put("profileBio","");
            edtProfile_Bio.setText("");
        }else{
        edtProfile_Bio.setText(parseUser.get("profileBio").toString());}
        if (parseUser.get("profileProfession") == null){
           // parseUser.put("profileProfession","");
            edtProfile_Profession.setText("");
        }else{
        edtProfile_Profession.setText(parseUser.get("profileProfession").toString());}
        if (parseUser.get("profileHobbies")==null){
           // parseUser.put("profileHobbies","");
            edtProfile_Hobbies.setText("");
        }else{
        edtProfile_Hobbies.setText(parseUser.get("profileHobbies").toString());}
        if (parseUser.get("profileFavSport")==null){
           // parseUser.put("profileFavSport","");
            edtProfile_FavSports.setText("");
        }else{
        edtProfile_FavSports.setText(parseUser.get("profileFavSport").toString());}



        //PUTTING THE DATA TO THE SERVER
        btnProfile_UpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Updating Info");
                progressDialog.show();

                parseUser.put("profileName",edtProfile_ProfileName.getText().toString());
                parseUser.put("profileBio",edtProfile_Bio.getText().toString());
                parseUser.put("profileProfession",edtProfile_Profession.getText().toString());
                parseUser.put("profileHobbies",edtProfile_Hobbies.getText().toString());
                parseUser.put("profileFavSport",edtProfile_FavSports.getText().toString());

                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null){
                            FancyToast.makeText(getContext(),
                                    "Info Updated",
                                    FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
                        }else{
                            FancyToast.makeText(getContext(),
                                    "There is an Error: " + e.getMessage(),
                                    FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                        }
                        progressDialog.dismiss();
                    }
                });
            }
        });

        return view;

    }
}