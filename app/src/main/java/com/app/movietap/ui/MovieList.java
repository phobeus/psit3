package com.app.movietap.ui;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.app.movietap.R;
import com.app.movietap.model.cacheable.Movie;
import com.app.movietap.tools.ActivityTools;

import java.util.Date;
import java.util.List;

/**
 * ArrayAdapter for movies - shows movies with some basic information and a cached picture
 */
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
    TextView txtDate = (TextView) rowView.findViewById(R.id.date);
    TextView txtDesc = (TextView) rowView.findViewById(R.id.description);
    Movie movie = _movies.get(position);

    txtTitle.setText(movie.getTitle());
    Date releaseDate = movie.getReleaseDate();
    if (releaseDate != null)
    {
      txtDate.setVisibility(View.VISIBLE);
      txtDate.setText("Erschienen am " + ActivityTools.fomateDate(releaseDate));
    } else
    {
      txtDate.setVisibility(View.GONE);
    }
    if (!movie.getOriginalTitle().equals(movie.getTitle()))
    {
      txtTitle.setText(txtTitle.getText() + " (" + movie.getOriginalTitle() + ")");
    }
    if (movie.getVoteCount() > 0)
    {
      txtDesc.setText("Bewertung: " + movie.getVoteAverage() + " (von " + movie.getVoteCount() + " Benutzern)");
    } else
    {
      txtDesc.setText("Noch keine Bewertung");
    }

    _rowQuery = new AQuery(rowView);
    _rowQuery.id(R.id.img).image("http://image.tmdb.org/t/p/w185" + movie.getPoster());

    return rowView;
  }

  private final Activity _context;
  private final List<Movie> _movies;
  private AQuery _rowQuery;
}