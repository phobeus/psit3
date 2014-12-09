package com.app.movietap.model.cacheable;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Date;

/**
 * A representation of a TMDb movie that implements Parcelable.
 * This object can be passed from activity to activity.
 */
public class Movie implements Parcelable
{
  /**
   * Creates a new instance of the movie class.
   *
   * @param id            TMDb id field
   * @param adult         marks if it is an adult film
   * @param backdrop      background poster
   * @param title         title of the movie
   * @param originalTitle original language title
   * @param releaseDate   date of release
   * @param poster        cover photo
   * @param popularity    TMDb popularity
   * @param voteAverage   TMDb vote average
   * @param voteCount     TMDb vote count
   */
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

  /**
   * Creates a new instance of the movie class.
   *
   * @param in the parcel object to parse
   */
  public Movie(Parcel in)
  {
    _id = in.readInt();
    _adult = in.readByte() == 1 ? true : false;
    _backdrop = in.readString();
    _title = in.readString();
    _originalTitle = in.readString();
    long milliseconds = in.readLong();
    _releaseDate = milliseconds == 0 ? null : new Date(milliseconds);
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
    parcel.writeByte((byte) (getIsAdult() ? 1 : 0));
    parcel.writeString(getBackdrop());
    parcel.writeString(getTitle());
    parcel.writeString(getOriginalTitle());
    Date releaseDate = getReleaseDate();
    parcel.writeLong(releaseDate != null ? releaseDate.getTime() : 0);
    parcel.writeString(getPoster());
    parcel.writeDouble(getPopularity());
    parcel.writeDouble(getVoteAverage());
    parcel.writeInt(getVoteCount());
  }

  /**
   * All Parcelables must have a CREATOR that implements these two methods.
   */
  public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>()
  {
    public Movie createFromParcel(Parcel in)
    {
      return new Movie(in);
    }

    public Movie[] newArray(int size)
    {
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

  public String getYoutubeId()
  {
    return _youtubeId;
  }

  public void setYoutubeId(String youtubeIds)
  {
    _youtubeId = youtubeIds;
  }

  public void setOverview(String overview)
  {
    _overview = overview;
  }

  /**
   * Sets the subtitle of the movie
   *
   * @param tagline the subtitle
   */
  public void setTagline(String tagline)
  {
    _tagline = tagline;
  }

  public String getGenres()
  {
    return _genres;
  }

  public String getOverview()
  {
    return _overview;
  }

  /**
   * Gets the subtitle of the movie
   *
   * @return the subtitle of the movie
   */
  public String getTagline()
  {
    return _tagline;
  }

  public String getProductionCompanies()
  {
    return _productionCompanies;
  }

  /**
   * Sets the genres from a json array.
   *
   * @param genresArray the array of genres
   */
  public void setGenres(JSONArray genresArray)
  {
    for (int i = 0; i < genresArray.length(); i++)
    {
      String name = "";
      try
      {
        name = genresArray.getJSONObject(i).getString("name");
      } catch (JSONException e)
      {
        e.printStackTrace();
      }

      if (_genres == null)
      {
        _genres = name;
      } else
      {
        _genres += ", " + name;
      }
    }
  }

  /**
   * Sets the production companies from a json array.
   *
   * @param companiesArray the companies array
   */
  public void setProductionCompanies(JSONArray companiesArray)
  {
    //production_companies
    for (int i = 0; i < companiesArray.length(); i++)
    {
      String name = "";
      try
      {
        name = companiesArray.getJSONObject(i).getString("name");
      } catch (JSONException e)
      {
        e.printStackTrace();
      }

      if (_productionCompanies == null)
      {
        _productionCompanies = name;
      } else
      {
        _productionCompanies += ", " + name;
      }
    }
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
  private String _genres;
  private String _overview;
  private String _tagline;
  private String _productionCompanies;
  private String _youtubeId;
}