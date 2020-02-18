package edu.lewisu.cs.example.bestseller;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();
    private final String API_KEY = "42ff06dcd8c04a4cae037a10a43ffd4c";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("api.nytimes.com")
                .appendPath("svc")
                .appendPath("books")
                .appendPath("v3")
                .appendPath("lists.json")
                .appendQueryParameter("api-key", API_KEY)
                .appendQueryParameter("list", "hardcover-fiction");

        Uri nytBooks = builder.build();
        Log.d("url", nytBooks.toString());
        DownloadBook downloadBook = new DownloadBook(this);
        downloadBook.execute(nytBooks);



    }
    private static class DownloadBook extends AsyncTask<Uri, Void, Book>
    {
        private WeakReference<MainActivity> activityWeakReference;

        @Override
        protected Book doInBackground(Uri... uris) {
            OkHttpClient client = new OkHttpClient();
            String jsonData = "";
            try
            {
                URL url = new URL(uris[0].toString());
                Request.Builder builder = new Request.Builder();
                builder .url(url);
                Request request = builder.build();
                Response response = client.newCall(request).execute();
                if(response.body()!=null)
                {
                    jsonData = response.body().string();
                    String title;
                    String author;
                    String description;
                    String amazonUrl;

                    JSONObject results = new JSONObject(jsonData);
                    JSONArray bookList = results.getJSONArray("results");
                    JSONObject bookObject = bookList.getJSONObject(5);
                    amazonUrl = bookObject.getString("amazon_product_url");
                    JSONObject bookDetails = bookObject.getJSONArray("book_details").getJSONObject(0);
                    title = bookDetails.getString("title");
                    author  = bookDetails.getString("author");
                    description = bookDetails.getString("description");

                    Book book = new Book(title, author, amazonUrl, description);
                    return book;
                }
            }
            catch (Exception ex)
            {
                Log.d(TAG, ex.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Book book) {
            MainActivity activity = activityWeakReference.get();
            if(activity == null || activity.isFinishing())
            {
                return;
            }

            TextView titleTextView = activity.findViewById(R.id.titleTextView);
            if (book!=null)
            {
                titleTextView.setText(book.getTitle());

                TextView authorTextView = activity.findViewById(R.id.authorTextView);
                authorTextView.setText(book.getAuthor());

                TextView descriptionTextView = activity.findViewById(R.id.descriptionTextView);
                descriptionTextView.setText(book.getDescription());

                TextView amazonTextView = activity.findViewById(R.id.amazonUrlTextView);
                amazonTextView.setText(book.getAmazon());
            }
            else
            {
                titleTextView.setText(activity.getResources().getString(R.string.download_error));

            }
        }

        public DownloadBook(MainActivity mainActivity) {
            activityWeakReference = new WeakReference<>(mainActivity);

        }


    }
}
