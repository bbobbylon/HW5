package com.example.hw5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>
{

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        this.clickHandler = clickHandler;
    }

    private final MovieAdapterOnClickHandler clickHandler;
    public interface MovieAdapterOnClickHandler
    {
        void onClick(Movie movie);
    }
    private List<Movie> movies;
    private Context context;



    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View  view = layoutInflater.inflate(R.layout.movie_list_item, parent, false);

        return new MovieViewHolder((view));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
    Movie movie = movies.get(position);
    String title = movie.getTitle();
    String vote_average = movie.getVote_average() ;
    //String popularity = movie.getPopularity();
    //String overview = movie.getOverview();
    //String releaseDate = movie.getReleaseDate();
    //I did not have a rank element in my results array of movies
        //I was trying to sort by vote average or popularity
        //but I kept getting a wrong argument count error. I wanted to sort by vote average as a
        //decimal and title as a string, but it was not working in srtings.xml
        String titleRank = context.getResources().getString(R.string.rank_title, vote_average, title);
    holder.movieDataTextView.setText(title);
    }

    @Override
    public int getItemCount()
    {
        if (movies!= null)
        {
            return movies.size();
        }
        return 0;
    }
    public void setMovieData(List<Movie> movieData)
    {
        movies = movieData;
        notifyDataSetChanged();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie movie = movies.get(adapterPosition);
            clickHandler.onClick(movie);
        }

        private final TextView movieDataTextView;


        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieDataTextView = itemView.findViewById(R.id.movieDataTextView);
            itemView.setOnClickListener(this);

        }

    }
}
