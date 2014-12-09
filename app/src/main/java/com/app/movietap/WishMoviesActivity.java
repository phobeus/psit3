package com.app.movietap;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.movietap.model.StoredMovie;
import com.app.movietap.model.cacheable.Movie;
import com.app.movietap.model.cacheable.MovieStatus;
import com.app.movietap.tools.ActivityTools;
import com.app.movietap.tools.ApiTools;
import com.app.movietap.tools.IPersistenceHandler;
import com.app.movietap.tools.PersistenceHandler;
import com.app.movietap.ui.MovieList;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides a list of movies that the user wishes to own
 */
public class WishMoviesActivity extends BaseActivity
{
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_wish_movies);

    _resultList = (ListView) findViewById(R.id.wish_movies_listViewResults);
    _resultList.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
      {
        Intent goToDetail = new Intent(WishMoviesActivity.this, MovieDetailActivity.class);
        goToDetail.putExtra("movie", _movies.get(position));
        startActivity(goToDetail);
      }
    });
  }

  @Override
  protected void onResume()
  {
    super.onResume();

    if (_moviesAdded == 0)
    {
      IPersistenceHandler handler = new PersistenceHandler(this);
      List<StoredMovie> movies = handler.loadListWhere(StoredMovie.class, "Status == ?", new String[]{MovieStatus.Wished + ""}, null, null, null);
      _movies = new ArrayList<Movie>(movies.size());

      if (movies.size() == 0)
      {
        MovieList adapter = new MovieList(WishMoviesActivity.this, new ArrayList<Movie>());
        _resultList.setAdapter(adapter);
      }

      for (StoredMovie movie : movies)
      {
        synchronized (this)
        {
          _moviesAdded++;
        }
        new GetMovieOperation().execute(movie.MovieId + "");
      }
    }
  }

  private void addToList(Movie result)
  {
    _movies.add(result);
    synchronized (this)
    {
      _moviesAdded--;
    }

    if (_moviesAdded == 0)
    {
      synchronized (this)
      {
        MovieList adapter = new MovieList(WishMoviesActivity.this, _movies);
        _resultList.setAdapter(adapter);
      }
    }
  }

  /**
   * A sub-class that loads movies asynchronously
   */
  private class GetMovieOperation extends AsyncTask<String, Void, Movie>
  {
    @Override
    protected Movie doInBackground(String... params)
    {
      Movie result = ApiTools.getMovie(WishMoviesActivity.this, Integer.parseInt(params[0]));

      return result;
    }

    @Override
    protected void onPostExecute(Movie result)
    {
      addToList(result);
    }

    @Override
    protected void onPreExecute()
    {
    }

    @Override
    protected void onProgressUpdate(Void... values)
    {
    }
  }

  private int _moviesAdded;
  private List<Movie> _movies;
  private ListView _resultList;
}
