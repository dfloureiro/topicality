package com.dfl.topicality.splashscreen;

import com.dfl.topicality.base.BasePresenter;
import com.dfl.topicality.base.BaseState;
import com.dfl.topicality.base.BaseView;

/**
 * Created by loureiro on 18-02-2018.
 */

class SplashScreenContract {

    interface View extends BaseView {

        void finishSplash();

        void showNetworkError();
    }

    interface Presenter extends BasePresenter<State> {

        /**
         * instantiates and returns a state with variables to save
         *
         * @return state to save
         */
        SplashScreenContract.State getState();

    }

    interface State extends BaseState {


    }

}
