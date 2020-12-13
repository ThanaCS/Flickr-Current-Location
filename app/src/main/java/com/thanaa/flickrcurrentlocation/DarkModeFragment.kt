package com.thanaa.flickrcurrentlocation

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.thanaa.flickrcurrentlocation.databinding.FragmentDarkModeBinding


class DarkModeFragment : Fragment() {
    val NIGHT_MODE = "NightMood"
    val APP_PREF = "AppSettingsPrefs"
    private var _binding: FragmentDarkModeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDarkModeBinding.inflate(inflater, container, false)
        val appSettingsPref = requireActivity().getSharedPreferences(APP_PREF, 0)
        val sharedPrefsEdit: SharedPreferences.Editor = appSettingsPref.edit()
        val isNightModeOn: Boolean = appSettingsPref.getBoolean(NIGHT_MODE, true)

        if (isNightModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            binding.buttonMode.text = getString(R.string.disable_dark_mode)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            binding.buttonMode.text = getString(R.string.enable_dark_mode)
        }

        binding.buttonMode.setOnClickListener {
            if (isNightModeOn) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPrefsEdit.putBoolean(NIGHT_MODE, false)
                sharedPrefsEdit.apply()
                binding.buttonMode.text = getString(R.string.disable_dark_mode)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPrefsEdit.putBoolean(NIGHT_MODE, true)
                sharedPrefsEdit.apply()
                binding.buttonMode.text = getString(R.string.enable_dark_mode)
            }
        }
        return binding.root
    }

}

