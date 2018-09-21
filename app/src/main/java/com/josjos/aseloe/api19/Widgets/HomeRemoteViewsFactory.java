package com.josjos.aseloe.api19.Widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Binder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.josjos.aseloe.api19.Entity.FilmItems;
import com.josjos.aseloe.api19.Entity.WidgetItems;
import com.josjos.aseloe.api19.R;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import lombok.Getter;

import static com.josjos.aseloe.api19.Adapter.RecyclerViewAdapter.url_poster;
import static com.josjos.aseloe.api19.Database.DatabaseContract.NoteColumns.CONTENT_URI;
public class HomeRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    @Getter  private ArrayList<WidgetItems> mWidgetItems = new ArrayList<>();
    private Context mContext;
    private int mAppWidgetId;

    public HomeRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

        final long identityToken = Binder.clearCallingIdentity();

        Cursor cursor = mContext.getContentResolver().query(CONTENT_URI, null, null, null, null);

        WidgetItems itemses;
        if (cursor != null) {
            if (cursor.moveToFirst()){
                do {
                    itemses = new WidgetItems(cursor);
                    mWidgetItems.add(itemses);
                    cursor.moveToNext();
                } while (!cursor.isAfterLast());
            }
            cursor.close();
        }

        Binder.restoreCallingIdentity(identityToken);

    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        final WidgetItems data = getMWidgetItems().get(position);

        Bitmap bmp = null;
        try{
            bmp = Glide.with(mContext)
                    .asBitmap()
                    .load(url_poster + data.getPoster_path())
                    .submit()
                    .get();

        }catch (InterruptedException | ExecutionException e){
            Log.d("Widget Load Error","error");
        }

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        rv.setImageViewBitmap(R.id.imgv_gambar_film, bmp);


        Bundle extras = new Bundle();
        extras.putInt(HomeWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imgv_gambar_film, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
