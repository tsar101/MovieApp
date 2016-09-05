package com.ironhide.movies;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by animesh on 30/7/16.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {
    private String LogTAG = MovieAdapter.class.getName();
    private final static String BaseURL = "http://image.tmdb.org/t/p/w300/";

    public MovieAdapter(Activity context, List<Movie> movies)
    {
        super(context,0,movies);
    }

    /**
     * {@inheritDoc}
     *
     * @param position
     * @param convertView
     * @param parent
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Movie movie = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_listing, parent, false);
        }

        SquareImageView poster = (SquareImageView) convertView.findViewById(R.id.moviePoster);

        if(movie.getImgUrl().equals("null"))
        {
            Picasso.with(convertView.getContext()).load("https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcQ17gHXp36K44nGCZSmy0ceuCAEQG4zBZqFWrvNuRxmFaQSuCuB").into(poster);

        }
        else {
            Picasso.with(convertView.getContext()).load(BaseURL + movie.getImgUrl()).into(poster);
        }
        return convertView;
    }
}
