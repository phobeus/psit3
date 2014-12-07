package com.app.movietap;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceFragment;


public class SettingsActivity extends Activity
{
  public static final String KEY_PREF_START_SCREEN = "pref_start_screen";
  public static final String KEY_STAY_LOGGED_IN = "pref_stay_logged_in";
  public static final String KEY_USER_USE_LOGIN = "pref_user_use_login";
  public static final String KEY_USER_USERNAME = "pref_username";
  public static final String KEY_USER_PASSWORD = "pref_password";

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    getFragmentManager().beginTransaction()
      .replace(android.R.id.content, new SettingsFragment())
      .commit();
  }

  public static class SettingsFragment extends PreferenceFragment
  {
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
      super.onCreate(savedInstanceState);

      addPreferencesFromResource(R.xml.preferences);
    }
  }

}
