package com.josjos.aseloe.api19;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;

import com.josjos.aseloe.api19.Adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.josjos.aseloe.api19.MainActivity.Cari;

public class SearchLayout extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<FilmItems>> {

    static final String EXTRA_CARI = "EXTRA_CARI";
    Locale current;

    @BindView(R.id.toolbar_search) Toolbar toolbar;
    @BindView(R.id.my_recycler_view_id) RecyclerView my_recycler_view;

    String url;
    RecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchlayout_layout);
        current = getResources().getConfiguration().locale;
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        url = getIntent().getStringExtra(EXTRA_CARI);

        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_CARI,url);


        my_recycler_view.setHasFixedSize(true);
        my_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(my_recycler_view.getContext());

        getSupportLoaderManager().initLoader(0,bundle,this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_header,menu);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        searchView.setQueryHint(getResources().getString(R.string.search_hint));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryCari) {

                Bundle bundle = new Bundle();
                bundle.putString(EXTRA_CARI, Cari + current.getDefault().getLanguage() + "&query=" +queryCari);
                extend_RestartLoader(bundle);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return true;
    }

    public void extend_RestartLoader(Bundle bundle){
        getSupportLoaderManager().restartLoader(0,bundle, this);
    }

    @Override
    public Loader<ArrayList<FilmItems>> onCreateLoader(int i, Bundle bundle) {
        String value = "";
        if(bundle != null){
            value = bundle.getString(EXTRA_CARI);
        }
        Log.d("Wei",value);
        return new MyAsyncTaskLoader(this,value);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<FilmItems>> loader, ArrayList<FilmItems> filmItems) {
        my_recycler_view.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setListFilm(filmItems);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<FilmItems>> loader) {
        adapter.setListFilm(null);
    }
}
