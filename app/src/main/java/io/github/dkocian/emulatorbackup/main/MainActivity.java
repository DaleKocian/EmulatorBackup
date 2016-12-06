package io.github.dkocian.emulatorbackup.main;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.dkocian.emulatorbackup.R;

public class MainActivity extends AppCompatActivity implements MainContract.MainView {
    private static final String TAG = MainActivity.class.getName();

    @BindView(R.id.rlWrapper)
    RelativeLayout rlWrapper;

    private MainContract.UserActionListener userActionListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        userActionListener = new MainPresenter(this);
        userActionListener.create(getIntent());
    }

    @OnClick(R.id.btnSaveData)
    public void onClickSave() {
        userActionListener.saveFileSaveData();
    }

    @OnClick(R.id.btnGetData)
    public void onGetSaveDataClicked() {
        userActionListener.getFileSaveData();
    }

    @Override
    public void showFileSavedSnackBar() {
        Snackbar.make(rlWrapper, R.string.file_saved, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showFileRetrievedSnackBar() {
        Snackbar.make(rlWrapper, R.string.file_retrieved, Snackbar.LENGTH_SHORT).show();
    }

    private boolean checkPlayServices() {
//        int result = GooglePlayServicesUtil.
        return true;
    }
}
