package com.app.movietap;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.app.movietap.model.cacheable.Movie;
import com.app.movietap.model.cacheable.MovieStatus;
import com.app.movietap.model.MovieSharedStatus;
import com.app.movietap.model.StoredMovie;
import com.app.movietap.tools.ActivityTools;
import com.app.movietap.tools.ApiTools;
import com.app.movietap.tools.IPersistenceHandler;
import com.app.movietap.tools.PersistenceHandler;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;

import java.util.Date;


public class MovieDetailActivity extends BaseActivity
{
  private Movie _movie;
  private AQuery _aQuery;
  private StoredMovie _storedMovie;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movie_detail);

    //set drawable icons
    final Drawable drawableRemember = getResources().getDrawable(R.drawable.ic_library);
    final Drawable drawableWish = getResources().getDrawable(R.drawable.ic_gift);
    final Drawable drawableRememberAdded = getResources().getDrawable(R.drawable.ic_library_added);
    final Drawable drawableWishAdded = getResources().getDrawable(R.drawable.ic_gift_added);

    //get information from last activity
    _movie = (Movie) getIntent().getExtras().getParcelable("movie");

    //set stuff
    TextView title = (TextView) findViewById(R.id.movieDetail_textViewTitle);
    title.setText(_movie.getTitle());

    //save image from the movie locally and display it
    _aQuery = new AQuery(this);
    _aQuery.id(R.id.movieDetail_imageViewPoster).image("http://image.tmdb.org/t/p/w500" + _movie.getPoster()); /*sizes are 500 or 780*/

    //check if movie exists in Wishlist
    IPersistenceHandler handler = new PersistenceHandler(this);
    _storedMovie = handler.loadWhere(StoredMovie.class, "MovieId = ? ", new String[]{_movie.getId() + ""});
    //change Image, to see that move is stored
    if (_storedMovie != null)
    {
      if (_storedMovie.Status == MovieStatus.Remembered)
      {
        ((Button) findViewById(R.id.movieDetail_buttonRemember)).setCompoundDrawablesWithIntrinsicBounds(drawableRememberAdded, null, null, null);
      } else if (_storedMovie.Status == MovieStatus.Wished)
      {
        ((Button) findViewById(R.id.movieDetail_buttonWish)).setCompoundDrawablesWithIntrinsicBounds(drawableWishAdded, null, null, null);
      }
    }

    //handle the adding or removing of movies from a list via the detail view
    Button rememberButton = (Button) findViewById(R.id.movieDetail_buttonRemember);
    rememberButton.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View view)
      {
        IPersistenceHandler handler = new PersistenceHandler(MovieDetailActivity.this);
        if (_storedMovie != null)
        {
          if (_storedMovie.Status == MovieStatus.Remembered)
          {
            handler.deleteWhere(StoredMovie.class, "Id = ?", new String[]{_storedMovie.Id + ""});
            ((Button) findViewById(R.id.movieDetail_buttonRemember)).setCompoundDrawablesWithIntrinsicBounds(drawableRemember, null, null, null);
            _storedMovie = null;
          }
        } else
        {
          _storedMovie = new StoredMovie(_movie);
          _storedMovie.Status = MovieStatus.Remembered;
          _storedMovie.Shared = MovieSharedStatus.WithNobody;
          _storedMovie.Id = handler.save(_storedMovie);
          ((Button) findViewById(R.id.movieDetail_buttonRemember)).setCompoundDrawablesWithIntrinsicBounds(drawableRememberAdded, null, null, null);
        }
      }
    });

    Button wishButton = (Button) findViewById(R.id.movieDetail_buttonWish);
    wishButton.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View view)
      {
        IPersistenceHandler handler = new PersistenceHandler(MovieDetailActivity.this);
        if (_storedMovie != null)
        {
          if (_storedMovie.Status == MovieStatus.Wished)
          {
            handler.deleteWhere(StoredMovie.class, "Id = ?", new String[]{_storedMovie.Id + ""});
            ((Button) findViewById(R.id.movieDetail_buttonWish)).setCompoundDrawablesWithIntrinsicBounds(drawableWish, null, null, null);
            _storedMovie = null;
          }
        } else
        {
          _storedMovie = new StoredMovie(_movie);
          _storedMovie.Status = MovieStatus.Wished;
          _storedMovie.Shared = MovieSharedStatus.WithNobody;
          _storedMovie.Id = handler.save(_storedMovie);
          ((Button) findViewById(R.id.movieDetail_buttonWish)).setCompoundDrawablesWithIntrinsicBounds(drawableWishAdded, null, null, null);
        }
      }
    });


    //moreinfo of the same movie
    Movie detailedMovie = ApiTools.getMovie(this, _movie.getId());

    //set new stuff from more detailedMovie
    TextView genresTextView = (TextView) findViewById(R.id.movieDetail_textViewGenre);
    String genres = detailedMovie.getGenres();
    genresTextView.setText((genres != null && StringUtils.isNotEmpty(genres)) ? genres : "Keine Genres verfügbar");

    TextView description = (TextView) findViewById(R.id.movieDetail_textViewDescription);
    String overview = detailedMovie.getOverview();
    description.setText((overview != null && StringUtils.isNotEmpty(overview)) ? overview : "Keine Beschreibung verfügbar");

    TextView subtitle = (TextView) findViewById(R.id.movieDetail_textViewSubtitle);
    String tagline = detailedMovie.getTagline();
    if (tagline != null && StringUtils.isNotEmpty(tagline))
    {
      subtitle.setVisibility(View.VISIBLE);
      subtitle.setText(tagline);
    } else
    {
      subtitle.setVisibility(View.GONE);
    }

    TextView production = (TextView) findViewById(R.id.movieDetail_textViewProduction);
    String productionCompanies = detailedMovie.getProductionCompanies();
    production.setText((productionCompanies != null && StringUtils.isNotEmpty(productionCompanies)) ? productionCompanies : "Keine Produktionsfirmen verfügbar");

    TextView release = (TextView) findViewById(R.id.movieDetail_textViewReleased);
    Date releaseDate = detailedMovie.getReleaseDate();
    release.setText((releaseDate != null) ? ActivityTools.fomateDate(releaseDate) : "Kein Erscheinungsdatum verfügbar");

    TextView ratingTextView = (TextView) findViewById(R.id.movieDetail_textViewRating);
    if (detailedMovie.getVoteCount() > 0)
    {
      ratingTextView.setText("Bewertung: " + detailedMovie.getVoteAverage() + " (von " + detailedMovie.getVoteCount() + " Benutzern)");
    } else
    {
      ratingTextView.setText("Noch keine Bewertungen");
    }

    //handle click on Trailer Button
    Button trailerButton = (Button) findViewById(R.id.movieDetail_buttonTrailer);
    final String trailer = detailedMovie.getYoutubeId();
    trailerButton.setText((trailer != null && StringUtils.isNotEmpty(trailer)) ? "YouTube Trailer ansehen" : "Kein Trailer verfügbar");

    trailerButton.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View view)
      {
        //Intent takeUserRegistration = new Intent(LoginActivity.this, RegisterActivity.class);
        //startActivity(takeUserRegistration);
        if(trailer != null && StringUtils.isNotEmpty(trailer))
        {
          startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + trailer)));
        }
      }
    });

  }
}
