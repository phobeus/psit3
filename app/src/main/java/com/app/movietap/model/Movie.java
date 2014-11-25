package com.app.movietap.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Movie implements Parcelable
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

  public Movie(Parcel in)
  {
    _id = in.readInt();
    _adult = in.readByte() == 1 ? true : false;
    _backdrop = in.readString();
    _title = in.readString();
    _originalTitle = in.readString();
    _releaseDate = new Date(in.readLong());
    _poster = in.readString();
    _popularity = in.readDouble();
    _voteAverage = in.readDouble();
    _voteCount = in.readInt();
  }

  @Override
  public int describeContents()
  {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i)
  {
    parcel.writeInt(getId());
    parcel.writeByte((byte)(getIsAdult() ? 1 : 0));
    parcel.writeString(getBackdrop());
    parcel.writeString(getTitle());
    parcel.writeString(getOriginalTitle());
    parcel.writeLong(getReleaseDate().getTime());
    parcel.writeString(getPoster());
    parcel.writeDouble(getPopularity());
    parcel.writeDouble(getVoteAverage());
    parcel.writeInt(getVoteCount());
  }

  // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
  public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
    public Movie createFromParcel(Parcel in) {
      return new Movie(in);
    }

    public Movie[] newArray(int size) {
      return new Movie[size];
    }
  };

  public int getId()
  {
    return _id;
  }

  public boolean getIsAdult()
  {
    return _adult;
  }

  public String getBackdrop()
  {
    return _backdrop;
  }

  public String getTitle()
  {
    return _title;
  }

  public double getVoteAverage()
  {
    return _voteAverage;
  }

  public int getVoteCount()
  {
    return _voteCount;
  }

  public String getOriginalTitle()
  {
    return _originalTitle;
  }

  public Date getReleaseDate()
  {
    return _releaseDate;
  }

  public String getPoster()
  {
    return _poster;
  }

  public double getPopularity()
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