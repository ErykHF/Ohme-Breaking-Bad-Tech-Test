package com.erykhf.android.ohmebreakingbadtechtest

import androidx.test.espresso.IdlingResource

import androidx.test.espresso.idling.CountingIdlingResource


object EspressoTestingIdlingResource {
    private const val RESOURCE = "GLOBAL"
    private val mCountingIdlingResource: CountingIdlingResource = CountingIdlingResource(RESOURCE)
    fun increment() {
        mCountingIdlingResource.increment()
    }

    fun decrement() {
        mCountingIdlingResource.decrement()
    }

    val idlingResource: IdlingResource
        get() = mCountingIdlingResource
}