package com.ebaba.sangeet.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.ebaba.sangeet.FragmentAdapter;
import com.ebaba.sangeet.R;
import com.google.android.material.tabs.TabLayout;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    TabLayout tabLayout;
     ViewPager viewPager;
     FragmentAdapter adapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        tabLayout=root.findViewById(R.id.tabs);
        viewPager  =root.findViewById(R.id.viewpager);
        tabLayout.addTab(tabLayout.newTab().setText("Rajeev Dixit"));
        tabLayout.addTab(tabLayout.newTab().setText("New World"));
        tabLayout.addTab(tabLayout.newTab().setText("Health"));
        adapter = new FragmentAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        return root;
    }



}
