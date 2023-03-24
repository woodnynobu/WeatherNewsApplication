package com.woodny.weathernewsapplication.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.woodny.weathernewsapplication.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_preferences, rootKey)
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        when(preference.key) {
            getString(R.string.key_terms_of_service) -> {

            }
            getString(R.string.key_privacy_policy) -> {

            }
            getString(R.string.key_license) -> {
                findNavController().navigate(R.id.action_settingsFragment_to_licenseFragment)
            }
        }
        return true
    }

}