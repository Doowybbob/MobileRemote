package com.doowybbob.MRApp;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

public class RemoteSettingsFragment extends PreferenceFragment {

	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.remote_preferences);
	}
	
}
