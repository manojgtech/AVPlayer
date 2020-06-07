package com.ebaba.sangeet.ui.home;

import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;

import com.ebaba.sangeet.MyListAdapter;
import com.ebaba.sangeet.R;
import com.ebaba.sangeet.Songs;
import com.ebaba.sangeet.VideoActivty;
import com.ebaba.sangeet.Videodata;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment  {

    private HomeViewModel homeViewModel;
    MyListAdapter adapter;
    ArrayList<Songs> songs;
    MediaPlayer mediaPlayer ;
    ImageButton prevbtn,playbtn,pausebtn,nextbtn;
    SlidingPaneLayout panel;
    ArrayList<Songs> playlist;
    int cur_Song=-1;
    TextView fsong_name;
    SharedPreferences sharedpreferences;
    private ProgressDialog progress;
    Handler handler;
    Runnable runnable;
    SeekBar seekbar;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "duration";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        progress=new ProgressDialog(getActivity());
        progress.setMessage("Downloading Music");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
        final int totalProgressTime = 100;
        final Thread t = new Thread() {
            @Override
            public void run() {
                int jumpTime = 0;

                while(jumpTime < totalProgressTime) {
                    try {
                        sleep(200);
                        jumpTime += 5;
                        progress.setProgress(jumpTime);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        panel=root.findViewById(R.id.footerpanel);
        songs=getAllMedia();
mediaPlayer=new MediaPlayer();
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.songs_list);
        adapter = new MyListAdapter(songs, getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        progress.dismiss();
      //  registerForContextMenu(recyclerView);
        EditText searchv=root.findViewById(R.id.searchsongs);
        fsong_name= root.findViewById(R.id.fsong_title);
        playlist=new ArrayList<>();
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
        pausebtn= root.findViewById(R.id.pausebtn);
        playbtn= root.findViewById(R.id.playbtn);
        pausebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
                playbtn.setVisibility(View.VISIBLE);
                pausebtn.setVisibility(View.GONE);
            }
        });

        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                playbtn.setVisibility(View.GONE);
                pausebtn.setVisibility(View.VISIBLE);

            }
        });
        adapter.setOnItemClickListener(new MyListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
               cur_Song=position;
                if(mediaPlayer!=null && mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer=null;
                }
                 playsong();

            }
        });

        //goto musicolayer full

        if(mediaPlayer!=null && mediaPlayer.isPlaying()){
            panel.setVisibility(View.VISIBLE);
        }

        seekbar=root.findViewById(R.id.player_seekbar);

        seekbar.setProgress(mediaPlayer.getDuration());


        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                 mediaPlayer.seekTo(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return root;
    }




    //play song

    void playsong(){


        try {
          int  cursong=0;
            if(cur_Song==-1) {
                 cursong = sharedpreferences.getInt("cur_Song", 0);
            }else{
                 cursong=cur_Song;
            }
            Songs song = songs.get(cursong);


            Uri uri = song.getUri();
            if(mediaPlayer==null){
                mediaPlayer=new MediaPlayer();
                //mediaPlayer=null;
            }else{
                mediaPlayer.reset();
            }

            panel.setVisibility(View.VISIBLE);
            fsong_name.setText(song.getName().substring(0,20));

            mediaPlayer.setDataSource(getActivity(),uri);
            mediaPlayer.prepare();
           // seekbar.setMax(mediaPlayer.getDuration());

            mediaPlayer.start();
            seekbar.setMax(mediaPlayer.getDuration());

            if(cur_Song==0){
                if(songs.size()>1){
                    cur_Song++;
                }
            }else if((cur_Song+1)==songs.size()){
                cur_Song=0;
            }else{
                cur_Song++;
            }
            playCycle();
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt("cur_song", cur_Song);
            editor.apply();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    playsong();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        fsong_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vint=new Intent(getActivity(), VideoActivty.class);
                Songs pos= songs.get(cur_Song);
                mediaPlayer.release();
                Bundle b=new Bundle();
                b.putString("title", pos.getName());
                b.putInt("duration", pos.getDuration());
                b.putInt("size", pos.getSize());
                b.putString("uri",pos.getUri().toString());
                vint.putExtras(b);
              //  getActivity().startActivity(vint);

            }
        });




    }
//

    public void playCycle(){
        seekbar.setProgress(mediaPlayer.getCurrentPosition());
        handler=new Handler();
        if(mediaPlayer.isPlaying()){
            runnable=new Runnable() {
                @Override
                public void run() {
                  playCycle();
                }
            };
            handler.postDelayed(runnable, 1000);
        }
    }


//create playlist

    public void setPlaylist(int pos){
        int nos=songs.size();
        for(int i=pos; i<nos;i++){
            playlist.add(songs.get(i));
        }
    }

    public ArrayList<Songs> getAllMedia() {

        HashSet<String> videoItemHashSet = new HashSet<>();
        ArrayList<Songs> downloadedList = new ArrayList<Songs>();


        String[] projection = {  MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.SIZE};
        Cursor cursor = getActivity().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        try {

            cursor.moveToFirst();
            int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
            int nameColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
            int durationColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);
            int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE);

            do{
                // Log.d("FIL",cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
                long id = cursor.getLong(idColumn);
                String name = cursor.getString(nameColumn);
                int duration = cursor.getInt(durationColumn);
                int size = cursor.getInt(sizeColumn);

                Uri contentUri = ContentUris.withAppendedId(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);

                downloadedList.add(new Songs(contentUri, name, duration,size  ));
            }while(cursor.moveToNext());

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //ArrayList<String> downloadedList = new ArrayList<>(videoItemHashSet);
        return downloadedList;
    }

    public void onResume() {
        super.onResume();
       playsong();
// add your code here which executes when the Fragment is visible and intractable.
    }

    public void onPause() {
        super.onPause();
        mediaPlayer.pause();
// add your code here which executes when user leaving the current fragment or fragment is no longer intractable.
    }

    public void onStop() {
        super.onStop();
        mediaPlayer.release();
// add your code here which executes Fragment going to be stopped.
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
        mediaPlayer=null;
    }
}
