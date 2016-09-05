package com.ironhide.movies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    public static final String MovieName = "Movie";
    public static final String MovieDescription = "MovieDescription";
    public static final String MoviePoster = "Poster";

    private final static String BaseURL = "http://image.tmdb.org/t/p/original/";
    private final static String LogTAG = DetailActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String movieName = intent.getStringExtra(MovieName);
        String description = intent.getStringExtra(MovieDescription);
        String posterUrl = intent.getStringExtra(MoviePoster);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setTitle(movieName);
        TextView nameView = (TextView) findViewById(R.id.name);
        nameView.setText(movieName);
        TextView descriptionView = (TextView) findViewById(R.id.description);
        descriptionView.setText(description);
        ImageView imageView = (ImageView) findViewById(R.id.moviePoster);
        Picasso.with(this).load(BaseURL+posterUrl).into(imageView);
    }
}
