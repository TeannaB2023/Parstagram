package com.codepath.parstagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Register Parse models
        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("KGoqwIyxL6QmXTilnlhrgpODc8EowNc3nOtKqThY")
                .clientKey("ztamahbSxQnRWYW93HsFnDQzKsM2Swga4Sx0RKDO")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
