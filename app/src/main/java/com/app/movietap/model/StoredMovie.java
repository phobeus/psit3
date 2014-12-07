package com.app.movietap.model;

import com.app.movietap.model.cacheable.Movie;
import com.app.movietap.model.database.Persistent;

@Persistent
public class StoredMovie
{
  @Persistent(Primary = true)
  public int Id;

  @Persistent
  public int MovieId;

  @Persistent
  public int UserId;

  @Persistent
  public int Status;

  @Persistent
  public String Details;

  @Persistent
  public int Shared;

  public StoredMovie()
  {
    // Needed for the creation by reflection
  }

  public StoredMovie(Movie movie)
  {
    MovieId = movie.getId();
  }
}
