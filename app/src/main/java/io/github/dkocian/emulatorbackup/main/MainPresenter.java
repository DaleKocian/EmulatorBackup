package io.github.dkocian.emulatorbackup.main;

import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.File;
import java.io.IOException;

import io.github.dkocian.emulatorbackup.common.Constants;
import io.github.dkocian.emulatorbackup.common.ExternalStorageReaderWriter;
import io.github.dkocian.emulatorbackup.common.FireBaseSingleton;

import static io.github.dkocian.emulatorbackup.common.RegexPatterns.MATCH_MY_BOY_SAVE_DATA;
import static io.github.dkocian.emulatorbackup.common.RegexPatterns.MATCH_PERIODS;

/**
 * Created by dalek on 3/24/2016.
 */
public class MainPresenter implements MainContract.UserActionListener {
    public static final String TAG = MainPresenter.class.getName();
    public static final String FILE_NAME = "FileName:";

    MainContract.MainView mainView;

    public MainPresenter(MainContract.MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void create(Intent intent) {
    }

    private File[] getSaveFiles() {
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Constants.MY_BOY_SAVE);
        return externalStoragePublicDirectory.listFiles();
    }

    @Override
    public void saveFileSaveData() {
        File[] saveFiles = getSaveFiles();
        Log.d(TAG, "Size: " + saveFiles.length);
        for (File file : saveFiles) {
            String[] fileNameArray = file.getName().split(MATCH_PERIODS);
            String extension = fileNameArray[fileNameArray.length - 1];
            String fileName = fileNameArray[0] + "-" + extension;
            if (extension.matches(MATCH_MY_BOY_SAVE_DATA)) {
                Log.d(TAG, FILE_NAME + file.getName());
                ExternalStorageReaderWriter externalStorageReaderWriter = new ExternalStorageReaderWriter();
                String base64OfFile = externalStorageReaderWriter.readFile(file);
                Log.e(TAG, base64OfFile);
                FireBaseSingleton.getInstance().getFirebaseRef().child(fileName).setValue(base64OfFile, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        mainView.showFileSavedSnackBar();
                    }
                });
            }
        }
    }

    @Override
    public void getFileSaveData() {
        FireBaseSingleton.getInstance().getFirebaseRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String key = postSnapshot.getKey();
                    String base64OfFile = (String) postSnapshot.getValue();
                    ExternalStorageReaderWriter externalStorageReaderWriter = new ExternalStorageReaderWriter();
                    try {
                        int indexOfDash = key.lastIndexOf("-");
                        StringBuilder fileNameBuilder = new StringBuilder(key);
                        fileNameBuilder.setCharAt(indexOfDash, '.');
                        externalStorageReaderWriter.writeFile(fileNameBuilder.toString(), base64OfFile);
                        mainView.showFileRetrievedSnackBar();
                    } catch (IOException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void refresh() {
    }
}
