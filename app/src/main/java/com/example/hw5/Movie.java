package com.example.hw5;


    public class Movie {
        private String title;
        private String vote_average;
        private String popularity;
        private String overview;
        private String releaseDate;



        public Movie(String title, String vote_average, String popularity, String overview, String releaseDate) {
            this.title = title;
            this.vote_average = vote_average;
            this.popularity = popularity;
            this.overview = overview;
            this.releaseDate = releaseDate;


        }

        public Movie(String title) {

            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getVote_average() {
            return vote_average;
        }

        public void setVote_average(String vote_average) {
            this.vote_average = vote_average;
        }


        public String getOverview() {
            return overview;
        }

        public void setOverview(String description) {
            this.overview = overview;
        }

        public String getPopularity() {
            return popularity;
        }

        public void setPopularity(String popularity) {
            this.popularity = popularity;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }


    }


