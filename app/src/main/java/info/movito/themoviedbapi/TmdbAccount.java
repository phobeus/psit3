package info.movito.themoviedbapi;

import info.movito.themoviedbapi.model.MovieList;
import info.movito.themoviedbapi.model.config.Account;
import info.movito.themoviedbapi.model.core.MovieResults;
import info.movito.themoviedbapi.model.core.ResultsPage;
import info.movito.themoviedbapi.model.core.SessionToken;
import info.movito.themoviedbapi.model.core.StatusCode;
import info.movito.themoviedbapi.tools.ApiUrl;
import info.movito.themoviedbapi.tools.MovieDbException;
import info.movito.themoviedbapi.tools.MovieDbExceptionType;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class TmdbAccount extends AbstractTmdbApi {

    public static final String PARAM_SESSION = "session_id";
    public static final String TMDB_METHOD_ACCOUNT = "account";


    TmdbAccount(TmdbApi tmdbApi) {
        super(tmdbApi);
    }


    /**
     * Get the basic information for an account. You will need to have a valid session id.
     */
    public Account getAccount(SessionToken sessionToken) {
        ApiUrl apiUrl = new ApiUrl(TMDB_METHOD_ACCOUNT);

        apiUrl.addParam(PARAM_SESSION, sessionToken);

        return mapJsonResult(apiUrl, Account.class);
    }


    /**
     * Get the lists that as user has created.
     */
    public List<MovieList> getLists(SessionToken sessionToken, int accountId, String language, Integer page) {
        ApiUrl apiUrl = new ApiUrl(TmdbAccount.TMDB_METHOD_ACCOUNT, accountId, "lists");

        apiUrl.addParam(TmdbAccount.PARAM_SESSION, sessionToken);

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addParam(PARAM_LANGUAGE, language);
        }

        if (page != null && page > 0) {
            apiUrl.addParam(PARAM_PAGE, page);
        }

        return mapJsonResult(apiUrl, MovieListResults.class).getResults();
    }


    static class MovieListResults extends ResultsPage<MovieList> {

    }


    public MovieResults getRatedMovies(SessionToken sessionToken, int accountId) {
        ApiUrl apiUrl = new ApiUrl(TMDB_METHOD_ACCOUNT, accountId, "rated_movies");
        apiUrl.addParam(PARAM_SESSION, sessionToken);

        return mapJsonResult(apiUrl, MovieResults.class);
    }


    /**
     * This method lets users rate a movie.
     * <p/>
     * A valid session id is required.
     *
     * @param sessionToken
     * @param movieId
     * @param rating
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     */
    public boolean postMovieRating(SessionToken sessionToken, Integer movieId, Integer rating) {
        ApiUrl apiUrl = new ApiUrl(TmdbMovies.TMDB_METHOD_MOVIE, movieId, "rating");

        apiUrl.addParam(PARAM_SESSION, sessionToken);

        if (rating < 0 || rating > 10) {
            throw new MovieDbException(MovieDbExceptionType.UNKNOWN_CAUSE, "rating out of range");
        }

        String jsonBody = Utils.convertToJson(jsonMapper, Collections.singletonMap("value", rating));

        return mapJsonResult(apiUrl, StatusCode.class, jsonBody).getStatusCode() == 12;
    }


    public MovieResults getFavoriteMovies(SessionToken sessionToken, int accountId) {
        ApiUrl apiUrl = new ApiUrl(TMDB_METHOD_ACCOUNT, accountId, "favorite/movies");
        apiUrl.addParam(PARAM_SESSION, sessionToken);

        return mapJsonResult(apiUrl, MovieResults.class);
    }


    public TvResults getFavoriteSeries(SessionToken sessionToken, int accountId) {
        ApiUrl apiUrl = new ApiUrl(TMDB_METHOD_ACCOUNT, accountId, "favorite/tv");
        apiUrl.addParam(PARAM_SESSION, sessionToken);

        return mapJsonResult(apiUrl, TvResults.class);
    }


    /**
     * Remove a movie from an account's favorites list.
     */
    public StatusCode addFavorite(SessionToken sessionToken, int accountId, Integer movieId, MediaType mediaType) {
        return changeFavoriteStatus(sessionToken, accountId, movieId, mediaType, true);
    }


    /**
     * Remove a movie from an account's favorites list.
     */
    public StatusCode removeFavorite(SessionToken sessionToken, int accountId, Integer movieId, MediaType mediaType) {
        return changeFavoriteStatus(sessionToken, accountId, movieId, mediaType, false);
    }


    private StatusCode changeFavoriteStatus(SessionToken sessionToken, int accountId, Integer movieId, MediaType mediaType, boolean isFavorite) {
        ApiUrl apiUrl = new ApiUrl(TMDB_METHOD_ACCOUNT, accountId, "favorite");

        apiUrl.addParam(PARAM_SESSION, sessionToken);

        HashMap<String, Object> body = new HashMap<String, Object>();

        body.put("media_type", mediaType.toString());
        body.put("media_id", movieId);
        body.put("favorite", isFavorite);

        String jsonBody = Utils.convertToJson(jsonMapper, body);

        return mapJsonResult(apiUrl, StatusCode.class, jsonBody);
    }


    /**
     * Get the list of movies on an accounts watchlist.
     *
     * @return The watchlist of the user
     */
    public MovieResults getWatchListMovies(SessionToken sessionToken, int accountId) {
        ApiUrl apiUrl = new ApiUrl(TMDB_METHOD_ACCOUNT, accountId, "watchlist/movies");
        apiUrl.addParam(PARAM_SESSION, sessionToken);

        return mapJsonResult(apiUrl, MovieResults.class);
    }


    public TvResults getWatchListSeries(SessionToken sessionToken, int accountId) {
        ApiUrl apiUrl = new ApiUrl(TMDB_METHOD_ACCOUNT, accountId, "watchlist/tv");
        apiUrl.addParam(PARAM_SESSION, sessionToken);

        return mapJsonResult(apiUrl, TvResults.class);
    }


    /**
     * Add a movie to an account's watch list.
     */
    public StatusCode addToWatchList(SessionToken sessionToken, int accountId, Integer movieId, MediaType mediaType) {
        return modifyWatchList(sessionToken, accountId, movieId, mediaType, true);
    }


    /**
     * Remove a movie from an account's watch list.
     */
    public StatusCode removeFromWatchList(SessionToken sessionToken, int accountId, Integer movieId, MediaType mediaType) {
        return modifyWatchList(sessionToken, accountId, movieId, mediaType, false);
    }


    private StatusCode modifyWatchList(SessionToken sessionToken, int accountId, Integer movieId, MediaType mediaType, boolean isWatched) {
        ApiUrl apiUrl = new ApiUrl(TMDB_METHOD_ACCOUNT, accountId, "watchlist");

        apiUrl.addParam(PARAM_SESSION, sessionToken);

        HashMap<String, Object> body = new HashMap<String, Object>();

        body.put("media_type", mediaType.toString());
        body.put("media_id", movieId);
        body.put("watchlist", isWatched);

        String jsonBody = Utils.convertToJson(jsonMapper, body);

        return mapJsonResult(apiUrl, StatusCode.class, jsonBody);
    }


    /**
     * needed to tell tmdb api about what type of id is provided. E.g. see http://docs.themoviedb.apiary.io/reference/account/accountidwatchlist
     */
    // note http://stackoverflow.com/questions/8143995/should-java-member-enum-types-be-capitalized
    public static enum MediaType {
        MOVIE, TV;


        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }


    }
}
