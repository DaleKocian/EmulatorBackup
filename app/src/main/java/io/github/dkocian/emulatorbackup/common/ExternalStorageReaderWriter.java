package io.github.dkocian.emulatorbackup.common;

import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class ExternalStorageReaderWriter {
    private static final String TAG = ExternalStorageReaderWriter.class.getName();
    StringBuilder text = new StringBuilder();

    public byte[] getFileContents(File file) {
        byte[] fileContents;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
            }
            fileContents = text.toString().getBytes(Constants.UTF_8);
            br.close();
        } catch (IOException e) {
            fileContents = null;
            Log.e(TAG, e.getMessage());
        }
        return fileContents;
    }

    public void writeFile(String fileName, String base64String) throws IOException {
        byte[] decode = Base64.decode(base64String, Base64.DEFAULT);
        FileOutputStream fileOutputStream = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Constants.MY_BOY_SAVE).getPath() + Constants.F_SLASH + fileName);
        fileOutputStream.write(decode);
        fileOutputStream.close();
    }
}
