package io.github.dkocian.emulatorbackup.common;

import com.firebase.client.Firebase;

public class FireBaseSingleton {
    private static Firebase mFirebaseRef;

    private FireBaseSingleton() {
        mFirebaseRef = new Firebase(Urls.HTTPS + Urls.BASE_URL + Constants.F_SLASH);
    }

    private static class SingletonHolder {
        private static final FireBaseSingleton INSTANCE = new FireBaseSingleton();
    }

    public static FireBaseSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Firebase getFirebaseRef() {
        return mFirebaseRef;
    }
}
