package com.app.movietap;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.movietap.model.Movie;
import com.app.movietap.tools.ApiTools;
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

    _resultList = (ListView) findViewById(R.id.search_listViewResults);

    handleIntent(getIntent());

    ((Button) findViewById(R.id.search_buttonSearch)).setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View view)
      {
        performSearch();
      }
    });

    EditText editText = (EditText) findViewById(R.id.search_searchEditText);
    editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
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
    // WIP - show the search result in it's own activity
    //Intent intent = createActivity("SearchResultActivity", this.getApplicationContext());
    //intent.putParcelableArrayListExtra("searchresult", (ArrayList)movies);
    //startActivity(intent);

    MovieList adapter = new MovieList(SearchActivity.this, movies);
    _resultList.setAdapter(adapter);
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
