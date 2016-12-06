package io.github.dkocian.emulatorbackup.main;

import android.content.Intent;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import com.annimon.stream.Stream;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;

import io.github.dkocian.emulatorbackup.common.Constants;
import io.github.dkocian.emulatorbackup.common.ExternalStorageReaderWriter;
import io.github.dkocian.emulatorbackup.common.FireBaseSingleton;

import static io.github.dkocian.emulatorbackup.common.RegexPatterns.MATCH_MY_BOY_SAVE_DATA;

class MainPresenter implements MainContract.UserActionListener {
    private static final String TAG = MainPresenter.class.getName();
    private static final String FILE_NAME = "FileName:";
    private static final String SIZE_MSG = "Size: ";

    private final MainContract.MainView mainView;

    MainPresenter(MainContract.MainView mainView) {
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
        Log.d(TAG, SIZE_MSG + saveFiles.length);
        Stream.of(saveFiles)
                .filter(file -> Files.getFileExtension(file.getName()).matches(MATCH_MY_BOY_SAVE_DATA))
                .forEach(file -> {
                    Log.d(TAG, FILE_NAME + file.getName());
                    try {
                        String savedFileName = file.getName() + '-' + Files.getFileExtension(file.getName());
                        FireBaseSingleton.getInstance().getFirebaseRef().child(Files.getNameWithoutExtension(savedFileName))
                                .setValue(Base64.encodeToString(Files.toByteArray(file), Base64.DEFAULT), (firebaseError, firebase) -> mainView.showFileSavedSnackBar());
                    } catch (IOException e) {
                        Log.e(TAG, e.getMessage());
                    }
                });
    }

    @Override
    public void getFileSaveData() {
        FireBaseSingleton.getInstance().getFirebaseRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Stream.of(dataSnapshot.getChildren())
                        .forEach(postSnapshot -> {
                            String key = postSnapshot.getKey();
                            String base64OfFile = (String) postSnapshot.getValue();
                            ExternalStorageReaderWriter externalStorageReaderWriter = new ExternalStorageReaderWriter();
                            try {
                                int indexOfDash = key.lastIndexOf(Constants.DASH);
                                StringBuilder fileNameBuilder = new StringBuilder(key);
                                fileNameBuilder.setCharAt(indexOfDash, Constants.CHAR_DOT);
                                externalStorageReaderWriter.writeFile(fileNameBuilder.toString(), base64OfFile);
                                mainView.showFileRetrievedSnackBar();
                            } catch (IOException e) {
                                Log.e(TAG, e.getMessage());
                            }
                        });
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
