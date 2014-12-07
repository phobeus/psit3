package com.app.movietap.tools;

import android.database.Cursor;

import com.app.movietap.model.User;

import java.util.List;

public interface IPersistenceHandler
{
  int save(Object object);

  Object load(Cursor cursor, Class<?> cls);

  List<Object> load(Class<?> cls, String where, String[] values, String groupBy, String orderBy, String limit);

  Object loadWhere(Class<?> cls, String where, String[] values, String groupBy, String orderBy, String limit);

  void deleteWhere(Class<?> cls, String where, String[] values);

  User getOrCreateLocalUser(String uid);
}
