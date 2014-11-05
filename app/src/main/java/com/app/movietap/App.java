package com.app.movietap;

/**
 * Created by andreas on 27.10.14.
 */
import android.app.Application;
import com.parse.Parse;

public class App extends Application {

    @Override public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "7B1jggAopjvJLcyC0DsPaUyA8tR7HoccLPrqWFdd", "C7m1jmzJqhoix9ENdWJH4zAcvnU00p9dPREDmn5c");
    }
}
