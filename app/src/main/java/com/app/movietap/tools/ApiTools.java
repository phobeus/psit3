package com.app.movietap.tools;

import android.content.Context;
import android.util.Log;

import com.app.movietap.api.UrlCacheTools;
import com.app.movietap.model.cacheable.Movie;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ApiTools
{
  public static List<Movie> searchMovies(Context context, String query, String adultVideos, String year)
  {
    URIBuilder url = getBaseUriBuilder();
    if(url == null) { return null; }

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
      }
      catch (NumberFormatException exception)
      {
        // NOP, no valid number given, just ignore this parameter
      }
    }

    String json = UrlCacheTools.getUrl(context, url);

    List<Movie> result = JsonTools.getMovies(json);

    return result != null ? result : new ArrayList<Movie>();
  }

  public static List<Movie> popularMovies(Context context)
  {
    URIBuilder url = getBaseUriBuilder();
    if(url == null) { return null; }

    url.setPath("/3/discover/movie");
    url.addParameter("sort_by", "popularity.desc");
    String json = UrlCacheTools.getUrl(context, url);

    List<Movie> result = JsonTools.getMovies(json);

    return result != null ? result : new ArrayList<Movie>();
  }

  public static List<Movie> bestRatedMovies(Context context)
  {
    URIBuilder url = getBaseUriBuilder();
    if(url == null) { return null; }

    url.setPath("/3/discover/movie");
    url.addParameter("certification_country", "CH");
    url.addParameter("sort_by", "vote_count.desc");
    String json = UrlCacheTools.getUrl(context, url);

    List<Movie> result = JsonTools.getMovies(json);

    return result != null ? result : new ArrayList<Movie>();
  }

  public static Movie getMovie(Context context, int id)
  {
    URIBuilder url = getBaseUriBuilder();
    if(url == null) { return null; }

    url.setPath("/3/movie/" + id);
    String jsonMovie = UrlCacheTools.getUrl(context, url);
    Movie movie = JsonTools.getMovie(jsonMovie);
    addTrailer(context, id, movie);

    return movie;
  }

  public static void addTrailer(Context context, int id, Movie movie)
  {
    URIBuilder url = getBaseUriBuilder();
    if(url != null)
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
