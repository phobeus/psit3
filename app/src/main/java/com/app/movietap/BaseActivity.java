package com.app.movietap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * This abstract class provides the basic action bar menu which has to be
 * present on every activity
 */
public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_navigation, menu);
        return true;
    }

    /**
     * react to actionbar events by forwarding user to desired activity.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_collection) {
            Intent intent = createActivity("BrowseMoviesActivity");
            startActivity(intent);
        } else if (id == R.id.action_wish) {
            Intent intent = createActivity("RegisterActivity");
            startActivity(intent);
        } else if (id == R.id.action_filter) {
            Intent intent = createActivity("HomeActivity");
            startActivity(intent);
        } else if (id == R.id.action_search) {
            /*get search query*/
            Intent intent = createActivity("NavigationActivity");
            startActivity(intent);
        } else {
            return super.onOptionsItemSelected(item);
        }

        return true;

    }

    /**
     * create a new intent and returns it. checks if activity is already
     * running.
     *
     * @param classNameToActivate
     * class name of activity
     * @return new Intent
     */
    protected Intent createActivity(String classNameToActivate) {
        try {
            Class<?> activityClass = Class.forName("com.app.movietap."
                    + classNameToActivate);
            Intent intent = new Intent(getApplicationContext(), activityClass);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            // startActivity(intent);
            return intent;
        } catch (ClassNotFoundException ex) {
            Log.e("movietap", "Menu error", ex);
        }

        // return main activity in case of not found activity
        return createActivity("com.app.movietap.NavigationActivity");
    }

}