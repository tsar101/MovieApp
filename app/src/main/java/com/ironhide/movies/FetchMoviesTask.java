package com.ironhide.movies;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by animesh on 30/7/16.
 */
public class FetchMoviesTask extends AsyncTask<String,Void,Movie[]> {
    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p/>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param params The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    private MovieAdapter movieAdapter;

    public FetchMoviesTask(MovieAdapter adapter)
    {
        this.movieAdapter=adapter;

    }
    private static String LogTAG = FetchMoviesTask.class.toString();
    @Override
    protected Movie[] doInBackground(String... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader bufferedReader = null;
        String moviesJsonString = null;
        String sortBy = params[0];

        try{

            Uri.Builder builder = null;
            String base = "https://api.themoviedb.org/3/discover/movie";
            builder = Uri.parse(base).buildUpon();
            builder.appendQueryParameter("sort_by",sortBy);
            builder.appendQueryParameter("api_key",BuildConfig.MovieDBAPIKey);
            Log.i(LogTAG,builder.build().toString());

            URL url = new URL(builder.build().toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer stringBuffer = new StringBuffer();

            if (inputStream == null)
            {
                return null;
            }

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;

            while ((line = bufferedReader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                stringBuffer.append(line + "\n");
            }
            Log.d(LogTAG,stringBuffer.toString());
            return getRelevantDataFromJSON(stringBuffer.toString());
        }
        catch (MalformedURLException e)
        {
            Log.e(LogTAG,e.getMessage());
        }
        catch (IOException e)
        {
            Log.e(LogTAG,e.getMessage());
        }
    return null;
    }

    private Movie[] getRelevantDataFromJSON(String movieString)
    {
        try {
            JSONObject jsonObject = new JSONObject(movieString);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            Movie [] movies = new Movie[jsonArray.length()];
            for(int i=0;i<jsonArray.length();i++)
            {
                String title = jsonArray.getJSONObject(i).getString("original_title");
                String imgUrl =  jsonArray.getJSONObject(i).getString("poster_path");
                String description = jsonArray.getJSONObject(i).getString("overview");
                movies[i] = new Movie(title,imgUrl,description);
            }
            return movies;
        }
        catch (JSONException j)
        {
            Log.e(LogTAG,j.getMessage());
        }
    return null;
    }

    /**
     * <p>Runs on the UI thread after {@link #doInBackground}. The
     * specified result is the value returned by {@link #doInBackground}.</p>
     * <p/>
     * <p>This method won't be invoked if the task was cancelled.</p>
     *
     * @param movies The result of the operation computed by {@link #doInBackground}.
     * @see #onPreExecute
     * @see #doInBackground
     * @see #onCancelled(Object)
     */
    @Override
    protected void onPostExecute(Movie[] movies) {
        super.onPostExecute(movies);
        //movieAdapter.clear();
        movieAdapter.clear();
        movieAdapter.addAll(movies);
    }
}
