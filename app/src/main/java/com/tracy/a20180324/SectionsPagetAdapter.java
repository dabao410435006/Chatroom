package com.tracy.a20180324;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by zhiyuan19960220 on 2018/3/24.
 */

class SectionsPagetAdapter extends FragmentPagerAdapter{

    public SectionsPagetAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                RequestsFragment requestsFragment = new RequestsFragment();
                return requestsFragment;
            case 1:
                ChatsFragment chatsFragment = new ChatsFragment();
                return chatsFragment;
            case 2:
                FriendsFragment friendsFragment = new FriendsFragment();
                return friendsFragment;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "交友邀請";
            case 1:
               return "聊天室";
            case 2:
               return "好友";
            default:
                return null;
        }
    }
}
