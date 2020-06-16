package dev.nikhi1.samplelib

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MainPresenterTest {

    lateinit var presenter: MainPresenter

    @Before
    fun setUp() {
        presenter = MainPresenter()
    }

    @Test
    fun check_valid_email() {
        Assert.assertEquals(true, presenter.isValidEmail("nikhil@google.com"))
    }

    @Test
    fun check_invalid_email() {
        Assert.assertEquals(false, presenter.isValidEmail("nikhil@yahoo.com"))
    }
}