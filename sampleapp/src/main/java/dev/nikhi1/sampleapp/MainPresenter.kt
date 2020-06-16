package dev.nikhi1.sampleapp

/**
 * An example presenter
 */
class MainPresenter {

    fun isValidEmail(email: String) = email.endsWith("google.com")
}