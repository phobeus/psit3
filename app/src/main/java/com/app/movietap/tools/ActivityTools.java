package com.app.movietap.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;

import com.app.movietap.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Provides shared methods used by activities.
 */
public class ActivityTools
{
  /**
   * Handler method for the menu actions.
   *
   * @param item     the called item
   * @param activity the activity that called the method
   * @return true if the request was handled
   */
  public static boolean HandleOptionsItemSelected(MenuItem item, Activity activity)
  {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    if (id == R.id.action_collection)
    {
      Intent intent = createActivity("RememberMoviesActivity", activity.getApplicationContext());
      activity.startActivity(intent);
    } else if (id == R.id.action_wish)
    {
      Intent intent = createActivity("WishMoviesActivity", activity.getApplicationContext());
      activity.startActivity(intent);
    } else if (id == R.id.action_search_view)
    {
      Intent intent = createActivity("SearchActivity", activity.getApplicationContext());
      activity.startActivity(intent);
    } else if (id == R.id.action_settings)
    {
      Intent intent = createActivity("SettingsActivity", activity.getApplicationContext());
      activity.startActivity(intent);
    }

    return true;
  }

  /**
   * Create a new intent and returns it. Checks if activity is already running.
   *
   * @param classNameToActivate the name of the class to try to activate
   * @param applicationContext  the context of the callee
   * @return a new intent according to the given data
   */
  public static Intent createActivity(String classNameToActivate, Context applicationContext)
  {
    try
    {
      Class<?> activityClass = Class.forName("com.app.movietap."
        + classNameToActivate);
      Intent intent = new Intent(applicationContext, activityClass);
      intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
      // startActivity(intent);
      return intent;
    } catch (ClassNotFoundException ex)
    {
      Log.e("movietap", "Menu error", ex);
    }

    // return main activity in case of not found activity
    return createActivity("LoginActivity", applicationContext);
  }

  /**
   * Gets a date as a formatted string.
   *
   * @param date the date
   * @return the formatted date
   */
  public static String fomateDate(Date date)
  {
    SimpleDateFormat dt = new SimpleDateFormat("dd.M.yyyy");
    return dt.format(date);
  }
}
