package io.github.dkocian.emulatorbackup;

import android.app.Application;

import com.firebase.client.Firebase;
import com.squareup.leakcanary.LeakCanary;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        LeakCanary.install(this);
    }
}