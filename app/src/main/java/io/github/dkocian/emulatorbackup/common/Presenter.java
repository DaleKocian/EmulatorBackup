package io.github.dkocian.emulatorbackup.common;

import android.content.Intent;

public interface Presenter {
    void create(Intent intent);

    void resume();

    void pause();

    void refresh();
}
