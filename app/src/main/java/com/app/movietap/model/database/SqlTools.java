package com.app.movietap.model.database;

public class SqlTools
{
  public static String getSqlType(Class<?> type)
  {
    if (Integer.class.isAssignableFrom(type) || int.class.isAssignableFrom(type))
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

    return String.class;
  }
}
