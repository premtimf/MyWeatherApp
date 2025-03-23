package com.example.myweatherapp.utils

/**
 * Created by PremtimFarizi on 23/Mar/2025
 **/
class ImageLinkBuilder {

    class Builder private constructor(val icon: String) {

        companion object {
            private val BASE_URL = "https://openweathermap.org/img/wn/"
            fun setIcon(icon: String): Builder = Builder(icon)
        }

        fun build(): String {
            val link =  StringBuilder()
            link.append(BASE_URL)
            link.append("$icon@2x.png")
            return link.toString()
        }
    }
}