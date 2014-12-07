package com.app.movietap.model.cacheable;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;

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
    parcel.writeByte((byte)(getIsAdult() ? 1 : 0));
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

  public void setGenres (JSONArray genresArray)
  {
      for(int i = 0; i < genresArray.length(); i++){
          String name = "";
          try {
              name = genresArray.getJSONObject(i).getString("name");
          } catch (JSONException e) {
              e.printStackTrace();
          }

          if(_genres == null) {
              _genres = name;
          } else  {
              _genres += ", " + name;
          }
      }
  }
  public void setProductionCompanies (JSONArray companiesArray)
  {
      //production_companies
      for(int i = 0; i < companiesArray.length(); i++){
          String name = "";
          try {
              name = companiesArray.getJSONObject(i).getString("name");
          } catch (JSONException e) {
              e.printStackTrace();
          }

          if(_productionCompanies == null) {
              _productionCompanies = name;
          } else  {
              _productionCompanies += ", " + name;
          }
      }
  }

  public void setOverview (String overview)
  {
      _overview = overview;
  }

  public void setTagline (String tagline)
  {
      _tagline = tagline;
  }

  public String getGenres(){ return _genres; };
  public String getOverview(){ return _overview; };
  public String getTagline(){ return _tagline; };
  public String getProductionCompanies(){ return _productionCompanies; };


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
  private String _overview; //short description
  private String _tagline;  //subtitle
  private String _productionCompanies;
}