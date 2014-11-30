package com.app.movietap.model.database;

import java.lang.reflect.Field;

public class PersistableField
{
  public String Name;
  public boolean IsPrimary;
  public Class<?> Type;

  public Field OriginalField;
}
