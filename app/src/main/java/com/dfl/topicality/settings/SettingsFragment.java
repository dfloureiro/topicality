package com.dfl.topicality.settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.text.SpannableString;
import android.text.util.Linkify;
import android.widget.Toast;

import com.dfl.topicality.R;

/**
 * Created by loureiro on 03-03-2018.
 */

public class SettingsFragment extends PreferenceFragmentCompat {

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        getPreferenceManager().setSharedPreferencesMode(UserSettingsPersistence.PREFS_MODE);
        getPreferenceManager().setSharedPreferencesName(UserSettingsPersistence.PREFS_KEY);
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        if (preference.getKey().equals("ABOUT_KEY")) {
            DialogFragment newFragment = new AboutDialogFragment();
            newFragment.show(getFragmentManager(), "about");
        } else if (preference.getKey().equals("CONTACT_US_KEY")) {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.developer_email)});
            i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.feedback_email_subject));
            try {
                startActivity(Intent.createChooser(i, getString(R.string.feedback_email_chooser)));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getActivity(), R.string.feedback_email_no_email_client_message, Toast.LENGTH_SHORT).show();
            }
        }
        return super.onPreferenceTreeClick(preference);
    }

    public static class AboutDialogFragment extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final SpannableString message = new SpannableString(getContext().getText(R.string.about_dialog_message));
            Linkify.addLinks(message, Linkify.WEB_URLS);

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.about_dialog_title)
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok, (dialog, id) -> {
                    });
            return builder.create();
        }
    }
}
