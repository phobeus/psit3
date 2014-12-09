package com.app.movietap.model;

import com.app.movietap.model.cacheable.Movie;
import com.app.movietap.model.database.Persistent;

/**
 * A representation of a movie that is stored locally
 */
@Persistent
public class StoredMovie
{
  /**
   * Obligatory constructor with no arguments so it can be created by reflection
   */
  public StoredMovie()
  {
    // Needed for the creation by reflection
  }

  public StoredMovie(Movie movie)
  {
    MovieId = movie.getId();
  }

  @Persistent(Primary = true)
  public int Id;

  /**
   * The TMDb movie id
   */
  @Persistent
  public int MovieId;

  /**
   * The owner of the movie
   */
  @Persistent
  public int UserId;

  /**
   * The status of the movie (see MovieStatus class)
   */
  @Persistent
  public int Status;

  /**
   * Details about the movie that the user can enter
   */
  @Persistent
  public String Details;

  /**
   * The privacy setting of the movie
   */
  @Persistent
  public int Shared;
}
