package io.github.dkocian.emulatorbackup.main;

import io.github.dkocian.emulatorbackup.common.Presenter;

class MainContract {

    interface UserActionListener extends Presenter {
        void saveFileSaveData();

        void getFileSaveData();
    }

    interface MainView {
        void showFileSavedSnackBar();

        void showFileRetrievedSnackBar();
    }
}
