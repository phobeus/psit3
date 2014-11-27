package com.app.movietap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

// Should not extend BaseActifity, as the
// Navigation would be visible then
public class RegisterActivity extends Activity {

    protected EditText nickNameText;
    protected EditText emailText;
    protected EditText passwordText;
    protected Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //initialize data form xml
        nickNameText = (EditText)findViewById(R.id.nickRegisterText);
        emailText = (EditText)findViewById(R.id.emailRegisterText);
        passwordText = (EditText)findViewById(R.id.passwordRegisterText);
        registerButton = (Button)findViewById(R.id.submitRegisterButton);

        //listen to registration button click from xml activity view
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get the entered Form Data from Registration
                String username = nickNameText.getText().toString().trim().toLowerCase();
                String password = passwordText.getText().toString();
                String email = emailText.getText().toString().trim();

                signUp(username, password, email);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void signUp(String username, String password, String email) {
        ParseUser newUser = new ParseUser();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setEmail(email);

        newUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(RegisterActivity.this, "erfolgreich registriert", Toast.LENGTH_LONG).show();

                    //redirect user to homeActivity
                    Intent takeUserToSearch = new Intent(RegisterActivity.this, SearchActivity.class);
                    startActivity(takeUserToSearch);
                } else {
                    Toast.makeText(RegisterActivity.this, "Registrierung fehlgeschlagen", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //NOT WORKING FOR SOME REASON --> USE TOASTS INSTEAD!!
    protected void showError(String message, Exception e){
        //login was not secussfull
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setMessage(e.getMessage());
        builder.setTitle(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //colse the alert dialog
                dialogInterface.dismiss();
            }
        });
    }
}
