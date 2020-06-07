package com.ebaba.sangeet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.ListFragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;


public class FragmentAdapter extends FragmentPagerAdapter {
public FragmentAdapter(FragmentManager fm) {
        super(fm);
        }

@Override
public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
        fragment = new RajeevFragment();
        }
        else if (position == 1)
        {
        fragment = new NewWorldFragment();
        }
        else if (position == 2)
        {
        fragment = new HealthFragment();
        }
        return fragment;
        }

@Override
public int getCount() {
        return 3;
        }

@Override
public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
        title = "Rajeev Dixit";
        }
        else if (position == 1)
        {
        title = "Health";
        }
        else if (position == 2)
        {
        title = "New World";
        }
        return title;
        }
        }