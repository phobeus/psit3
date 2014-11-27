package com.app.movietap;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.app.movietap.model.Movie;
import com.app.movietap.tools.ActivityTools;
import com.app.movietap.tools.ApiTools;


public class MovieDetailActivity extends BaseActivity {

    private Movie _movie;
    private AQuery _aQuery;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        //get information from last activity
        _movie = (Movie)getIntent().getExtras().getParcelable("movie");

        //set stuff
        TextView title = (TextView)findViewById(R.id.movieDetail_textViewTitle);
        title.setText(_movie.getTitle());

        //save image from the movie locally and display it
        _aQuery = new AQuery(this);
        _aQuery.id(R.id.movieDetail_imageViewPoster).image("http://image.tmdb.org/t/p/w300" + _movie.getPoster());






        //moreinfo of the same movie
        Movie detailedMovie = ApiTools.getMovie(_movie.getId());

        //set new stuff from more detailedMovie
        TextView genres = (TextView)findViewById(R.id.movieDetail_textViewGenre);
        genres.setText(detailedMovie.getGenres());

        TextView description = (TextView)findViewById(R.id.movieDetail_textViewDescription);
        description.setText(detailedMovie.getOverview());

        TextView subtitle = (TextView)findViewById(R.id.movieDetail_textViewSubtitle);
        subtitle.setText(detailedMovie.getTagline());

        TextView production = (TextView)findViewById(R.id.movieDetail_textViewProduction);
        production.setText(detailedMovie.getProductionCompanies());

        TextView release = (TextView)findViewById(R.id.movieDetail_textViewReleased);
        release.setText(ActivityTools.fomateDate(detailedMovie.getReleaseDate()));

        TextView rating = (TextView)findViewById(R.id.movieDetail_textViewRating);
        rating.setText(Double.toString(detailedMovie.getVoteAverage()));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
