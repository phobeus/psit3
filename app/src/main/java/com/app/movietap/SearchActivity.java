package com.app.movietap;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.movietap.model.Movie;
import com.app.movietap.tools.ActivityTools;
import com.app.movietap.tools.ApiTools;

import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends BaseActivity
{
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);

    handleIntent(getIntent());

    findViewById(R.id.search_buttonSearch).setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View view)
      {
        performSearch();
      }
    });

    findViewById(R.id.search_buttonPopular).setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View view)
      {
        performPopular();
      }
    });

    findViewById(R.id.search_buttonRated).setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View view)
      {
        performBestRated();
      }
    });

    EditText editText = (EditText) findViewById(R.id.search_searchEditText);
    editText.setOnEditorActionListener(new TextView.OnEditorActionListener()
    {
      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
      {
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_SEARCH)
        {
          performSearch();
          handled = true;
        }
        return handled;
      }
    });
  }

  public void performSearch()
  {
    String query = ((EditText) findViewById(R.id.search_searchEditText)).getText().toString();
    new SearchMovieOperation().execute(query);

    // Hide Keyboard
    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
  }

  public void performPopular()
  {
    new PopularMovieOperation().execute();

    // Hide Keyboard
    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
  }

  public void performBestRated()
  {
    new BestRatedMovieOperation().execute();

    // Hide Keyboard
    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
  }

  @Override
  protected void onNewIntent(Intent intent)
  {
    handleIntent(intent);
  }

  private void handleIntent(Intent intent)
  {

    if (Intent.ACTION_SEARCH.equals(intent.getAction()))
    {
      String query = intent.getStringExtra(SearchManager.QUERY);
      Toast.makeText(SearchActivity.this, "Du suchtest nach " + query, Toast.LENGTH_LONG).show();
    }
  }

  private void openSearchResult(List<Movie> movies)
  {
    Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
    intent.putParcelableArrayListExtra("searchresult", (ArrayList) movies);
    startActivity(intent);
  }

  private class SearchMovieOperation extends AsyncTask<String, Void, List<Movie>>
  {
    @Override
    protected List<Movie> doInBackground(String... params)
    {
      List<Movie> result = ApiTools.searchMovies(params[0]);

      return result;
    }

    @Override
    protected void onPostExecute(List<Movie> result)
    {
      openSearchResult(result);
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

  private class PopularMovieOperation extends AsyncTask<String, Void, List<Movie>>
  {
    @Override
    protected List<Movie> doInBackground(String... params)
    {
      List<Movie> result = ApiTools.popularMovies();

      return result;
    }

    @Override
    protected void onPostExecute(List<Movie> result)
    {
      openSearchResult(result);
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

  private class BestRatedMovieOperation extends AsyncTask<String, Void, List<Movie>>
  {
    @Override
    protected List<Movie> doInBackground(String... params)
    {
      List<Movie> result = ApiTools.bestRatedMovies();

      return result;
    }

    @Override
    protected void onPostExecute(List<Movie> result)
    {
      openSearchResult(result);
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
