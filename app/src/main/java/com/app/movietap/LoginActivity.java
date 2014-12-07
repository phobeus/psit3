package com.app.movietap;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.movietap.model.User;
import com.app.movietap.tools.ActivityTools;
import com.app.movietap.tools.IPersistenceHandler;
import com.app.movietap.tools.PersistenceHandler;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import org.apache.commons.lang3.StringUtils;

// Should not extend BaseActifity, as the
// Navigation would be visible then
public class LoginActivity extends Activity
{
  protected EditText nickNameText;
  protected EditText passwordText;
  protected Button loginButton;
  protected Button buttonNoLogin;
  protected Button gotoRegisterButton;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
    String username = sharedPref.getString(SettingsActivity.KEY_USER_USERNAME, "");
    String password = sharedPref.getString(SettingsActivity.KEY_USER_PASSWORD, "");

    if (sharedPref.getBoolean(SettingsActivity.KEY_STAY_LOGGED_IN, false))
    {
      if (!sharedPref.getBoolean(SettingsActivity.KEY_USER_USE_LOGIN, false))
      {
        goToHomeScreen();
      } else if(!StringUtils.isBlank(username) && !StringUtils.isBlank(password))
      {
        doLogin(username, password);
      }
    }

    setContentView(R.layout.activity_login);

    ((EditText) findViewById(R.id.editTextNickLogin)).setText(username);
    ((EditText) findViewById(R.id.editTextPasswordLogin)).setText(password);

    //initialize data form xml
    nickNameText = (EditText) findViewById(R.id.editTextNickLogin);
    passwordText = (EditText) findViewById(R.id.editTextPasswordLogin);
    loginButton = (Button) findViewById(R.id.buttonLoginLogin);
    buttonNoLogin = (Button) findViewById(R.id.buttonNoLogin);
    gotoRegisterButton = (Button) findViewById(R.id.buttonGotoRegisterLogin);

    //listen to registration button click from xml activity view
    loginButton.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View view)
      {
        //Get the entered Form Data from Login
        String username = nickNameText.getText().toString().trim().toLowerCase();
        String password = passwordText.getText().toString();

        doLogin(username, password);
      }
    });

    buttonNoLogin.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View view)
      {
        getOrCreateLocalUser();

        goToHomeScreen();
      }
    });

    gotoRegisterButton.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View view)
      {
        //redirect to the registration activity
        Intent takeUserRegistration = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(takeUserRegistration);
      }
    });
  }

  public void doLogin(String username, String password)
  {
    //Login the user via parse.com
    ParseUser.logInInBackground(username, password, new LogInCallback()
    {
      @Override
      public void done(ParseUser parseUser, ParseException e)
      {
        if (e == null)
        {
          Toast.makeText(LoginActivity.this, "Willkommen zurück!", Toast.LENGTH_LONG).show();
          goToHomeScreen();
        } else
        {
          Toast.makeText(LoginActivity.this, "Anmeldung fehlgeschlagen", Toast.LENGTH_SHORT).show();
        }
      }
    });
  }

  public void goToHomeScreen()
  {
    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
    String setting = sharedPref.getString(SettingsActivity.KEY_PREF_START_SCREEN, "SearchActivity");
    Intent intent = ActivityTools.createActivity(setting, this);

    startActivity(intent);
  }

  public void getOrCreateLocalUser()
  {
    String uid = Settings.Secure.getString(this.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    IPersistenceHandler handler = new PersistenceHandler(this);
    User user = handler.getOrCreateLocalUser(uid);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings)
    {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
