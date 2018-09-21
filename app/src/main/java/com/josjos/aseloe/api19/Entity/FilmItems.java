package com.josjos.aseloe.api19.Entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONObject;

import lombok.Getter;
import lombok.Setter;

import static android.provider.UserDictionary.Words._ID;
import static com.josjos.aseloe.api19.Database.DatabaseContract.NoteColumns.OVERVIEW;
import static com.josjos.aseloe.api19.Database.DatabaseContract.NoteColumns.POSTER_PATH;
import static com.josjos.aseloe.api19.Database.DatabaseContract.NoteColumns.RELEASE_DATE;
import static com.josjos.aseloe.api19.Database.DatabaseContract.NoteColumns.TITLE;
import static com.josjos.aseloe.api19.Database.DatabaseContract.NoteColumns.VOTE_AVERAGE;
import static com.josjos.aseloe.api19.Database.DatabaseContract.getColumnInt;
import static com.josjos.aseloe.api19.Database.DatabaseContract.getColumnString;


public class FilmItems implements Parcelable {

    @Getter @Setter int id;
    @Getter @Setter String title, poster_path, overview, release_date, vote_average;



    public FilmItems(JSONObject object){
        try{

            String title = object.getString("title");
            String poster_path = object.getString("poster_path");
            String overview = object.getString("overview");
            String release_date = object.getString("release_date");
            String vote_avarage = object.getString("vote_average") ;


            this.title = title;
            this.poster_path = poster_path;
            this.overview = overview;
            this.release_date = release_date;
            this.vote_average = vote_avarage;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public FilmItems(){

    }

    public  FilmItems(Cursor cursor){
        this.id = getColumnInt(cursor, _ID);
        this.title = getColumnString(cursor, TITLE);
        this.poster_path = getColumnString(cursor,POSTER_PATH);
        this.overview = getColumnString(cursor,OVERVIEW);
        this.release_date = getColumnString(cursor,RELEASE_DATE);
        this.vote_average = getColumnString(cursor,VOTE_AVERAGE);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.poster_path);
        dest.writeString(this.overview);
        dest.writeString(this.release_date);
        dest.writeString(this.vote_average);
    }

    protected FilmItems(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.poster_path = in.readString();
        this.overview = in.readString();
        this.release_date = in.readString();
        this.vote_average = in.readString();
    }

    public static final Parcelable.Creator<FilmItems> CREATOR = new Parcelable.Creator<FilmItems>() {
        @Override
        public FilmItems createFromParcel(Parcel source) {
            return new FilmItems(source);
        }

        @Override
        public FilmItems[] newArray(int size) {
            return new FilmItems[size];
        }
    };
}
