package com.app.movietap.model.database;

import java.util.List;

/**
 * Defines a database table in its generic form as class to create sql statements.
 * It can be created from a class marked as Persistent
 */
public class PersistableClass
{
  /**
   * The name of the table
   */
  public String Name;

  /**
   * The fields inside the table
   */
  public List<PersistableField> Fields;
}
