package com.app.movietap.tools;

import com.app.movietap.model.Movie;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JsonTools
{
    public static final List<Movie> getMovies(String json)
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

    public static final JSONObject getMovie(String json)
    {

        if(!StringUtils.isBlank(json))
        {
            try
            {
                JSONObject reader = new JSONObject(json);
                JSONArray results = reader.getJSONArray("results");

                return results.getJSONObject(0);

            } catch (JSONException e)
            {
                // TODO: Logging mechanism
                return null;
            }
        }

        return null;
    }

  private static SimpleDateFormat _dateFormat = new SimpleDateFormat("yyyy-MM-dd");
}
