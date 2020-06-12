package dev.nikhi1.sampleapp

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.FragmentScenario.launchInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class FirstFragmentTest {

    private lateinit var fragmentScenario: FragmentScenario<FirstFragment>
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        fragmentScenario = launchInContainer(FirstFragment::class.java)
        navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())
        navController.setGraph(R.navigation.nav_graph)
    }

    @Test
    fun find_and_click_button() {
        fragmentScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.button_first)).perform(ViewActions.click())
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.SecondFragment)
    }
}
