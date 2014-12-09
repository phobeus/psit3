package com.app.movietap.model;

import com.app.movietap.model.database.Persistent;

/**
 * The rating class to provide user ratings.
 */
@Persistent
public class Rating
{
  @Persistent(Primary = true)
  public int Id;

  /**
   * The TMDb movie id
   */
  @Persistent
  public int MovieId;

  /**
   * The user that gave the rating
   */
  @Persistent
  public int UserId;

  /**
   * The rating the user gave the movie
   */
  @Persistent
  public double Rating;
}
