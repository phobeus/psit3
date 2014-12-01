package com.app.movietap.api;

import android.util.Log;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGetHC4;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;

public class UrlCacheTools
{
    public static String getUrl(URIBuilder uri) {
        return getUrl(uri.toString());
    }

    public static String getUrl(String url) {
        if (!_cache.containsKey(url)) {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            String uri = url.toString();
            HttpGetHC4 httpGet = new HttpGetHC4(uri);

            try {
                // TODO: Handle error codes (503 for too many requests, 404 for not found etc.)
                CloseableHttpResponse response = httpClient.execute(httpGet);
                String result = EntityUtils.toString(response.getEntity());
                _cache.put(uri, result);
              Log.d("caching", "Cached URL result (" + result.length() + "B)");
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return _cache.get(url);
    }

    private static HashMap<String, String> _cache = new HashMap<String, String>();
}
