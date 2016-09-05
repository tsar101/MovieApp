package com.ironhide.movies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends ActionBarActivity {
    private static String LogTAG = MainActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String [] sampStrings={"Hello ld!","Hola World !","This is great"};

        GridView gridView = (GridView) findViewById(R.id.gridView);

//        ArrayAdapter<String> gridViewAdapter = new ArrayAdapter<String>(this,R.layout.movie_listing);
//        gridViewAdapter.addAll(sampStrings);
//        gridView.setAdapter(gridViewAdapter);
//

        final MovieAdapter movieAdapter = new MovieAdapter(this, new ArrayList<Movie>());
        gridView.setAdapter(movieAdapter);

        //FetchMoviesTask fetchMoviesTask = new FetchMoviesTask(movieAdapter);
        //fetchMoviesTask.execute(this);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(),DetailActivity.class);
                intent.putExtra(DetailActivity.MovieName,movieAdapter.getItem(position).getMovieName());
                intent.putExtra(DetailActivity.MoviePoster,movieAdapter.getItem(position).getImgUrl());
                intent.putExtra(DetailActivity.MovieDescription,movieAdapter.getItem(position).getDescription());
                startActivity(intent);

                Log.d(LogTAG,movieAdapter.getItem(position).getMovieName());
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedOption = item.getItemId();
        if(selectedOption == R.id.action_settings)
        {
            Log.i(LogTAG,"Settings Selected");
            startActivity(new Intent(this,SettingsActivity.class));
        }
    return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GridView gridView = (GridView) findViewById(R.id.gridView);
        MovieAdapter movieAdapter = (MovieAdapter) gridView.getAdapter();
        FetchMoviesTask fetchMoviesTask = new FetchMoviesTask(movieAdapter);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sortBy = sharedPreferences.getString(getString(R.string.sort_by),"popularity.desc");
        fetchMoviesTask.execute(sortBy);
        String[] values=getResources().getStringArray(R.array.sort_by_options_values);
        int i=-1;
        for(String value:values)
        {   i++;
            if(value.equals(sortBy))
            {
                getSupportActionBar().setTitle(getResources().getStringArray(R.array.sort_by_options)[i]);
            }
        }

    }
}
