package com.ebaba.sangeet;


import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class YoutubeAdapter extends RecyclerView.Adapter<YoutubeAdapter.ViewHolder>{
    private ArrayList<Youtube> listdata;
    private ArrayList<Youtube>  fulllist;
    public  static Context mctx;

    // RecyclerView recyclerView;
    public YoutubeAdapter(ArrayList<Youtube> listdata, Context mctx) {
        this.listdata = listdata;
        this.mctx=mctx;
        fulllist=new ArrayList<>(listdata);

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.youtube_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Youtube song=listdata.get(position);
        String sname=song.getTitle();
        holder.textView.setText(sname);
       // holder.textView.setTag(song);
       holder.itemView.setTag(song);
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Youtube> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(fulllist);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Youtube item : fulllist) {
                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            listdata.clear();
            listdata.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };



    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public ViewHolder(final View itemView) {
            super(itemView);

            this.textView =  itemView.findViewById(R.id.youtbe_titte);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent vint=new Intent(mctx,YoutubeActivity.class);
                    Youtube pos=(Youtube) itemView.getTag();

                    vint.putExtra("vuri",pos.getUrl());
                    vint.putExtra("vname",pos.getTitle());
                    itemView.getContext().startActivity(vint);


                }
            });



        }
    }
}