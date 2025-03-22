package com.example.myweatherapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.myweatherapp.utils.SharedPreferencesUtils
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by PremtimFarizi on 22/Mar/2025
 **/
@RunWith(AndroidJUnit4::class)
class SharedPreferencesTest {

    @Test
    fun test_shared_preferences() {
        val myNameKey = "MY_NAME_KEY"
        val myName = "Premtim"
        val defaultName = "defaultName"
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        SharedPreferencesUtils.init(appContext)
        SharedPreferencesUtils.sharedPreferences?.edit()?.putString(myNameKey, myName)?.apply()
        val myNameSavedInSharedPreferences = SharedPreferencesUtils.sharedPreferences?.getString(myNameKey, defaultName)
        assertEquals(myName, myNameSavedInSharedPreferences)
    }
}