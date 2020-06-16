package dev.nikhi1.samplelib

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.FragmentScenario.launchInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import org.junit.After
import org.junit.Before
import org.junit.Test

class LibFragmentTest {

    private lateinit var fragmentScenario: FragmentScenario<LibFragment>

    @Before
    fun setUp() {
        fragmentScenario = launchInContainer(LibFragment::class.java)
    }

    @Test
    fun check_textview_content() {
        onView(ViewMatchers.withText("TextView1")).check(ViewAssertions.doesNotExist())
    }

    @After
    fun tearDown() {
    }
}