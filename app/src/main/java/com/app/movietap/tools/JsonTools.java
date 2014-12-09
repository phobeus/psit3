package com.app.movietap.tools;

import com.app.movietap.model.cacheable.Movie;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Provides functions to handle strings and json strings.
 */
public class JsonTools
{
  /**
   * Returns a list of movies from the given json string.
   * If an error occurs, returns an empty list.
   *
   * @param json string to parse
   * @return a list of movies from the given json string
   */
  public static final List<Movie> getMovies(String json)
  {
    ArrayList<Movie> result = new ArrayList<Movie>();

    if (!StringUtils.isBlank(json))
    {
      try
      {
        JSONObject reader = new JSONObject(json);
        JSONArray results = reader.getJSONArray("results");

        for (int i = 0; i < results.length(); i++)
        {
          try
          {
            JSONObject movieObject = results.getJSONObject(i);
            Movie movie = getMovie(movieObject);
            result.add(movie);
          } catch (JSONException e)
          {
            // TODO: Logging mechanism, for now just skip an element that can't be parsed
            return result;
          }
        }
      } catch (JSONException e)
      {
        // TODO: Logging mechanism
        return result;
      }
    }

    return result;
  }

  /**
   * Returns a movie from a given json string
   *
   * @param json the json string containing the movie
   * @return the movie from the given json string or null if it is not found
   */
  public static final Movie getMovie(String json)
  {

    if (!StringUtils.isBlank(json))
    {
      try
      {
        // TODO: Add description etc. to the movie here before returning it.
        return getMovie(new JSONObject(json));

      } catch (JSONException e)
      {
        // TODO: Logging mechanism
        return null;
      }
    }

    return null;
  }

  /**
   * Returns the url for a trailer of a json string containing a movie
   *
   * @param json the json string to parse
   * @return the url for the given json object or null if no url is found
   */
  public static final String getTrailer(String json)
  {
    if (!StringUtils.isBlank(json))
    {
      try
      {
        return getTrailerUrl(new JSONObject(json));

      } catch (JSONException e)
      {
        return null;
      }
    }
    return null;
  }

  /**
   * Returns a movie from a json object.
   *
   * @param movieObject the json object containing the movie
   * @return the movie object
   * @throws JSONException when a json parse error occurs
   */
  private static Movie getMovie(JSONObject movieObject) throws JSONException
  {
    boolean adult = movieObject.getBoolean("adult");
    String backdrop = movieObject.getString("backdrop_path");
    String title = movieObject.getString("title");
    int id = movieObject.getInt("id");
    String originalTitle = movieObject.getString("original_title");
    Date releaseDate = null;
    try
    {
      releaseDate = _dateFormat.parse(movieObject.getString("release_date"));
    } catch (ParseException e)
    {
      // TODO: Logging mechanism
      releaseDate = null;
    }
    String poster = movieObject.getString("poster_path");
    double popularity = movieObject.getDouble("popularity");
    double voteAverage = movieObject.getDouble("vote_average");
    int voteCount = movieObject.getInt("vote_count");

    Movie returnMovie = new Movie(id, adult, backdrop, title, originalTitle, releaseDate, poster, popularity, voteAverage, voteCount);

    // TODO: Add additional parameters here, be careful, not every json string has those!
    if (movieObject.has("tagline"))
    {
      String tagline = movieObject.getString("tagline");
      if (tagline != null && !tagline.equals("null"))
      {
        returnMovie.setTagline(tagline);
      }
    }
    if (movieObject.has("overview"))
    {
      String overview = movieObject.getString("overview");
      if (overview != null && !overview.equals("null"))
      {
        returnMovie.setOverview(overview);
      }
    }
    if (movieObject.has("genres"))
    {
      JSONArray genreArray = movieObject.getJSONArray("genres");
      returnMovie.setGenres(genreArray);
    }
    if (movieObject.has("production_companies"))
    {
      JSONArray productionArray = movieObject.getJSONArray("production_companies");
      returnMovie.setProductionCompanies(productionArray);
    }

    return returnMovie;
  }

  /**
   * Returns the url for a trailer of a json object
   *
   * @param movieObject the json object to parse
   * @return the url for the given json object
   * @throws JSONException when a parsing exception occurs within the json object
   */
  private static String getTrailerUrl(JSONObject movieObject) throws JSONException
  {
    JSONArray trailers = movieObject.getJSONArray("results");
    String videoId = null;
    String tempVideoId = null;
    int VideoSize = 0;
    int tempVideoSize = 0;

    for (int i = 0; i < trailers.length(); i++)
    {
      try
      {
        if (trailers.getJSONObject(i).getString("site").equals("YouTube"))
        {
          tempVideoId = trailers.getJSONObject(i).getString("key");
          tempVideoSize = trailers.getJSONObject(i).getInt("size");
        }
      } catch (JSONException e)
      {
        e.printStackTrace();
      }

      if (tempVideoSize >= VideoSize)
      {
        videoId = tempVideoId;
      }
    }

    return videoId;
  }

  private static final SimpleDateFormat _dateFormat = new SimpleDateFormat("yyyy-MM-dd");
}
