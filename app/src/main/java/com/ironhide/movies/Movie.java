package com.ironhide.movies;

/**
 * Created by animesh on 30/7/16.
 */
public class Movie {
    private String movieName;
    private String imgUrl;
    private String description;

    public Movie(String movieName, String imgUrl, String description) {
        this.movieName = movieName;
        this.imgUrl = imgUrl;
        this.description = description;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }


    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
