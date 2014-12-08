package com.app.movietap.model;

import com.app.movietap.model.database.Persistent;

@Persistent
public class UrlCache
{
  @Persistent(Primary = true)
  public Integer Id;

  @Persistent
  public String Request;

  @Persistent
  public String Response;

  @Persistent
  public long Expires;
}
