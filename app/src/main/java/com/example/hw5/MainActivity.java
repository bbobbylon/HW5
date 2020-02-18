package com.example.hw5;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.jar.JarEntry;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();
    private final static String URL_STRING = "https://api.themoviedb.org/3/movie/now_playing?api_key=5aea6b8c5526b617ed3558cc63040698&language=en-US&page=1";
    private final String API_KEY = "5aea6b8c5526b617ed3558cc63040698";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //dont have to do this, can just do it as a string
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath("now_playing")
                .appendQueryParameter("api_key", API_KEY)
                .appendQueryParameter("language", "en-US")
                .appendQueryParameter("page", "1");

        Uri tmdbMovies = builder.build();
        Log.d("url", tmdbMovies.toString());
DownloadMovie downloadMovie = new DownloadMovie(this);
downloadMovie.execute(tmdbMovies);

    }
    private static class DownloadMovie extends AsyncTask<Uri, Void, Movie>
    {
        private WeakReference<MainActivity> activityWeakReference;
        public DownloadMovie(MainActivity mainActivity)
        {
            activityWeakReference = new WeakReference<>(mainActivity); //avoids memory leaks
        }

        @Override
        protected Movie doInBackground(Uri... uris) {


            //I was not sure whether to use the string version or Uri builder, so i left string version in comments



          /*  String jsonData = "";
            HttpURLConnection urlConnection = null;
            try
            {
                URL url = new URL(URL_STRING);
                urlConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                Scanner scanner = new Scanner(inputStream);
                scanner.useDelimiter("\\A");

                boolean hasInput = scanner.hasNext();
                if(hasInput)
                {
                    jsonData = scanner.next();
                }
            }
            catch(Exception ex)
            {
                Log.d(TAG,ex.getMessage());
            }
            finally
            {
                if(urlConnection !=null)
                {
                    urlConnection.disconnect();
                }
            }
            return null;
            */
            OkHttpClient client = new OkHttpClient();
            String jsonData = "";
            try {
                URL url = new URL(uris[0].toString());
                Request.Builder builder = new Request.Builder();
                builder.url(url);
                Request request = builder.build();
                Response response = client.newCall(request).execute();
                if(response.body() != null)
                {
                    jsonData = response.body().string();
                    String title;
                    String vote_average;
                    String overview;


                    //I would like to implement a poster image of the movie instead of popularity.
                    String popularity;
                    String releaseDate;
                    JSONObject results = new JSONObject(jsonData);
                    JSONArray movieList = results.getJSONArray("results");
                    JSONObject movieObject = movieList.getJSONObject(1);
                    title = movieObject.getString("title");
                    vote_average = movieObject.getString("vote_average");
                    overview = movieObject.getString("overview");
                    popularity = movieObject.getString("popularity");
                    releaseDate = movieObject.getString("release_date");



                    Movie movie = new Movie(title, vote_average, popularity, overview,releaseDate) ;
                    return movie;

                }


            }catch(Exception ex)
            {
                Log.d(TAG, ex.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Movie movie) {
            MainActivity activity = activityWeakReference.get();
            if(activity == null || activity.isFinishing())
            {
                return;
            }
            TextView titleTextView = activity.findViewById(R.id.titleTextView);

            if (movie !=null)
            {
                titleTextView.setText(movie.getTitle());

                TextView vote_averageTextView = activity.findViewById(R.id.vote_averageTextView);
                vote_averageTextView.setText(movie.getVote_average());

                TextView overviewTextView = activity.findViewById(R.id.overviewTextView);
                overviewTextView.setText(movie.getOverview());

                TextView popularityTextView = activity.findViewById(R.id.popularityTextView);
                popularityTextView.setText(movie.getPopularity());

                TextView releaseDateTextView = activity.findViewById(R.id.releaseDateTextView);
                releaseDateTextView.setText(movie.getReleaseDate());

            }
            else
            {
                titleTextView.setText(activity.getResources().getString(R.string.download_error));
            }
        }
    }






}

