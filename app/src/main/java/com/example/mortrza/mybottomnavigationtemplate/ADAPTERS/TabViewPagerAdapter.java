package com.example.mortrza.mybottomnavigationtemplate.ADAPTERS;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabViewPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> fragmentList;
    List<String> titles;

    public TabViewPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentList = new ArrayList<>();
        titles = new ArrayList<>();
    }

    public void addFragment(Fragment fragment,String title){
        fragmentList.add(fragment);
        titles.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
