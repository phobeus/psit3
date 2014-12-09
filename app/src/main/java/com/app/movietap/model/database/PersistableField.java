package com.app.movietap.model.database;

import java.lang.reflect.Field;

/**
 * Defines a database field in its generic form to create sql statements.
 * It can be created from a field marked as Persistent
 */
public class PersistableField
{
  /**
   * The name of the field
   */
  public String Name;

  /**
   * Defines if the field is the primary key
   */
  public boolean IsPrimary;

  /**
   * The type of the field as a java class
   */
  public Class<?> Type;

  /**
   * The original field gathered from reflection
   */
  public Field OriginalField;
}
