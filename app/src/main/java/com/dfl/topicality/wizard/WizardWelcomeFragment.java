package com.dfl.topicality.wizard;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dfl.topicality.R;

/**
 * Created by loureiro on 01-02-2018.
 */

public class WizardWelcomeFragment extends Fragment {


    public WizardWelcomeFragment() {

    }

    public static WizardWelcomeFragment newInstance() {
        return new WizardWelcomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.content_wizard_welcome, container, false);
    }
}
