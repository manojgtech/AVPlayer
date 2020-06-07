package com.ebaba.sangeet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    MyListAdapter adapter;
    ArrayList<Songs> songs;
    MediaPlayer mediaPlayer ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        File root=new File(Environment.getExternalStorageDirectory().getName());
//         songs=getAllMedia();
//         mediaPlayer = new MediaPlayer();
//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.songs_list);
//        adapter = new MyListAdapter(songs,this);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
//                DividerItemDecoration.VERTICAL));
//        recyclerView.setAdapter(adapter);
//        EditText searchv=findViewById(R.id.searchsongs);
//        searchv.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                adapter.getFilter().filter(editable.toString());
//            }
//        });
//  ImageButton ib=findViewById(R.id.prevbtn);
//  ib.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//          Intent fin=new Intent(MainActivity.this,MusicActivity.class);
//          startActivity(fin);
//      }
//  });

    }

    public void getFilter(){

    }




    public ArrayList<File> walkdir(File dir) {
        String pdfPattern = ".mp3";
         ArrayList<File> colls  =new ArrayList<File>();
        File[] listFile = dir.listFiles();
     //  Log.d("DIRS" , "num:"+listFile.length);

            for (File f:listFile){

                if(f.isFile()){
                    //if (listFile[i].getName().endsWith(pdfPattern)){
                        //Do what ever u want
                       // Toast.makeText(this, listFile[i].getName(), Toast.LENGTH_LONG).show();
                       // Log.d("File",f.getName());
                          colls.add(f);

                   // }
                }
            }

        //Log.d("MSIc", listFile.toString());
        return  colls;
    }

    public ArrayList<Songs> getAllMedia() {
        HashSet<String> videoItemHashSet = new HashSet<>();
        ArrayList<Songs> downloadedList = new ArrayList<Songs>();


        String[] projection = {  MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.SIZE};
        Cursor cursor = getApplicationContext().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
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
}
