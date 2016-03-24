package io.github.dkocian.emulatorbackup;

import android.app.Application;
import android.content.Context;

import com.firebase.client.Firebase;

public class MyApplication extends Application {
    static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        this.context = this;

    }

    public static Context getMyApplicationContext() {
        return context;
    }
}