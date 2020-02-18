package edu.lewisu.cs.example.quizapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = QuizActivity.class.getSimpleName();
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button gameOverButton;
    private TextView answerTextView;
    private TextView gameScoreTextView;
    private TextView lifetimeScoreTextView;
    private Question question;


    private int numQuestions = 5;

    private int currentIndex;
    private int score;
    private int lifetimeScore;

    private static final String QUESTION_URL = "https://opentdb.com/api.php?amount=1&category=18&type=boolean";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quiz);

        trueButton = findViewById(R.id.trueButton);
        falseButton = findViewById(R.id.falseButton);

        trueButton.setOnClickListener(new TrueButtonClickListener());
        falseButton.setOnClickListener(new FalseButtonClickListener());

        nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new NextButtonClickListener());

        answerTextView = findViewById(R.id.answerTextView);
        gameScoreTextView = findViewById(R.id.gameScoreTextView);
        lifetimeScoreTextView = findViewById(R.id.lifetimeScoreTextView);

        //get value from intent
        Intent sender = getIntent();
        lifetimeScore = sender.getIntExtra("lifetime", 0);

        gameOverButton = findViewById(R.id.gameOverButton);
        gameOverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create an intent, set extras and result
                Intent returnIntent = new Intent();
                returnIntent.putExtra("score", score);
                setResult(RESULT_OK, returnIntent);

                finish();
            }
        });



        if(savedInstanceState != null){
            currentIndex = savedInstanceState.getInt("question");
            score = savedInstanceState.getInt("score");
            lifetimeScore = savedInstanceState.getInt("lifetime");
        }


        updateQuestion();
        updateScore();

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
        outState.putInt("question", currentIndex);
        outState.putInt("score", score);
        outState.putInt("lifetime", lifetimeScore);
    }



    private void updateQuestion(){
        DownloadQuestion downloadQuestion = new DownloadQuestion(this);
        downloadQuestion.execute(QUESTION_URL);
        answerTextView.setVisibility(View.INVISIBLE);

    }

    private void updateScore(){
        String scoreString = getResources().getString(R.string.game_score_string, score);
        gameScoreTextView.setText(scoreString);
        scoreString = getResources().getString(R.string.lifetime_score_string, lifetimeScore);
        lifetimeScoreTextView.setText(scoreString);
    }

    private void checkAnswer(Boolean userPress){

        if(userPress.equals(question.answerTrue)){
            answerTextView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            answerTextView.setText(getResources().getText(R.string.correct));
            score ++;
            lifetimeScore++;


        }else{
            answerTextView.setTextColor(getResources().getColor(R.color.colorIncorrect));
            answerTextView.setText(getResources().getText(R.string.incorrect));
        }

        updateScore();
        answerTextView.setVisibility(View.VISIBLE);

        currentIndex++;
        if(currentIndex < numQuestions) {
            nextButton.setVisibility(View.VISIBLE);
        }else{
            gameOverButton.setVisibility(View.VISIBLE);
        }


    }


    private class TrueButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            checkAnswer(true);
            v.setEnabled(false);
            falseButton.setVisibility(View.GONE);

        }
    }

    private class FalseButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            checkAnswer(false);
            v.setEnabled(false);
            trueButton.setVisibility(View.GONE);

        }
    }


    private class NextButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            updateQuestion();
            nextButton.setVisibility(View.GONE);
            trueButton.setVisibility(View.VISIBLE);
            falseButton.setVisibility(View.VISIBLE);
            trueButton.setEnabled(true);
            falseButton.setEnabled(true);
        }
    }
    private static class DownloadQuestion extends AsyncTask<String, Void, Question>
    {
        private WeakReference<QuizActivity> activityReference;

        @Override
        protected Question doInBackground(String... strings) {
            String jsonData = "";
            HttpURLConnection urlConnection = null;


            try{
                URL url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();

                Scanner scanner = new Scanner(inputStream);
                scanner.useDelimiter("\\A");

                boolean hasInput = scanner .hasNext();
                if(hasInput)
                {
                    jsonData = scanner.next();
                }
                else
                {
                    return null;
                }

                JSONObject results = new JSONObject(jsonData);
                JSONArray questions = results.getJSONArray("results");
                JSONObject questionObject = questions.getJSONObject(0);
                String questionText = questionObject.getString("question");

                Boolean correctAnswer =Boolean.parseBoolean(questionObject.getString("correct_answer"));

                Question question = new Question(questionText, correctAnswer);
                return question;
            }catch(Exception ex)
            {
                Log.d(TAG,ex.toString());

            }
            finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }

        DownloadQuestion(QuizActivity context)
        {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected void onPostExecute(Question question) {
            QuizActivity activity = activityReference.get();
            if(activity == null || activity.isFinishing())
            {
                return;
            }
            TextView displayTextView = activity.findViewById(R.id.questionTextView);
            activity.question = question;
            if(question!=null)
            {
                displayTextView.setText(Html.fromHtml(question.getQuestionText()));

            }
            else
            {
                displayTextView.setText(activity.getResources().getString(R.string.download_error));

            }
        }
    }

}
