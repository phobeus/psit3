package com.app.movietap;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.app.movietap.model.cacheable.Movie;
import com.app.movietap.tools.ApiTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides search logic for different types of searches and calls the
 * according activities.
 */
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

  @Override
  protected void onNewIntent(Intent intent)
  {
    handleIntent(intent);
  }

  private void performSearch()
  {
    String showAdult = ((Switch) findViewById(R.id.search_switchShowAdult)).isChecked() + "";
    String year = ((EditText) findViewById(R.id.search_editTextYear)).getText().toString();
    String query = ((EditText) findViewById(R.id.search_searchEditText)).getText().toString();
    new SearchMovieOperation().execute(query, showAdult, year);
    hideKeyboard();
  }

  private void hideKeyboard()
  {
    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
  }

  private void performPopular()
  {
    new PopularMovieOperation().execute();
    hideKeyboard();
  }

  private void performBestRated()
  {
    new BestRatedMovieOperation().execute();
    hideKeyboard();
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

  /**
   * Private sub-class for async tasks involving loading data from the network.
   */
  private class SearchMovieOperation extends AsyncTask<String, Void, List<Movie>>
  {
    @Override
    protected List<Movie> doInBackground(String... params)
    {
      List<Movie> result = ApiTools.searchMovies(SearchActivity.this, params[0], params[1], params[2]);

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

  /**
   * Private sub-class for async tasks involving loading data from the network.
   */
  private class PopularMovieOperation extends AsyncTask<String, Void, List<Movie>>
  {
    @Override
    protected List<Movie> doInBackground(String... params)
    {
      List<Movie> result = ApiTools.popularMovies(SearchActivity.this);

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

  /**
   * Private sub-class for async tasks involving loading data from the network.
   */
  private class BestRatedMovieOperation extends AsyncTask<String, Void, List<Movie>>
  {
    @Override
    protected List<Movie> doInBackground(String... params)
    {
      List<Movie> result = ApiTools.bestRatedMovies(SearchActivity.this);

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
