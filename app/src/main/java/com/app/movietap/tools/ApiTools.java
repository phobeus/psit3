package com.app.movietap.tools;

import android.content.Context;
import android.util.Log;

import com.app.movietap.api.UrlCacheTools;
import com.app.movietap.model.cacheable.Movie;

import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ApiTools
{
  public static List<Movie> searchMovies(Context context, String query)
  {
    URIBuilder url = getBaseUriBuilder();
    if(url == null) { return null; }

    url.setPath("/3/search/movie");
    url.addParameter("query", query);

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
    url.addParameter("sort_by", "vote_average.desc");
    String json = UrlCacheTools.getUrl(context, url);

    List<Movie> result = JsonTools.getMovies(json);

    return result != null ? result : new ArrayList<Movie>();
  }

  public static Movie getMovie(Context context, int id)
  {
      URIBuilder url = getBaseUriBuilder();
      if(url == null) { return null; }

      url.setPath("/3/movie/" + id);

      String json = UrlCacheTools.getUrl(context, url);

      return JsonTools.getMovie(json);
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
