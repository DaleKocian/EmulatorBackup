package io.github.dkocian.emulatorbackup.common;

import android.content.Intent;

@SuppressWarnings("ALL")
public interface Presenter {
    void create(Intent intent);

    void resume();

    void pause();

    void refresh();
}
