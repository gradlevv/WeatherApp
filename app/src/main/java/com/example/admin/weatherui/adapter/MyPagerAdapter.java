package com.example.admin.weatherui.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.example.admin.weatherui.fragments.FirstFragment;

public class MyPagerAdapter extends FragmentStatePagerAdapter {


    SparseArray<FirstFragment> firstFragment;

    public MyPagerAdapter(FragmentManager fragmentManager, SparseArray<FirstFragment> fragments) {
        super(fragmentManager);
        this.firstFragment = fragments;
        Log.d("Constructor", "Executed!");
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        Log.d("getCount", "Executed!");
        return firstFragment.size();
    }


    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {

       return firstFragment.valueAt(position);

//        switch (position) {
//            case 0: // Fragment # 0 - This will show FirstFragment
//                Log.d("Case ",String.valueOf(position));
//                return new FirstFragment();
//            default:
//                return new FirstFragment();
//        }
    }

//    @Override
//    public long getItemId(int position) {
//        return firstFragment.keyAt(position);
//    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;


    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }


    // Returns the page title for the top indicator
    // @Override
    //  public CharSequence getPageTitle(int position) {
    //     return  titles[position];
    // }

}