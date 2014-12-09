package com.app.movietap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Provides the ability to register a user via the parse library.
 * This activity does not implement the BaseActivity class.
 */
public class RegisterActivity extends Activity
{
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);

    //initialize data form xml
    nickNameText = (EditText) findViewById(R.id.nickRegisterText);
    emailText = (EditText) findViewById(R.id.emailRegisterText);
    passwordText = (EditText) findViewById(R.id.passwordRegisterText);
    registerButton = (Button) findViewById(R.id.submitRegisterButton);

    //listen to registration button click from xml activity view
    registerButton.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View view)
      {

        //Get the entered Form Data from Registration
        String username = nickNameText.getText().toString().trim().toLowerCase();
        String password = passwordText.getText().toString();
        String email = emailText.getText().toString().trim();

        signUp(username, password, email);
      }
    });
  }

  private void signUp(String username, String password, String email)
  {
    ParseUser newUser = new ParseUser();
    newUser.setUsername(username);
    newUser.setPassword(password);
    newUser.setEmail(email);

    newUser.signUpInBackground(new SignUpCallback()
    {
      @Override
      public void done(ParseException e)
      {
        if (e == null)
        {
          Toast.makeText(RegisterActivity.this, "erfolgreich registriert", Toast.LENGTH_LONG).show();

          //redirect user to homeActivity
          Intent takeUserToSearch = new Intent(RegisterActivity.this, SearchActivity.class);
          startActivity(takeUserToSearch);
        } else
        {
          Toast.makeText(RegisterActivity.this, "Registrierung fehlgeschlagen", Toast.LENGTH_SHORT).show();
        }
      }
    });
  }

  protected EditText nickNameText;
  protected EditText emailText;
  protected EditText passwordText;
  protected Button registerButton;
}
