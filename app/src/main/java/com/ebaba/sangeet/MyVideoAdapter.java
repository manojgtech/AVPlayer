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

public class MyVideoAdapter extends RecyclerView.Adapter<MyVideoAdapter.ViewHolder>{
    private ArrayList<Videodata> listdata;
    private ArrayList<Videodata>  fulllist;
public  static Context mctx;

    // RecyclerView recyclerView;
    public MyVideoAdapter(ArrayList<Videodata> listdata, Context mctx) {
        this.listdata = listdata;
        this.mctx=mctx;
        fulllist=new ArrayList<>(listdata);

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.videolist, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Videodata song=listdata.get(position);
        String sname=song.getName();
        holder.textView.setText(sname);
     holder.itemView.setTag(song);
     holder.thumbpic.setImageBitmap(song.getThumnb());
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
            ArrayList<Videodata> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(fulllist);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Videodata item : fulllist) {
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
        public ImageView thumbpic;
        public ViewHolder(final View itemView) {
            super(itemView);

            this.textView = (TextView) itemView.findViewById(R.id.vname);
            this.thumbpic=itemView.findViewById(R.id.vthumb);
          final   MediaPlayer mp=new MediaPlayer();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent vint=new Intent(mctx,VideoActivty.class);
                     Videodata pos=(Videodata) view.getTag();
                    Bundle b=new Bundle();
                    b.putString("title", pos.getName());
                    b.putInt("duration", pos.getDuration());
                    b.putInt("size", pos.getSize());
                    b.putString("uri",pos.getUri().toString());
                    vint.putExtras(b);
                    itemView.getContext().startActivity(vint);


                }
            });



        }
    }
}