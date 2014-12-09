package com.app.movietap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.movietap.model.cacheable.Movie;
import com.app.movietap.ui.MovieList;

import java.util.ArrayList;

/**
 * Provides a list of search results that can be clicked
 */
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

  private ArrayList<Movie> _movies;
  private ListView _resultList;
}
