package com.app.movietap.model;

import java.util.Date;

public class Movie
{
  public Movie(int id, boolean adult, String backdrop, String title, String originalTitle, Date releaseDate, String poster, double popularity, double voteAverage, int voteCount)
  {
    _id = id;
    _adult = adult;
    _backdrop = backdrop;
    _title = title;
    _originalTitle = originalTitle;
    _releaseDate = releaseDate;
    _poster = poster;
    _popularity = popularity;
    _voteAverage = voteAverage;
    _voteCount = voteCount;
  }

  public int GetId()
  {
    return _id;
  }

  public boolean GetIsAdult()
  {
    return _adult;
  }

  public String GetBackdrop()
  {
    return _backdrop;
  }

  public String GetTitle()
  {
    return _title;
  }

  public double GetVoteAverage()
  {
    return _voteAverage;
  }

  public int GetVoteCount()
  {
    return _voteCount;
  }

  public String GetOriginalTitle()
  {
    return _originalTitle;
  }

  public Date GetReleaseDate()
  {
    return _releaseDate;
  }

  public String GetPoster()
  {
    return _poster;
  }

  public double GetPopularity()
  {
    return _popularity;
  }

  private int _id;
  private boolean _adult;
  private String _backdrop;
  private String _title;
  private String _originalTitle;
  private Date _releaseDate;
  private String _poster;
  private double _popularity;
  private double _voteAverage;
  private int _voteCount;
}