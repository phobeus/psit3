package com.app.movietap.tools;

import android.util.Log;

import com.app.movietap.api.UrlCache;
import com.app.movietap.model.Movie;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGetHC4;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ApiTools
{
  public static List<Movie> searchMovies(String query)
  {
    URIBuilder url = getBaseUriBuilder();
    if(url == null) { return null; }

    url.setPath("/3/search/movie");
    url.addParameter("query", query);

    String json = UrlCache.getUrl(url);

    List<Movie> result = JsonTools.getMovies(json);

    return result != null ? result : new ArrayList<Movie>();
  }

  public static Movie getMovie(int id)
  {
      URIBuilder url = getBaseUriBuilder();
      if(url == null) { return null; }

      url.setPath("/3/movie/" + id);

      String json = UrlCache.getUrl(url);

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
