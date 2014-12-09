package com.app.movietap.model;

import com.app.movietap.model.database.Persistent;

/**
 * The url cache table that stores url requests and responses.
 */
@Persistent
public class UrlCache
{
  @Persistent(Primary = true)
  public Integer Id;

  /**
   * The requested url
   */
  @Persistent
  public String Request;

  /**
   * The response from the url
   */
  @Persistent
  public String Response;

  /**
   * The date as a long when the cache entry expires
   */
  @Persistent
  public long Expires;
}
