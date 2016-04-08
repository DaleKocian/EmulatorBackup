package io.github.dkocian.emulatorbackup.common;

import android.os.Environment;
import android.util.Base64;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExternalStorageReaderWriter {
    private static final String TAG = ExternalStorageReaderWriter.class.getName();

    public void writeFile(String fileName, String base64String) throws IOException {
        byte[] decode = Base64.decode(base64String, Base64.DEFAULT);
        FileOutputStream fileOutputStream = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Constants.MY_BOY_SAVE).getPath() + Constants.F_SLASH + fileName);
        fileOutputStream.write(decode);
        fileOutputStream.close();
    }
}
