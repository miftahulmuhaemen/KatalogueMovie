package com.josjos.aseloe.api19.Entity;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.util.Log;

import lombok.Getter;
import lombok.Setter;

import static com.josjos.aseloe.api19.Database.DatabaseContract.NoteColumns.POSTER_PATH;
import static com.josjos.aseloe.api19.Database.DatabaseContract.NoteColumns.TITLE;
import static com.josjos.aseloe.api19.Database.DatabaseContract.NoteColumns.VOTE_AVERAGE;
import static com.josjos.aseloe.api19.Database.DatabaseContract.getColumnString;

public class WidgetItems {

    @Getter @Setter
    String poster_path;

    public WidgetItems(Cursor cursor){
        this.poster_path = getColumnString(cursor,POSTER_PATH);
    }

    public WidgetItems() {

    }
}
