package com.app.movietap.api;

import android.content.Context;
import android.util.Log;

import com.app.movietap.model.UrlCache;
import com.app.movietap.tools.IPersistenceHandler;
import com.app.movietap.tools.PersistenceHandler;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGetHC4;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UrlCacheTools
{
  public static String getUrl(Context context, URIBuilder uri)
  {
    return getUrl(context, uri.toString());
  }

  public static String getUrl(Context context, String url)
  {
    if (_cache == null)
    {
      IPersistenceHandler handler = new PersistenceHandler(context);
      try
      {
        _cache = handler.loadListWhere(UrlCache.class, null, null, null, null, null);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }

    for (int i = 0; i < _cache.size(); i++)
    {
      UrlCache cache = _cache.get(i);
      if (cache.Request.equals(url))
      {
        if (cache.Expires > new java.util.Date().getTime())
        {
          return cache.Response;
        } else
        {
          IPersistenceHandler handler = new PersistenceHandler(context);
          handler.deleteWhere(UrlCache.class, "Id = ?", new String[]{cache.Id + ""});
          _cache.remove(i);
          break;
        }
      }
    }

    CloseableHttpClient httpClient = HttpClients.createDefault();
    String uri = url.toString();
    HttpGetHC4 httpGet = new HttpGetHC4(uri);
    try
    {
      // TODO: Handle error codes (503 for too many requests, 404 for not found etc.)
      CloseableHttpResponse response = httpClient.execute(httpGet);
      String result = EntityUtils.toString(response.getEntity());
      UrlCache cache = new UrlCache();
      cache.Request = url;
      cache.Response = result;
      Calendar cal = Calendar.getInstance();
      cal.setTime(new Date());
      cal.add(Calendar.DATE, 1);
      cache.Expires = cal.getTimeInMillis();

      IPersistenceHandler handler = new PersistenceHandler(context);
      handler.save(cache);
      _cache.add(cache);

      Log.d("caching", "Cached URL result (" + result.length() + "Bytes), total items in cache: " + _cache.size());

      return result;
    } catch (IOException e)
    {
      e.printStackTrace();
      return null;
    }
  }

  private static List<UrlCache> _cache;
}
