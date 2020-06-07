package com.ebaba.sangeet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.ebaba.sangeet.FragmentAdapter;
import com.ebaba.sangeet.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class RajeevFragment extends Fragment {



    YoutubeAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<Youtube> mylist;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_rajeev, container, false);
         recyclerView =root.findViewById(R.id.list1);
       String[] urls=  RajeevVideos.urls;
        String[] titles=  RajeevVideos.tittes;
        mylist=new ArrayList<Youtube>();
       for(int i=0;i<urls.length;i++){
           mylist.add(new Youtube(titles[i],urls[i]));
       }
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
       adapter=new YoutubeAdapter(mylist,getActivity());
       recyclerView.setAdapter(adapter);


        return root;
    }
}
