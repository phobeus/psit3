package com.app.movietap;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.app.movietap.model.Movie;


public class MovieDetailActivity extends BaseActivity {

    private Movie _movie;
    private AQuery _aQuery;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        //get information from last activity
        _movie = (Movie)getIntent().getExtras().getParcelable("movie");
        TextView title = (TextView)findViewById(R.id.movieDetail_textView);
        title.setText(_movie.getTitle());

        //save image from the movie locally and display it
        _aQuery = new AQuery(this);
        _aQuery.id(R.id.movieDetail_imageViewPoster).image("http://image.tmdb.org/t/p/w185" + _movie.getPoster());

        //

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
