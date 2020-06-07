package com.ebaba.sangeet;

import android.graphics.Bitmap;
import android.net.Uri;

public class Videodata {
    private final Uri uri;
    private final String name;
    private final int duration;
    private final int size;
    Bitmap thumnb;

    public Videodata(Uri uri, String name, int duration, int size, Bitmap thumnb) {
        this.uri = uri;
        this.name = name;
        this.duration = duration;
        this.size = size;
        this.thumnb=thumnb;
    }

    public Uri getUri() {
        return uri;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public int getSize() {
        return size;
    }

    public Bitmap getThumnb() {
        return thumnb;
    }

    public void setThumnb(Bitmap thumnb) {
        this.thumnb = thumnb;
    }
}
