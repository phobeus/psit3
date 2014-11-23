package com.app.movietap.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;

import com.app.movietap.R;

public class ActivityTools
{
  public static boolean HandleOptionsItemSelected(MenuItem item, Activity activity)
  {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    if (id == R.id.action_collection)
    {
      Intent intent = createActivity("BrowseMoviesActivity", activity.getApplicationContext());
      activity.startActivity(intent);
    } else if (id == R.id.action_wish)
    {
      Intent intent = createActivity("RegisterActivity", activity.getApplicationContext());
      activity.startActivity(intent);
    } else if (id == R.id.action_filter)
    {
      Intent intent = createActivity("HomeActivity", activity.getApplicationContext());
      activity.startActivity(intent);
    } else if (id == R.id.action_search)
    {
        /*get search query*/
      Intent intent = createActivity("NavigationActivity", activity.getApplicationContext());
      activity.startActivity(intent);
    } else if (id == R.id.action_search_view)
    {
      Intent intent = createActivity("SearchActivity", activity.getApplicationContext());
      activity.startActivity(intent);
    }

    return true;
  }

  /**
   * create a new intent and returns it. checks if activity is already
   * running.
   *
   * @param classNameToActivate
   * class name of activity
   * @param applicationContext
   * @return new Intent
   */
  protected static Intent createActivity(String classNameToActivate, Context applicationContext) {
    try {
      Class<?> activityClass = Class.forName("com.app.movietap."
        + classNameToActivate);
      Intent intent = new Intent(applicationContext, activityClass);
      intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
      // startActivity(intent);
      return intent;
    } catch (ClassNotFoundException ex) {
      Log.e("movietap", "Menu error", ex);
    }

    // return main activity in case of not found activity
    return createActivity("com.app.movietap.NavigationActivity", applicationContext);
  }

}
