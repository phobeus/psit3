package com.app.movietap.tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.app.movietap.model.Rating;
import com.app.movietap.model.StoredMovie;
import com.app.movietap.model.UrlCache;
import com.app.movietap.model.User;
import com.app.movietap.model.database.*;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PersistenceHandler extends SQLiteOpenHelper implements IPersistenceHandler
{
  public PersistenceHandler(Context context)
  {
    super(context, "MovieTap", null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db)
  {
    List<PersistableClass> classes = getPersistableClasses();

    for (PersistableClass cls : classes)
    {
      createTableForClass(db, cls);
    }
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
  {
    if (oldVersion >= 6 && newVersion <= 8)
    {
      List<PersistableClass> classes = getPersistableClasses();

      for (PersistableClass cls : classes)
      {
        if (cls.Name.equals("UrlCache"))
        {
          db.execSQL("DROP TABLE IF EXISTS " + cls.Name);
          createTableForClass(db, cls);

          return;
        }
      }
    }

    List<PersistableClass> classes = this.getPersistableClasses();
    for (PersistableClass cls : classes)
    {
      db.execSQL("DROP TABLE IF EXISTS " + cls.Name);
    }

    onCreate(db);
  }

  @Override
  public int save(Object object)
  {
    List<PersistableField> fields = getPersistableFields(object.getClass());
    ContentValues values = new ContentValues();
    for (PersistableField f : fields)
    {
      if (!f.IsPrimary)
      {
        try
        {
          Object value = object.getClass().getField(f.Name).get(object);
          if (value instanceof String)
          {
            values.put(f.Name, (String) value);
          } else if (value instanceof Integer)
          {
            values.put(f.Name, (Integer) value);
          } else if (value instanceof Boolean)
          {
            values.put(f.Name, (Boolean) value);
          } else if (value instanceof Float)
          {
            values.put(f.Name, (Float) value);
          } else if (value instanceof Double)
          {
            values.put(f.Name, (Double) value);
          }
        } catch (IllegalAccessException e)
        {
          e.printStackTrace();
        } catch (NoSuchFieldException e)
        {
          e.printStackTrace();
        }
      }
    }

    SQLiteDatabase db = this.getWritableDatabase();
    int result = (int) db.insert(object.getClass().getSimpleName(), null, values);
    db.close();

    return result;
  }

  @Override
  public <T> T load(Class<T> cls, Cursor cursor)
  {
    Constructor[] ctors = cls.getDeclaredConstructors();
    Constructor ctor = null;
    for (int i = 0; i < ctors.length; i++)
    {
      ctor = ctors[i];
      if (ctor.getGenericParameterTypes().length == 0)
        break;
    }

    Object result = null;
    try
    {
      ctor.setAccessible(true);
      result = ctor.newInstance();

      for (PersistableField field : getPersistableFields(cls))
      {
        Object value;
        Class<?> sqlType = SqlTools.getType(field.Type);
        int columnIndex = cursor.getColumnIndex(field.Name);
        if (sqlType.equals(Integer.class))
        {
          value = cursor.getInt(columnIndex);
        } else if (sqlType.equals(Double.class))
        {
          value = cursor.getDouble(columnIndex);
        } else if (sqlType.equals(Float.class))
        {
          value = cursor.getFloat(columnIndex);
        } else if (sqlType.equals(Long.class))
        {
          value = cursor.getLong(columnIndex);
        } else
        {
          value = cursor.getString(columnIndex);
        }
        field.OriginalField.set(result, value);
      }
    } catch (InstantiationException e)
    {
      e.printStackTrace();
    } catch (IllegalAccessException e)
    {
      e.printStackTrace();
    } catch (InvocationTargetException e)
    {
      e.printStackTrace();
    }

    return convertInstanceOfObject(result, cls);
  }

  @Override
  public <T> List<T> loadListWhere(Class<T> cls, String where, String[] values, String groupBy, String orderBy, String limit)
  {
    SQLiteDatabase db = getWritableDatabase();
    ArrayList<String> fields = new ArrayList<String>();
    for (PersistableField f : getPersistableFields(cls))
    {
      fields.add(f.Name);
    }

    ArrayList<T> result = new ArrayList<T>();

    Cursor cursor = db.query(cls.getSimpleName(), Arrays.copyOf(fields.toArray(), fields.size(), String[].class), where, values, groupBy, null, orderBy, limit);
    if (cursor != null && cursor.getCount() >= 1)
    {
      cursor.moveToFirst();

      do
      {
        result.add(load(cls, cursor));
      } while (cursor.moveToNext());
      db.close();
    }

    return result;
  }

  @Override
  public <T> T loadWhere(Class<T> cls, String where, String[] values)
  {
    Object item = loadWhere(cls, where, values, null, null, null);

    return convertInstanceOfObject(item, cls);
  }

  private static <T> T convertInstanceOfObject(Object o, Class<T> clazz)
  {
    try
    {
      return clazz.cast(o);
    } catch (ClassCastException e)
    {
      return null;
    }
  }

  public <T> T loadWhere(Class<T> cls, String where, String[] values, String groupBy, String orderBy, String limit)
  {
    SQLiteDatabase db = getWritableDatabase();
    ArrayList<String> fields = new ArrayList<String>();
    for (PersistableField f : getPersistableFields(cls))
    {
      fields.add(f.Name);
    }

    Cursor cursor = db.query(cls.getSimpleName(), Arrays.copyOf(fields.toArray(), fields.size(), String[].class), where, values, groupBy, null, orderBy, limit);
    if (cursor != null && cursor.getCount() == 1)
    {
      cursor.moveToFirst();
      Object loadedObject = load(cls, cursor);
      db.close();

      return convertInstanceOfObject(loadedObject, cls);
    }

    return null;
  }

  @Override
  public void deleteWhere(Class<?> cls, String where, String[] values)
  {
    SQLiteDatabase db = getWritableDatabase();
    db.delete(cls.getSimpleName(), where, values);
    db.close();
  }

  @Override
  public User getOrCreateLocalUser(String uid)
  {
    SQLiteDatabase db = this.getWritableDatabase();
    ArrayList<String> fields = new ArrayList<String>();
    for (PersistableField f : getPersistableFields(User.class))
    {
      fields.add(f.Name);
    }

    Cursor cursor = db.query("User", Arrays.copyOf(fields.toArray(), fields.size(), String[].class), "UID = ?", new String[]{uid}, null, null, null);
    User user;
    if (cursor != null && cursor.getCount() == 1)
    {
      cursor.moveToFirst();
      Object result = load(User.class, cursor);
      user = (User) result;
    } else
    {
      user = new User();
      user.UID = uid;
      save(user);
    }
    db.close();

    return user;
  }

  private List<PersistableClass> getPersistableClasses()
  {
    ArrayList<Class<?>> annotated = new ArrayList<Class<?>>();

    annotated.add(User.class);
    annotated.add(StoredMovie.class);
    annotated.add(Rating.class);
    annotated.add(UrlCache.class);

    List<PersistableClass> classes = new ArrayList<PersistableClass>();
    for (Class<?> cls : annotated)
    {
      String className = cls.getAnnotation(Persistent.class).Name();
      if (StringUtils.isEmpty(className))
      {
        className = cls.getSimpleName();
      }

      List<PersistableField> fields = getPersistableFields(cls);

      if (!fields.isEmpty())
      {
        PersistableClass persistableClass = new PersistableClass();
        persistableClass.Name = className;
        persistableClass.Fields = fields;

        classes.add(persistableClass);
      }
    }

    return classes;
  }

  private List<PersistableField> getPersistableFields(Class<?> cls)
  {
    List<PersistableField> fields = new ArrayList<PersistableField>();
    for (Field field : cls.getDeclaredFields())
    {
      if (field.isAnnotationPresent(Persistent.class))
      {
        String propertyName = field.getAnnotation(Persistent.class).Name();
        if (StringUtils.isEmpty(propertyName))
        {
          propertyName = field.getName();
          PersistableField persistableField = new PersistableField();
          persistableField.Name = propertyName;
          persistableField.Type = field.getType();
          persistableField.IsPrimary = field.getAnnotation(Persistent.class).Primary();
          persistableField.OriginalField = field;

          fields.add(persistableField);
        }
      }
    }
    return fields;
  }

  private void createTableForClass(SQLiteDatabase db, PersistableClass cls)
  {
    StringBuilder sqlBuilder = new StringBuilder();

    sqlBuilder.append("CREATE TABLE ");
    sqlBuilder.append(cls.Name);
    sqlBuilder.append(" ( ");

    for (int i = 0; i < cls.Fields.size(); i++)
    {
      PersistableField field = cls.Fields.get(i);
      sqlBuilder.append(field.Name);
      sqlBuilder.append(" ");
      sqlBuilder.append(SqlTools.getSqlType(field.Type));
      if (field.IsPrimary)
      {
        sqlBuilder.append(" PRIMARY KEY AUTOINCREMENT");
      }
      if (i + 1 < cls.Fields.size())
      {
        sqlBuilder.append(", ");
      }
    }

    sqlBuilder.append(" );");

    String sqlCommand = sqlBuilder.toString();

    db.execSQL(sqlCommand);
  }

  private static final int DATABASE_VERSION = 8;
}
