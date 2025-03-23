package com.example.myweatherapp

import com.example.myweatherapp.utils.ImageLinkBuilder
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by PremtimFarizi on 23/Mar/2025
 **/
class ImageLinkBuilderTest {
    @Test
    fun test_LinkBuilder() {
        val fullLink = "https://openweathermap.org/img/wn/10d@2x.png"
        val iconLink = "10d"
        val buildLink = ImageLinkBuilder.Builder.setIcon(iconLink).build()
        assertEquals(fullLink, buildLink)
    }
}