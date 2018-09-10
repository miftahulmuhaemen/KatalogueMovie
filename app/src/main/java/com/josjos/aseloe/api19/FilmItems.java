package com.josjos.aseloe.api19;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import lombok.Getter;
import lombok.Setter;

public class FilmItems {

    @Getter @Setter String title, poster_path, overview, release_date;

    public FilmItems(JSONObject object){
        try{
            String title = object.getString("title");
            String poster_path = object.getString("poster_path");
            String overview = object.getString("overview");
            String release_date = object.getString("release_date");

            this.title = title;
            this.poster_path = poster_path;
            this.overview = overview;
            this.release_date = release_date;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
