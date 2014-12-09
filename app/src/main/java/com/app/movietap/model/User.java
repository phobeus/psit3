package com.app.movietap.model;

import com.app.movietap.model.database.Persistent;

/**
 * The user class which is used in multi user scenarios.
 */
@Persistent
public class User
{
  @Persistent(Primary = true)
  public int Id;

  /**
   * The UID of the user, according to the device he is using
   */
  @Persistent
  public String UID;

  @Persistent
  public String Username;

  /**
   * Defines the default sharing policy
   */
  @Persistent
  public int Privacy;
}
