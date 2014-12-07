package com.app.movietap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.movietap.model.database.User;
import com.app.movietap.tools.PersistenceHandler;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

// Should not extend BaseActifity, as the
// Navigation would be visible then
public class LoginActivity extends Activity {


    protected EditText nickNameText;
    protected EditText passwordText;
    protected Button loginButton;
    protected Button buttonNoLogin;
    protected Button gotoRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialize data form xml
        nickNameText = (EditText)findViewById(R.id.editTextNickLogin);
        passwordText = (EditText)findViewById(R.id.editTextPasswordLogin);
        loginButton = (Button)findViewById(R.id.buttonLoginLogin);
        buttonNoLogin = (Button)findViewById(R.id.buttonNoLogin);
        gotoRegisterButton = (Button)findViewById(R.id.buttonGotoRegisterLogin);

        //listen to registration button click from xml activity view
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            //Get the entered Form Data from Login
            String username = nickNameText.getText().toString().trim().toLowerCase();
            String password = passwordText.getText().toString();


            //Login the user via parse.com
            ParseUser.logInInBackground(username, password, new LogInCallback() {
                @Override
                public void done(ParseUser parseUser, ParseException e) {
                    if (e == null)
                    {
                        //successful login
                        Toast.makeText(LoginActivity.this, "Willkommen zurück!", Toast.LENGTH_LONG).show();

                        //redirect to the home activity
                        Intent takeUserToSearch = new Intent(LoginActivity.this, SearchActivity.class);
                        startActivity(takeUserToSearch);
                    } else {
                        //login was not secussfull
                        Toast.makeText(LoginActivity.this, "Anmeldung fehlgeschlagen", Toast.LENGTH_SHORT).show();
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
            }
        });

      buttonNoLogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          String uid = Settings.Secure.getString(LoginActivity.this.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
          PersistenceHandler handler = new PersistenceHandler(LoginActivity.this);
          User user = handler.getOrCreateLocalUser(uid);

          Intent takeUserToSearch = new Intent(LoginActivity.this, SearchActivity.class);
                startActivity(takeUserToSearch);
         }
      });

        gotoRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //redirect to the registration activity
                Intent takeUserRegistration = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(takeUserRegistration);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
