package com.dfl.topicality.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dfl.topicality.MainActivity;
import com.dfl.topicality.R;
import com.dfl.topicality.TopicalityApplication;
import com.dfl.topicality.settings.UserSettingsPersistence;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by loureiro on 18-02-2018.
 */

public class SplashScreenActivity extends AppCompatActivity implements SplashScreenContract.View {

    @BindView(R.id.image_view_splash)
    ImageView splashImage;
    @BindView(R.id.text_view_splash)
    TextView splashText;
    @BindView(R.id.network_error_layout)
    RelativeLayout networkErrorLayout;

    private SplashScreenContract.Presenter presenter;
    private UserSettingsPersistence userSettingsPersistence;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        userSettingsPersistence = ((TopicalityApplication) getApplication()).getUserSettingsPersistence();

        if (userSettingsPersistence.isFirstBoot()) {
            userSettingsPersistence.setCountry(userSettingsPersistence.getCountry().toString());
            userSettingsPersistence.setLanguage(userSettingsPersistence.getLanguage().toString());
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
    public void showNetworkError() {
        splashImage.setVisibility(View.GONE);
        splashText.setVisibility(View.GONE);
        networkErrorLayout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.button_retry_network_error)
    public void onButtonRetryNetworkErrorClick() {
        splashImage.setVisibility(View.VISIBLE);
        splashText.setVisibility(View.VISIBLE);
        networkErrorLayout.setVisibility(View.GONE);
        presenter.subscribe(null);
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