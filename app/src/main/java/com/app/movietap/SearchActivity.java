package com.app.movietap;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.app.movietap.model.Movie;
import com.app.movietap.tools.APITools;
import com.app.movietap.ui.MovieList;

import java.util.List;


public class SearchActivity extends BaseActivity
{
  private ListView _resultList;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);

    _resultList = (ListView) findViewById(R.id.search_listView);

    handleIntent(getIntent());

    ((Button) findViewById(R.id.button_search)).setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View view)
      {
        String query = ((EditText) findViewById(R.id.search_searchBox)).getText().toString();
        new SearchMovieOperation().execute(query);

        // Hide Keyboard
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
      }
    });
  }

  @Override
  protected void onNewIntent(Intent intent)
  {
    handleIntent(intent);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings)
    {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  private void handleIntent(Intent intent)
  {

    if (Intent.ACTION_SEARCH.equals(intent.getAction()))
    {
      String query = intent.getStringExtra(SearchManager.QUERY);
      Toast.makeText(SearchActivity.this, "Du suchtest nach " + query, Toast.LENGTH_LONG).show();
    }
  }

  private void setListResult(List<Movie> movies)
  {
    MovieList adapter = new MovieList(SearchActivity.this, movies);
    _resultList.setAdapter(adapter);
  }

  private class SearchMovieOperation extends AsyncTask<String, Void, List<Movie>>
  {
    @Override
    protected List<Movie> doInBackground(String... params)
    {
      List<Movie> result = APITools.searchMovies(params[0]);

      return result;
    }

    @Override
    protected void onPostExecute(List<Movie> result)
    {
      setListResult(result);
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

}
