package com.app.movietap.ui;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.app.movietap.R;
import com.app.movietap.model.Movie;

import java.util.List;

public class MovieList extends ArrayAdapter<Movie>
{
  public MovieList(Activity context, List<Movie> movies)
  {
    super(context, R.layout.movie_row, movies);

    _context = context;
    _movies = movies;
  }

  @Override
  public View getView(int position, View view, ViewGroup parent)
  {
    LayoutInflater inflater = _context.getLayoutInflater();
    View rowView = inflater.inflate(R.layout.movie_row, null, true);
    TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
    TextView txtDesc = (TextView) rowView.findViewById(R.id.description);
    Movie movie = _movies.get(position);
    txtTitle.setText(movie.getTitle());
    if(movie.getReleaseDate() != null)
    {
      txtDesc.setText(movie.getReleaseDate().toString());
    }
    else if(!movie.getOriginalTitle().equals(movie.getTitle()))
    {
      txtDesc.setText(movie.getOriginalTitle());
    }
    _rowQuery = new AQuery(rowView);
    _rowQuery.id(R.id.img).image("http://image.tmdb.org/t/p/w185" + movie.getPoster());

    return rowView;
  }

  private final Activity _context;
  private final List<Movie> _movies;
  private AQuery _rowQuery;
}