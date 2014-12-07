package com.app.movietap.tools;

import android.database.Cursor;

import com.app.movietap.model.User;

import java.util.List;

public interface IPersistenceHandler
{
  int save(Object object);

  <T> T load(Class<T> cls, Cursor cursor);

  <T> List<T> loadListWhere(Class<T> cls, String where, String[] values, String groupBy, String orderBy, String limit);

  <T> T loadWhere(Class<T> cls, String where, String[] values);

  <T> T loadWhere(Class<T> cls, String where, String[] values, String groupBy, String orderBy, String limit);

  void deleteWhere(Class<?> cls, String where, String[] values);

  User getOrCreateLocalUser(String uid);
}
