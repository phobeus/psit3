package com.app.movietap.tools;

import com.app.movietap.model.User;

import java.util.List;

/**
 * Provides methods to persist objects and load them again.
 * Remarks: The persistence model only works on classes marked with the @Persistent annotation.
 */
public interface IPersistenceHandler
{
  /**
   * Saves an object marked with the Persistent annotation
   *
   * @param object the object to save
   * @return the Id of the saved object
   */
  int save(Object object);

  /**
   * Loads a list of generic objects marked with the Persistent annotation
   *
   * @param cls     the class of the generic object T to load
   * @param where   the where clause (use ? as placeholder)
   * @param values  the values for the where clause
   * @param groupBy group by clause
   * @param orderBy order by clause
   * @param limit   limit clause
   * @param <T>     the generic type (must be a class marked as persistent)
   * @return a list with T objects in it matching the where clause
   */
  <T> List<T> loadListWhere(Class<T> cls, String where, String[] values, String groupBy, String orderBy, String limit);

  /**
   * Loads a generic object marked with the Persistent annotation with the given where clause
   * This is an overload of loadWhere
   *
   * @param cls    the class of the generic object T to load
   * @param where  the where clause (use ? as placeholder)
   * @param values the values for the where clause
   * @param <T>    the generic type (must be a class marked as persistent)
   * @return an object of type T matching the where clause
   */
  <T> T loadWhere(Class<T> cls, String where, String[] values);

  /**
   * Loads a generic object marked with the Persistent annotation with the given where clause
   *
   * @param cls     the class of the generic object T to load
   * @param where   the where clause (use ? as placeholder)
   * @param values  the values for the where clause
   * @param groupBy group by clause
   * @param orderBy order by clause
   * @param limit   limit clause
   * @param <T>     the generic type (must be a class marked as persistent)
   * @return an object of type T matching the where clause
   */
  <T> T loadWhere(Class<T> cls, String where, String[] values, String groupBy, String orderBy, String limit);

  /**
   * Deletes a generic object marked with the Persistent annotation with the given where clause
   *
   * @param cls    the class of the generic object T to load
   * @param where  the where clause (use ? as placeholder)
   * @param values the values for the where clause
   */
  void deleteWhere(Class<?> cls, String where, String[] values);

  /**
   * Returns a user object which will get created if no object existed for this phone
   *
   * @param uid the unique phone identifier
   * @return the user object from the database (might be newly created)
   */
  User getOrCreateLocalUser(String uid);
}
