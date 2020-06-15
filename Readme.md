# Multiple Module Dependency setup :package:

The purpose of this repo to demonstrate the use of a custom **Gradle** plugin to centralize the dependency management in multi-module app setup.

When you start breaking down your Android app into different modules you need to do the following steps for each one:

* Define `compileSdkVersion`, `buildToolsVersion`, `minSdkVersion`, `targetSdkVersion` etc.
* Copy the `dependencies` block even though you might be repeating yourself. This is specially true for common and test dependencies.
* ~~Make sure each feature module depends on the `app` module or whatever base module you have.~~ Turn outs this is a bit of complex scenario to infer for simple use case. This has been removed from the `Plugin` implementation.

The obvious side-effect of this is the versioning nightmare if the same library is being consumed across different modules which might lead to bloated apk sizes.
We don't want that or do we :thinking:.

I recently started working on a sample [project](https://github.com/nikhil-thakkar/eventbrite-clone) which demonstrate the use of `dynamic-feature` modules and found myself in a similar situation.
This lead me to do some research on the topic and I found people already have a solution :money_mouth_face:.
There are a couple of blog posts that I found interesting (outlined below) and inspired me to dig further.

# About :books:
The repo contains two example android modules namely `sampleapp` and `samplelib`. These modules demonstrate the usage of the `Plugin` in their respective `build.gradle` files [here](https://github.com/nikhil-thakkar/multi-module-dependency-setup/blob/927ab581e25f7e30d524bd72a78104612dfe18c9/sampleapp/build.gradle.kts#L1-L4) and [here](https://github.com/nikhil-thakkar/multi-module-dependency-setup/blob/927ab581e25f7e30d524bd72a78104612dfe18c9/samplelib/build.gradle.kts#L1-L4).

The, `AndroidModulePlugin`, plugin will configure the following for each of the modules:
* `compileSdkVersion`, `buildToolsVersion`, `minSdkVersion`, `targetSdkVersion`, `compileOptions`, `testInstrumentationRunner`
* Add common test dependencies like junit, mockk
* Configure SonarQube and its properties
* Configure Jacoco

Note: You can still override properties which are set by this plugin, by just configuring them again using the android {} or other blocks :sunglasses:.


# Use it in your project :cookie:
The actual setup is pretty simple. The code is also pretty self-explanatory. All you have to do is copy the `buildSrc` folder to root of your app project.
And click on **Gradle sync**![alt gradle sync icon](https://developer.android.com/studio/images/buttons/toolbar-sync-gradle.png) so that gradle can pick up the source and build the plugin and add it to build phase.

After the project builds successfully the only step is to apply the plugin in your individual app/library/feature modules `build.gradle`.
```
apply plugin: 'dev.nikhi1.plugin.android'
```
Make sure to apply this plugin after either `application`, `library` and/or `dynamic-feature` plugin definition in their respective gradle files.

# Why Jacoco?
Jacoco is a tool to measure code coverage and currently the most widely used.

> Code coverage in simpler terms means how much of the production code that you wrote is being executed at runtime. 

To measure this you write Unit\UI\Integration tests. And this is what Jacoco does under the hood, it hooks on to these tests while they are executing. In the process, it can see what code is executed as part of the tests and calculates its coverage.
These coverages are helpful, for example, if you have miss handling an `else` branch in one of your test cases.

Though on Android, the test execution data (.ec file) for UI test cases is not taken into account by Jacoco and hence we have to tweak the gradle task and make sure if also reads those files to give a complete code coverage across Unit and UI/Integration test cases.

The task outputs xml reports per module which are uploaded to SonarQube for a nice looking dashboard :tada:. 

This example [repo](https://sonarcloud.io/dashboard?id=nikhil-thakkar_multi-module-dependency-setup) has 100% code coverage. Of course this doesn't mean the code is rock solid and is following all the SOLID principles of software engineering. It's more an indicator that code bases are lacking test cases and we should do something about it :smile:.

If you are not ready with test cases yet then remove the method `configureJacoco` from the `AndroidModulePlugin` class. Every method is self-contained and could be added/removed on need basis.

# Why SonarQube?
SonarQube is the leading tool for continuously inspecting the Code Quality and Security of your codebases and guiding development teams during Code Reviews.

These analyses are driven by automated Static Code Analysis rules. You can add your own rules or use [detekt](https://detekt.github.io/detekt/) rules for analysis as well.

The `Plugin` configures the different properties required for SonarQube plugin to work properly. For example, the path of the Jacoco xml reports etc. Check [here](https://github.com/nikhil-thakkar/multi-module-dependency-setup/blob/927ab581e25f7e30d524bd72a78104612dfe18c9/buildSrc/src/main/kotlin/AndroidModulePlugin.kt#L120-L145).

This repo uses [`sonarcloud.io`](https://sonarcloud.io) which is free for open-source projects and is a cloud hosted solution. But in most cases, there would be a private hosted SonarQube server running in the infra. Just configure the `Plugin` with correct values.

Refer [this](https://github.com/nikhil-thakkar/eventbrite-clone/blob/master/.github/workflows/pull_request.yml) github action if you feel lost.

# A word about CI :gear:
Irrespective of any CI tool, in theory, we need to run some gradle tasks. These gradle tasks are for most cases either run Unit and/or UI tests. Post that some static code analysis which could be either `ktlint` or `detekt` or something on similar lines.

Here is the minimum gradle tasks that needs to be run
```
./gradlew clean connectedDebugAndroidTest jacocoTestDebugUnitTestReport sonarqube
```
Ofcourse you have to make sure that an emulator/device is up and running in order to run the UI test cases.

If everything is set up properly, you should be able to see the analysis report on the configured SonarQube instance.

# Minimum requirements
* Gradle version: v6.x
* Android Studio: v4.0

# Known limitations
* Currently there is no way to combine the jacoco reports across different modules. This [plugin](https://github.com/vanniktech/gradle-android-junit-jacoco-plugin) might help.
* Cannot skip SonarQube analysis for a particular module.
* Not tested with `Roboelectric` framework.

# References
* [Managing dependencies in multi-module setup](https://medium.com/wantedly-engineering/managing-android-multi-module-project-with-gradle-plugin-and-kotlin-4fcc126e7e49)
* [buildSrc Trick](https://quickbirdstudios.com/blog/gradle-kotlin-buildsrc-plugin-android/)
* [Modularization Tips](https://jeroenmols.com/blog/2019/06/12/modularizationtips/)
* [Code coverage in practice](https://www.rallyhealth.com/coding/code-coverage-for-android-testing)
* [Android Gradle Plugin Reference](https://google.github.io/android-gradle-dsl/current/index.html) - Not sure if this is being maintained
