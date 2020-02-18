package edu.lewisu.cs.example.networklab;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();
    private final static String  URL_STRING = "http://cs.lewisu.edu/~howardcy/materials/php/books1.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goButtonClick(View v) {
        DownloadData downloadData = new DownloadData(this);
        downloadData.execute(URL_STRING);
    }



    private static class DownloadData extends AsyncTask<String, Void, String>
    {
        private WeakReference<MainActivity> activityReference;

        public DownloadData(MainActivity activityReference) {
            this.activityReference = new WeakReference<>(activityReference);
        }

        @Override
        protected String doInBackground(String... strings) {
            String jsonData = "";
            HttpURLConnection urlConnection = null;
            try
            {
                URL url = new URL(strings[0]);
                urlConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                Scanner scanner = new Scanner(inputStream);
                scanner.useDelimiter("\\A"); //read until we get to the end of the file
                boolean hasInput = scanner.hasNext();
                if(hasInput)
                {
                    jsonData = scanner.next();
                }
                else
                {
                    return activityReference.get().getResources().getString(R.string.download_error);
                }
            }
            catch (Exception ex)
            {
                Log.d(TAG, ex.toString());
                return activityReference.get().getResources().getString(R.string.download_error);

            }
            finally
            {
                if(urlConnection !=null)
                {
                    urlConnection.disconnect();
                }
            }
            String title;
            String isbn;
            String row;
            StringBuilder results = new StringBuilder();


            try
            {
                JSONArray jsonArray = new JSONArray(jsonData);
                for (int i=0; i<jsonArray.length();i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    title = jsonObject.getString("title");
                    isbn = jsonObject.getString("isbn");
                    row = isbn + "\t" + title + "\n";
                    results.append(row);
                }
            }catch(Exception ex)
            {
                Log.d(TAG, ex.toString());
                return activityReference.get().getResources().getString(R.string.data_error);
            }
            return results.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            MainActivity activity = activityReference.get();
            if(activity == null||activity.isFinishing())
            {
                return;
            }
            TextView textView = activity.findViewById(R.id.displayTextView);
            textView.setText(s);
        }
    }
}


