package com.fahmi.fahmipundamentalandroid

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import com.fahmi.fahmipundamentalandroid.SettinganActivity.ThemeUtils.PREFS_NAME
import com.fahmi.fahmipundamentalandroid.SettinganActivity.ThemeUtils.PREF_DARK_THEME
import com.google.android.material.switchmaterial.SwitchMaterial


class SettinganActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_settingan)
//
//        val switchTheme = findViewById<SwitchMaterial>(R.id.ganti_tema)
//
//        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
//            if (isChecked) {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                switchTheme.isChecked = true
//                switchTheme.setText("Tema Terang")
//            } else {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                switchTheme.isChecked = false
//            }
//        }
//    }

    object ThemeUtils {

        const val PREFS_NAME = "theme_prefs"
        const val PREF_DARK_THEME = "dark_theme"

        fun applyTheme(context: Context) {
            val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val isDarkTheme = sharedPreferences.getBoolean(PREF_DARK_THEME, false)






            if (isDarkTheme) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

            }
        }

        fun saveThemeSelection(context: Context, isDarkTheme: Boolean) {
            val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            sharedPreferences.edit().putBoolean(PREF_DARK_THEME, isDarkTheme).apply()

        }
    }

    private var isDarkTheme = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settingan)



        val switchTheme = findViewById<SwitchMaterial>(R.id.ganti_tema)

        val sharedPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        isDarkTheme = sharedPrefs.getBoolean(PREF_DARK_THEME, false)
        switchTheme.isChecked = isDarkTheme


        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                ThemeUtils.saveThemeSelection(this, true) // Save dark theme preference
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.setText("Tema Terang")
                isDarkTheme = true
            } else {
                ThemeUtils.saveThemeSelection(this, false) // Save light theme preference
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.setText("Tema Gelap")
                isDarkTheme = false
            }

            with(sharedPrefs.edit()) {
                putBoolean(PREF_DARK_THEME, isDarkTheme)
                apply()
            }
        }





    }




}
