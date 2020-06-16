package dev.nikhi1.sampleapp

import androidx.fragment.app.testing.FragmentScenario
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class SecondFragmentTest {

    private lateinit var fragmentScenario: FragmentScenario<SecondFragment>
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        fragmentScenario = FragmentScenario.launchInContainer(SecondFragment::class.java)
        navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())
        navController.setGraph(R.navigation.nav_graph)
        navController.setCurrentDestination(R.id.SecondFragment)
    }

    @Test
    fun find_and_click_button() {
        fragmentScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(ViewMatchers.withId(R.id.button_second)).perform(ViewActions.click())
        Truth.assertThat(navController.currentDestination?.id).isEqualTo(R.id.FirstFragment)
    }
}