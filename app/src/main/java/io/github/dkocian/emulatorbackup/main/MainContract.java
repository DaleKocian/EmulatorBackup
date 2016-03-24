package io.github.dkocian.emulatorbackup.main;

import io.github.dkocian.emulatorbackup.common.Presenter;

/**
 * Created by dalek on 3/24/2016.
 */
public class MainContract {
    interface UserActionListener extends Presenter {
        void saveFileSaveData();

        void getFileSaveData();
    }

    interface MainView {
        void showFileSavedSnackBar();

        void showFileRetrievedSnackBar();
    }
}
