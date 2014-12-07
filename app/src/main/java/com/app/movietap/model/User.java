package com.app.movietap.model;

import com.app.movietap.model.database.Persistent;

@Persistent
public class User
{
  public User()
  { }

  public User(int id, String uid, String username, int privacy)
  {
    Id = id;
    UID = uid;
    Username = username;
    Privacy = privacy;
  }

  @Persistent(Primary = true)
  public int Id;

  @Persistent
  public String UID;

  @Persistent
  public String Username;

  @Persistent
  public int Privacy;
}
