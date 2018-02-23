package com.dfl.topicality.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.dfl.topicality.MainActivity;
import com.dfl.topicality.R;
import com.dfl.topicality.TopicalityApplication;
import com.dfl.topicality.settings.UserSettingsPersistence;

/**
 * Created by loureiro on 18-02-2018.
 */

public class SplashScreenActivity extends AppCompatActivity implements SplashScreenContract.View {

    private SplashScreenContract.Presenter presenter;
    private UserSettingsPersistence userSettingsPersistence;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        userSettingsPersistence = ((TopicalityApplication) getApplication()).getUserSettingsPersistence();

        if (userSettingsPersistence.isFirstBoot()) {
            presenter = new SplashScreenPresenter(this, ((TopicalityApplication) getApplication()).getRequestFactory(),
                    ((TopicalityApplication) getApplication()).getDatabase(), userSettingsPersistence.getCountry(), userSettingsPersistence.getLanguage());
            presenter.subscribe(null);
        } else {
            finishSplash();
        }
    }

    @Override
    public void finishSplash() {
        userSettingsPersistence.setFirstBoot();
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (presenter != null) {
            presenter.unsubscribe();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter = null;
        userSettingsPersistence = null;
    }
}