package com.ebaba.sangeet;

import android.app.Activity;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{

    private OnItemClickListener mListener;
    private ArrayList<Songs> listdata;
    private ArrayList<Songs>  fulllist;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }


public  static Context mctx;


    private OnItemClickListener recyclerViewClickInterface;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    // RecyclerView recyclerView;
    public MyListAdapter(ArrayList<Songs> listdata,Context mctx) {
        this.listdata = listdata;
        this.mctx=mctx;
        fulllist=new ArrayList<>(listdata);
        this.recyclerViewClickInterface=recyclerViewClickInterface;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.musiclist, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem,mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Songs song=listdata.get(position);
        String sname=song.getName().substring(0,20);
        holder.textView.setText(sname);
        holder.textView.setTag(song.getUri());
        //holder.bind(listdata.get(position), listener);
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
            ArrayList<Songs> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(fulllist);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Songs item : fulllist) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
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

        public ViewHolder(final View itemView,final OnItemClickListener listener) {
            super(itemView);

            this.textView = (TextView) itemView.findViewById(R.id.song_name);
         // final   MediaPlayer mp=new MediaPlayer();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }

//
//        public void bind(final Songs item, final OnItemClickListener listener) {
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override public void onClick(View v) {
//                    listener.onItemClick(item);
//                }
//            });
//        }



    }
}