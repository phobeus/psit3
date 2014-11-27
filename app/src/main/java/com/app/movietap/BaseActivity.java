package com.app.movietap;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;

import com.app.movietap.tools.ActivityTools;

/**
 * This abstract class provides the basic action bar menu which has to be
 * present on every activity
 */
public abstract class BaseActivity extends Activity
{
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    StrictMode.setThreadPolicy(policy);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {

    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_navigation, menu);

    return true;
  }

  /**
   * react to actionbar events by forwarding user to desired activity.
   */
  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    super.onOptionsItemSelected(item);

    return ActivityTools.HandleOptionsItemSelected(item, this);
  }
}