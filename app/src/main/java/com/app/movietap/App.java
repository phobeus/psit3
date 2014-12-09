package com.app.movietap;

import android.app.Application;
import com.parse.Parse;

/**
 * Main class, initializes the parse library.
 */
public class App extends Application {

    @Override public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "7B1jggAopjvJLcyC0DsPaUyA8tR7HoccLPrqWFdd", "C7m1jmzJqhoix9ENdWJH4zAcvnU00p9dPREDmn5c");
    }
}
