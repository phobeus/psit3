package com.app.movietap.tools;

import android.text.format.DateUtils;

import com.app.movietap.model.Movie;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JsonTools
{
  public static final String getJSON(String address){
    StringBuilder builder = new StringBuilder();
    HttpClient client = new DefaultHttpClient();
    HttpGet httpGet = new HttpGet(address);
    try{
      HttpResponse response = client.execute(httpGet);
      StatusLine statusLine = response.getStatusLine();
      int statusCode = statusLine.getStatusCode();
      if(statusCode == 200){
        HttpEntity entity = response.getEntity();
        InputStream content = entity.getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(content));
        String line;
        while((line = reader.readLine()) != null){
          builder.append(line);
        }
      } else {
        //TODO: Error handling or return empty item
        return null;
        //Log.e(MainActivity.class.toString(), "Failed to get JSON object");
      }
    }catch(ClientProtocolException e){
      e.printStackTrace();
    } catch (IOException e){
      e.printStackTrace();
    }
    return builder.toString();
  }

  public static final List<Movie> GetMovies(String json)
  {
    ArrayList<Movie> result = new ArrayList<Movie>();

    if(!StringUtils.isBlank(json))
    {
      try
      {
        JSONObject reader = new JSONObject(json);
        JSONArray results = reader.getJSONArray("results");

        for(int i = 0; i< results.length();i++)
        {
          try
          {
            JSONObject movieObject = results.getJSONObject(i);
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

            result.add(new Movie(id, adult, backdrop, title, originalTitle, releaseDate, poster, popularity, voteAverage, voteCount));
          }
          catch (JSONException e)
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

  private static SimpleDateFormat _dateFormat = new SimpleDateFormat("yyyy-MM-dd");
}
