package com.app.movietap;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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


public class WishMoviesActivity extends ListActivity
{
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_browse_movies);
  }

  @Override
  protected void onResume()
  {
    super.onResume();

    IPersistenceHandler handler = new PersistenceHandler(this);
    List<StoredMovie> movies = handler.loadListWhere(StoredMovie.class, "Status == ?", new String[] {MovieStatus.Wished + ""}, null, null, null);
    _movies = new ArrayList<Movie>(movies.size());

    for(StoredMovie movie : movies)
    {
      new GetMovieOperation().execute(movie.MovieId + "");
    }

    MovieList adapter = new MovieList(WishMoviesActivity.this, _movies);
    _list = (ListView) findViewById(android.R.id.list);
    _list.setAdapter(adapter);
  }

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

  private void addToList(Movie result)
  {
    _movies.add(result);

    MovieList adapter = new MovieList(WishMoviesActivity.this, _movies);
    _list = (ListView) findViewById(android.R.id.list);
    _list.setAdapter(adapter);
  }

  @Override
  public void onListItemClick(ListView l, View v, int position, long id)
  {
    Intent goToDetail = new Intent(WishMoviesActivity.this, MovieDetailActivity.class);
    goToDetail.putExtra("movie", _movies.get(position));
    startActivity(goToDetail);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    getMenuInflater().inflate(R.menu.menu_navigation, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    super.onOptionsItemSelected(item);

    return ActivityTools.HandleOptionsItemSelected(item, this);
  }

  private ListView _list;
  private List<Movie> _movies;
}
