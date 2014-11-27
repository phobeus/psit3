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
                        Movie movie = getMovie(movieObject);
                        result.add(movie);
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
        if(movieObject.has("tagline")) { returnMovie.setTagline(movieObject.getString("tagline")); }
        if(movieObject.has("overview")) { returnMovie.setOverview(movieObject.getString("overview")); }
        if(movieObject.has("genres")) {
            JSONArray genreArray = movieObject.getJSONArray("genres");
            returnMovie.setGenres(genreArray);
        }
        if(movieObject.has("production_companies")) {
            JSONArray productionArray = movieObject.getJSONArray("production_companies");
            returnMovie.setProductionCompanies(productionArray);
        }
        


        return returnMovie;
    }

    public static final Movie getMovie(String json)
    {

        if(!StringUtils.isBlank(json))
        {
            try
            {

                // TODO: Add description etc. to the movie here before returning it.
                // This is an example string:
                // {"adult":false,"backdrop_path":"/pA6FQxybVUjNlbYUAJJupLTfUsN.jpg","belongs_to_collection":null,"budget":40000000,"genres":[{"id":35,"name":"Comedy"}],"homepage":"","id":232672,"imdb_id":"tt1086772","original_title":"Blended","overview":"After a bad blind date, a man and woman find themselves stuck together at a resort for families, where their attractions grows as their respective kids benefit from the burgeoning relationship.","popularity":34.08180121625,"poster_path":"/shXBDFnGTmG3DOUib7vBt21tMFk.jpg","production_companies":[{"name":"Warner Brothers","id":6577},{"name":"Warner Bros.","id":6194},{"name":"Happy Madison","id":878},{"name":"Gulfstream Pictures","id":20788}],"production_countries":[{"iso_3166_1":"US","name":"United States of America"}],"release_date":"2014-05-23","revenue":123494610,"runtime":117,"spoken_languages":[{"iso_639_1":"en","name":"English"}],"status":"Released","tagline":"Single Dad, No Clue. Single Mum, Flying Solo.","title":"Blended","vote_average":6.9,"vote_count":178}
                return getMovie(new JSONObject(json));

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
