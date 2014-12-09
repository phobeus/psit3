package com.app.movietap.model.database;

/**
 * Provides database specific helper methods.
 */
public class SqlTools
{
  /**
   * Determines the sqlite database type and returns it.
   *
   * @param type the requested type
   * @return the database type as a string
   */
  public static String getSqlType(Class<?> type)
  {
    if (Integer.class.isAssignableFrom(type) || int.class.isAssignableFrom(type) || long.class.isAssignableFrom(type) || Long.class.isAssignableFrom(type))
    {
      return "INTEGER";
    }

    if (String.class.isAssignableFrom(type) || Boolean.class.isAssignableFrom(type))
    {
      return "TEXT";
    }

    if (Float.class.isAssignableFrom(type) || Double.class.isAssignableFrom(type))
    {
      return "REAL";
    }

    return null;
  }

  /**
   * Determines the type and returns the same type as the upper case class.
   * We ran into some issues extracting fields by reflection, this method fixes this issue.
   *
   * @param field the requested field
   * @return the given type standardized
   */
  public static Class<?> getType(Class<?> field)
  {
    if (field.isAssignableFrom(int.class) || field.isAssignableFrom(Integer.class))
    {
      return Integer.class;
    }
    if (field.isAssignableFrom(String.class))
    {
      return String.class;
    }
    if (field.isAssignableFrom(Double.class) || field.isAssignableFrom(double.class))
    {
      return Double.class;
    }
    if (field.isAssignableFrom(Float.class) || field.isAssignableFrom(float.class))
    {
      return Float.class;
    }
    if (field.isAssignableFrom(Long.class) || field.isAssignableFrom(long.class))
    {
      return Long.class;
    }

    return String.class;
  }
}
