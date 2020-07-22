package com.mannett.instagramclonev2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabAdapter extends FragmentPagerAdapter {
    public TabAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int tabPosition) {

        switch (tabPosition){

            case 0: //THE 1ST TAB
                ProfileTab profileTab = new ProfileTab(); //The FRAGMENT created to be a TAB
                return profileTab; //NOT NEEDED A BREAK STATEMENT. RETURN PERFORMS AS A BREAK.

            case 1: //THE 2ND TAB
                UsersTab usersTab = new UsersTab(); //another FRAGMENT created to be a TAB
                return usersTab;

            case 2:
                return new SharedPicturesTab(); // ANOTHER WAY TO CALL THE 3rd FRAGMENT
            default:
                return null;


        }

    }

    @Override
    public int getCount() {
        return 3; //The number of the TABs we have
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0:
                return "Profile"; //NAME OF THE 1ST TAB
            case 1:
                return "Users"; //NAME OF THE 2ND TAB
            case 2:
                return "Share Pictures"; // NAME OF THE 3RD TAB
            default:
                return null;


        }
    }
}
