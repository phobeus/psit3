package com.app.movietap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseObject;


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
                String username = nickNameText.getText().toString().trim();
                String password = passwordText.getText().toString();
                String email = emailText.getText().toString().trim();

                //parse.com test
                ParseObject testObject = new ParseObject("User");
                testObject.put("name", username);
                testObject.put("email", email);
                testObject.put("password", password);
                testObject.saveInBackground();
                /*-->> callback to check if parse succeed
                * new SaveCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            //myObjectSavedSuccessfully();
                            Log.d("MovieTap_savingParse", "Success");
                        } else {
                            //myObjectSaveDidNotSucceed();
                            Log.d("MovieTap_savingParse", e.getMessage());
                        }
                    }
                }
                * */

                //toast -> messagon on android device
                Toast.makeText(RegisterActivity.this, "Hello " + username + "!", Toast.LENGTH_LONG).show();


                //if successful go to homepage
                Intent takeUserhome = new Intent(RegisterActivity.this, HomeActivity.class);
                startActivity(takeUserhome);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register, menu);
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
}
