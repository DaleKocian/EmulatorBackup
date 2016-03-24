package io.github.dkocian.emulatorbackup.common;

import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by dalek on 3/24/2016.
 */
public class ExternalStorageReaderWriter {
    private static final String TAG = ExternalStorageReaderWriter.class.getName();
    StringBuilder text = new StringBuilder();

    public ExternalStorageReaderWriter() {
    }

    public String readFile(File file) {
        String base64 = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
            }
            byte[] data = text.toString().getBytes("UTF-8");
            base64 = Base64.encodeToString(data, Base64.DEFAULT);
            br.close();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        return base64;
    }

    public void writeFile(String fileName, String base64String) throws IOException {
        byte[] decode = Base64.decode(base64String, Base64.DEFAULT);
        FileOutputStream fileOutputStream = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Constants.MY_BOY_SAVE).getPath() + "/" + fileName);
        fileOutputStream.write(decode);
        fileOutputStream.close();
    }
}
