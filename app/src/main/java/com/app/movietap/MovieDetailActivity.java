package com.app.movietap;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.app.movietap.tools.ApiTools;

import org.json.JSONException;
import org.json.JSONObject;


public class MovieDetailActivity extends BaseActivity {

    private AQuery _rowQuery;

    protected TextView movieTitleText;
    String moviePoster;
    String movieRelease;
    String movieVote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        JSONObject movieObject = ApiTools.getMovie(54320);
        try {
            String backdrop =  movieObject.getString("backdrop_path");
            Toast.makeText(MovieDetailActivity.this, "backdrop " + backdrop, Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //get information from last activity
        String movieTitle = getIntent().getStringExtra("title");
        String moviePoster = getIntent().getStringExtra("poster");
        String movieRelease = getIntent().getStringExtra("release");
        String movieVote = getIntent().getStringExtra("vote");

        moviePoster = "http://image.tmdb.org/t/p/w185" + moviePoster;

        //http://stackoverflow.com/questions/2471935/how-to-load-an-imageview-by-url-in-android






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
