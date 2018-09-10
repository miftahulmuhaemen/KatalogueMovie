package com.josjos.aseloe.api19.Fragements;


import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.josjos.aseloe.api19.Adapter.RecyclerViewAdapter;
import com.josjos.aseloe.api19.FilmItems;
import com.josjos.aseloe.api19.MyAsyncTaskLoader;
import com.josjos.aseloe.api19.R;

import java.util.ArrayList;
import java.util.Locale;
import static com.josjos.aseloe.api19.MainActivity.PlayingNow;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilmPlayingnow extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<FilmItems>> {

    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    Locale current;

    public FilmPlayingnow() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        current = getResources().getConfiguration().locale;
        recyclerView = (RecyclerView) inflater.inflate(R.layout.recylerview, container, false);
        adapter = new RecyclerViewAdapter(recyclerView.getContext());
        getLoaderManager().initLoader(0, null,  this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    @Override
    public Loader<ArrayList<FilmItems>> onCreateLoader(int i, Bundle bundle) {
        return new MyAsyncTaskLoader(getActivity(),PlayingNow + current.getDefault().getCountry().toLowerCase());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<FilmItems>> loader, ArrayList<FilmItems> filmItems) {
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setListFilm(filmItems);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<FilmItems>> loader) {
        adapter.setListFilm(null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}