package com.app.movietap;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.movietap.model.Movie;
import com.app.movietap.ui.MovieList;

import java.util.ArrayList;


public class SearchResultActivity extends BaseActivity
{

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search_result);

    _resultList = (ListView) findViewById(R.id.search_listViewResults);
    _resultList.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
      {
        Intent goToDetail = new Intent(SearchResultActivity.this, MovieDetailActivity.class);
        goToDetail.putExtra("movie", _movies.get(position));
        startActivity(goToDetail);
      }
    });
    _movies = getIntent().getExtras().getParcelableArrayList("searchresult");

    MovieList adapter = new MovieList(this, _movies);
    _resultList.setAdapter(adapter);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.search_result, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_settings)
    {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private ArrayList<Movie> _movies;
  private ListView _resultList;
}
