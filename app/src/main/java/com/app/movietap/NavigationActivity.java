package com.app.movietap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class NavigationActivity extends Activity {


    protected Button homeNavButton;
    protected Button registerNavButton;
    protected Button moviesNavButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);



        homeNavButton = (Button)findViewById(R.id.buttonHome);
        registerNavButton = (Button)findViewById(R.id.buttonRegistration);
        moviesNavButton = (Button)findViewById(R.id.buttonMovies);

        //listen to registration button click from xml activity view
        homeNavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if successful go to homepage
                Intent takeUserHome = new Intent(NavigationActivity.this, HomeActivity.class);
                startActivity(takeUserHome);
            }});

        registerNavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //if successful go to homepage
                Intent takeUserRegister = new Intent(NavigationActivity.this, RegisterActivity.class);
                startActivity(takeUserRegister);
            }});

        moviesNavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if successful go to homepage
                Intent takeUserMovies = new Intent(NavigationActivity.this, BrowseMoviesActivity.class);
                startActivity(takeUserMovies);
            }});

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_navigation, menu);
        return true;
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
