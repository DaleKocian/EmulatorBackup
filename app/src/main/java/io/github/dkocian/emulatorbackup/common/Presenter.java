package io.github.dkocian.emulatorbackup.common;

import android.content.Intent;

/**
 * Created by dalek on 3/24/2016.
 */
public interface Presenter {
    void create(Intent intent);

    void resume();

    void pause();

    void refresh();
}
