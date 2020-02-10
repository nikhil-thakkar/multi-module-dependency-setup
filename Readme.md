# Multiple Module Dependency setup

The purpose of this repo to demonstrate the use of a custom **Gradle** plugin to centralize the dependency management in multi-module app setup.

When you start breaking down your Android app into different modules you need to do the following steps for each one:

* Define `compileSdkVersion`, `buildToolsVersion`, `minSdkVersion`, `targetSdkVersion` etc.
* Copy the `dependencies` block even though you might be repeating yourself. This is specially true for common and test dependencies.
* Make sure each feature module depends on the `app` module or whatever base module you have.

The obvious side-effect of this is the versioning nightmare if the same library is being consumed across different modules which might lead to bloated apk sizes.
We don't want that or do we :thinking:.

I recently started working on a sample [project](https://github.com/nikhil-thakkar/eventbrite-clone) which demonstrate the use of `dynamic-feature` modules and found myself in a similar situation.
This lead me to do some research on the topic and :money_mouth_face: I found people already have a solution.
There are a couple of blog posts that I found interesting (outlined below) and inspired me to dig further.

# Setup
The actual setup is pretty simple. The code is also pretty self-explanatory. All you have to do is copy the `buildSrc` folder to root of your app project.
And click on **Gradle sync** so that gradle can pick up the source and build the plugin and add it to build phase.

After the project builds successfully the only step is to apply the plugin in your individual feature modules `build.gradle`.
```
apply plugin: 'dev.nikhi1.plugin.android'
```
Make sure to apply this plugin after either `application` and/or `dynamic-feature` plugin definition.

This plugin will configure the following:
* `compileSdkVersion`, `buildToolsVersion`, `minSdkVersion`, `targetSdkVersion`, `compileOptions`, `testInstrumentationRunner`
* Add dependency on `app` module for feature module
* Add common test dependencies like junit, mockk

Note: You can still override properties which are set by this plugin, by just configuring them again using the android {} block :sunglasses:.

# Minimum requirements
* Gradle version: v5.4.1
* Android Studio: v3.5

Feel free to modify to the source to suit your needs.

# References
* [Managing dependencies in multi-module setup](https://medium.com/wantedly-engineering/managing-android-multi-module-project-with-gradle-plugin-and-kotlin-4fcc126e7e49)
* [buildSrc Trick](https://quickbirdstudios.com/blog/gradle-kotlin-buildsrc-plugin-android/)
* [Modularization Tips](https://jeroenmols.com/blog/2019/06/12/modularizationtips/)
* [Android Gradle Plugin Reference](https://google.github.io/android-gradle-dsl/current/index.html) - Not sure if this is being maintained
