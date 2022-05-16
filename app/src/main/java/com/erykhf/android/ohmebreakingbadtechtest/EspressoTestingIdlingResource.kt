package com.erykhf.android.ohmebreakingbadtechtest

import androidx.test.espresso.IdlingResource

import androidx.test.espresso.idling.CountingIdlingResource


object EspressoTestingIdlingResource {
    private const val RESOURCE = "GLOBAL"
    val idlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        idlingResource.increment()
    }

    fun decrement() {
        idlingResource.decrement()
    }
}