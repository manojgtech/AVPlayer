package com.ebaba.sangeet.ui.dashboard;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ebaba.sangeet.MyListAdapter;
import com.ebaba.sangeet.MyVideoAdapter;
import com.ebaba.sangeet.R;
import com.ebaba.sangeet.Songs;
import com.ebaba.sangeet.Videodata;

import java.util.ArrayList;
import java.util.HashSet;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    MyVideoAdapter adapter;
    ArrayList<Videodata> songs;
    MediaPlayer mediaPlayer ;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        songs=getAllMedia();
        mediaPlayer = new MediaPlayer();
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.songs_list);
        adapter = new MyVideoAdapter(songs,getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3)
        );
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        EditText searchv=root.findViewById(R.id.searchsongs);
        searchv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                adapter.getFilter().filter(editable.toString());
            }
        });
        return root;
    }
    public ArrayList<Videodata> getAllMedia() {
        HashSet<String> videoItemHashSet = new HashSet<>();
        ArrayList<Videodata> downloadedList = new ArrayList<Videodata>();


        String[] projection = {  MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.SIZE};
        Cursor cursor = getActivity().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        try {

            cursor.moveToFirst();
            int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
            int nameColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
            int durationColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);
            int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);

            do{
                // Log.d("FIL",cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)));
                long id = cursor.getLong(idColumn);
                String name = cursor.getString(nameColumn);
                int duration = cursor.getInt(durationColumn);
                int size = cursor.getInt(sizeColumn);

                Uri contentUri = ContentUris.withAppendedId(
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id);
              //  Bitmap thumbnail =
                      //  getActivity().getContentResolver().loadThumbnail(
                             //   contentUri, new Size(64, 64), null);
               // Bitmap thumb = ThumbnailUtils.createVideoThumbnail(contentUri.getPath(), MediaStore.Images.Media.);

                MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                mmr.setDataSource( getActivity(), contentUri );

             //   if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O_MR1) {
                    Bitmap  bm= mmr.getScaledFrameAtTime( 1000, MediaMetadataRetriever.OPTION_NEXT_SYNC, 300, 400 );

                downloadedList.add(new Videodata(contentUri, name, duration,size ,bm ));
            }while(cursor.moveToNext());

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //ArrayList<String> downloadedList = new ArrayList<>(videoItemHashSet);
        return downloadedList;
    }



}
