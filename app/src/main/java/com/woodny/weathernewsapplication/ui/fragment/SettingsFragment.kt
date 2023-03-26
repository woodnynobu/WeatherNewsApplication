package com.woodny.weathernewsapplication.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.woodny.weathernewsapplication.BuildConfig
import com.woodny.weathernewsapplication.R
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {

    @Inject
    lateinit var analytics: FirebaseAnalytics

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_preferences, rootKey)

        val appVersionPreference: Preference? = findPreference(getString(R.string.key_app_version))
        appVersionPreference?.let {
            it.summary = BuildConfig.VERSION_NAME
        }

        analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, SettingsFragment::class.java.simpleName)
        }
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        when(preference.key) {
            getString(R.string.key_terms_of_service) -> {
                val action = SettingsFragmentDirections.actionSettingsFragmentToCommonWebFragment(BuildConfig.TERMS_OF_SERVICE_URL)
                findNavController().navigate(action)
            }
            getString(R.string.key_privacy_policy) -> {
                val action = SettingsFragmentDirections.actionSettingsFragmentToCommonWebFragment(BuildConfig.PRIVACY_POLICY_URL)
                findNavController().navigate(action)
            }
            getString(R.string.key_license) -> {
                startActivity(Intent(context, OssLicensesMenuActivity::class.java))
            }
        }
        return true
    }

}