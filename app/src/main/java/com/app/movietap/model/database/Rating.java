package com.app.movietap.model.database;

@Persistent
public class Rating
{
  @Persistent(Primary = true)
  public int Id;

  @Persistent
  public int MovieId;

  @Persistent
  public int UserId;

  @Persistent
  public double Rating;
}
