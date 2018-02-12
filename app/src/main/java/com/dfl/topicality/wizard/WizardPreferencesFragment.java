package com.dfl.topicality.wizard;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.dfl.topicality.MainActivity;
import com.dfl.topicality.R;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by loureiro on 01-02-2018.
 */

public class WizardPreferencesFragment extends Fragment {

    @BindView(R.id.spinner_country)
    Spinner countrySpinner;
    @BindView(R.id.spinner_language)
    Spinner languageSpinner;
    @BindView(R.id.wizard_finish_button)
    Button finishButton;

    private Unbinder unbinder;

    public WizardPreferencesFragment() {

    }

    public static WizardPreferencesFragment newInstance() {
        return new WizardPreferencesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(
                R.layout.content_wizard_preferences, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String locale = getActivity().getResources().getConfiguration().locale.getCountry();
        String language = getActivity().getResources().getConfiguration().locale.getLanguage();

        String[] countryNames = getResources().getStringArray(R.array.country_names);
        String[] countryCodes = getResources().getStringArray(R.array.country_codes);
        for (int position = 0; position < countryCodes.length; position++) {
            if (Arrays.asList(countryCodes).get(position).equalsIgnoreCase(locale)) {
                countrySpinner.setSelection(position);
                position = countryCodes.length;
                // TODO: 01-02-2018 save country code
            }
        }

        String[] languageNames = getResources().getStringArray(R.array.language_names);
        String[] languageCodes = getResources().getStringArray(R.array.language_codes);
        for (int position = 0; position < languageCodes.length; position++) {
            if (Arrays.asList(languageCodes).get(position).equalsIgnoreCase(language)) {
                languageSpinner.setSelection(position);
                position = languageCodes.length;
                // TODO: 01-02-2018 save language code
            }
        }
    }

    @OnClick(R.id.wizard_finish_button)
    void onClickFinishButton() {
        Intent myIntent = new Intent(getActivity(), MainActivity.class);
        getActivity().startActivity(myIntent);
        getActivity().finish();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}