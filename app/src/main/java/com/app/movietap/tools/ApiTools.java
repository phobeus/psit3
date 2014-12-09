package com.app.movietap.tools;

import android.content.Context;
import android.util.Log;

import com.app.movietap.model.cacheable.Movie;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains methods to help getting objects from TMDb API directly.
 */
public class ApiTools
{
  /**
   * Returns a list of movies from a query.
   *
   * @param context     the context of the callee
   * @param query       the search string to find movies from
   * @param adultVideos states whether adult movies should be included (true or false)
   * @param year        if set, the query will be restricted around that year
   * @return a list of movies matching the given criteria
   */
  public static List<Movie> searchMovies(Context context, String query, String adultVideos, String year)
  {
    URIBuilder url = getBaseUriBuilder();
    if (url == null)
    {
      return null;
    }

    url.setPath("/3/search/movie");
    url.addParameter("query", query);

    if (StringUtils.isNotEmpty(adultVideos))
    {
      url.addParameter("include_adult", Boolean.valueOf(adultVideos).toString());
    }
    if (StringUtils.isNotBlank(year))
    {
      try
      {
        Integer yearInt = Integer.parseInt(year);
        url.addParameter("year", yearInt + "");
      } catch (NumberFormatException exception)
      {
        // NOP, no valid number given, just ignore this parameter
      }
    }

    String json = UrlCacheTools.getUrl(context, url);

    List<Movie> result = JsonTools.getMovies(json);

    return result != null ? result : new ArrayList<Movie>();
  }

  /**
   * Returns a list of popular movies.
   *
   * @param context the context of the callee
   * @return a list of movies that are popular according to TMDb
   */
  public static List<Movie> popularMovies(Context context)
  {
    URIBuilder url = getBaseUriBuilder();
    if (url == null)
    {
      return null;
    }

    url.setPath("/3/discover/movie");
    url.addParameter("sort_by", "popularity.desc");
    String json = UrlCacheTools.getUrl(context, url);

    List<Movie> result = JsonTools.getMovies(json);

    return result != null ? result : new ArrayList<Movie>();
  }

  /**
   * Returns a list of best rated movies.
   *
   * @param context the context of the callee
   * @return a list of movies that are best rated according to TMDb
   */
  public static List<Movie> bestRatedMovies(Context context)
  {
    URIBuilder url = getBaseUriBuilder();
    if (url == null)
    {
      return null;
    }

    url.setPath("/3/discover/movie");
    url.addParameter("certification_country", "CH");
    url.addParameter("sort_by", "vote_count.desc");
    String json = UrlCacheTools.getUrl(context, url);

    List<Movie> result = JsonTools.getMovies(json);

    return result != null ? result : new ArrayList<Movie>();
  }

  /**
   * Returns a movie with the given TMDb id.
   *
   * @param context the context of the callee
   * @param id      the TMDb internal movie id
   * @return a movie matching the id or null
   */
  public static Movie getMovie(Context context, int id)
  {
    URIBuilder url = getBaseUriBuilder();
    if (url == null)
    {
      return null;
    }

    url.setPath("/3/movie/" + id);
    String jsonMovie = UrlCacheTools.getUrl(context, url);
    Movie movie = JsonTools.getMovie(jsonMovie);
    addTrailer(context, id, movie);

    return movie;
  }

  /**
   * Injects the given movie with the trailer url
   *
   * @param context the context of the callee
   * @param id      the TMDb internal movie id
   * @param movie   the movie to inject
   */
  public static void addTrailer(Context context, int id, Movie movie)
  {
    URIBuilder url = getBaseUriBuilder();
    if (url != null)
    {
      //add youtube trailer if exists
      url.setPath("/3/movie/" + id + "/videos");
      String jsonMovie = UrlCacheTools.getUrl(context, url);
      String trailer = JsonTools.getTrailer(jsonMovie);
      if (trailer != null)
      {
        movie.setYoutubeId(trailer);
      }
    }
  }

  /**
   * Returns the base uri including the language and the api key.
   *
   * @return uri with the language and the api key
   */
  private static URIBuilder getBaseUriBuilder()
  {
    URIBuilder url = null;
    try
    {
      url = new URIBuilder("http://api.themoviedb.org");
      url.addParameter("api_key", _apiKey);
      url.addParameter("language", "de");
    } catch (URISyntaxException e)
    {
      Log.e("ApiTools", e.getMessage());
    }

    return url;
  }

  private static final String _apiKey = "ec098c57b57433b5b52376f6425884ef";
}
