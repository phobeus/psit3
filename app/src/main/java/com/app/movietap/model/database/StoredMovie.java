package com.app.movietap.model.database;

import com.app.movietap.model.Movie;

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

  }

  public StoredMovie(Movie movie)
  {
    MovieId = movie.getId();
  }
}
