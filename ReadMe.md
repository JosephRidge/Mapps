 https://docs.mapbox.com/android/maps/guides/install/#work-with-jetpack-compose-extension 

How to save(best practice) a secret key/ token in an android app

Add Secret Key in Android app:

Steps:
In your project root directory add a file and call it secrets.properties :


We then add dependencies:
Target file: build.gradle (app level), at the plugin section add:

id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1"









Your plugin object will look like this:

plugins {
alias(libs.plugins.android.application)
alias(libs.plugins.kotlin.android)
alias(libs.plugins.kotlin.compose)
id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1"
}

Under the android object, navigate to the defaultconfig (still under the build.gradle file app level)
In my case i am storing a MAPBOX_API_KEY hence i name it in accordance to what it is.

Add the following:

buildConfigField("String", "MAPBOX_API_KEY", "\"${project.findProperty("MAPBOX_API_KEY")}\"")
The reason we add it as a buildConfig is because we will ref to it using the buildConfigurations.

Now your default config object will look like this:


defaultConfig {
applicationId = "com.jayr.mapboxmap"
minSdk = 24
targetSdk = 35
versionCode = 1
versionName = "1.0"
buildConfigField("String", "MAPBOX_API_KEY", "\"${project.findProperty("MAPBOX_API_KEY")}\"")
testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}





Next bit is to add the buildFeatures bit, where you will notice you have compose = true
If that is not the case then make sure you add it this way:


buildFeatures {
compose = true
buildConfig = true
}

The next bit now is we add the a secrets object, this serves the purpose of referencing to the secrets file itself.

So add this after the close of the android object:

secrets {
propertiesFileName = "secrets.properties"
defaultPropertiesFileName = "local.properties"
}


Finally in you buil.gradle file you add the following dependencies:

Add as this:


    implementation(libs.secrets.gradle.plugin)
    implementation(libs.maps.compose)









Navigate to the libs.versions.toml file and add the following:

Under [versions] :


mapsCompose = "11.13.5"
secretsGradlePlugin = "2.0.1"


Under [libraries] :

maps-compose = { module = "com.mapbox.extension:maps-compose", version.ref = "mapsCompose" }
secrets-gradle-plugin = { module = "com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin", version.ref = "secretsGradlePlugin" }



Final outcome in your build.gradle (app level) :

plugins {
alias(libs.plugins.android.application)
alias(libs.plugins.kotlin.android)
alias(libs.plugins.kotlin.compose)
id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1"
}

android {
namespace = "com.jayr.mapboxmap"
compileSdk = 35

    defaultConfig {
        applicationId = "com.jayr.mapboxmap"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
         buildConfigField("String", "MAPBOX_API_KEY", "\"${project.findProperty("MAPBOX_API_KEY")}\"")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}
secrets {
propertiesFileName = "secrets.properties"
defaultPropertiesFileName = "local.properties"
}

dependencies {
implementation(libs.android)
implementation(libs.maps.compose)
implementation(libs.androidx.core.ktx)
implementation(libs.androidx.lifecycle.runtime.ktx)
implementation(libs.androidx.activity.compose)
implementation(platform(libs.androidx.compose.bom))
implementation(libs.androidx.ui)
implementation(libs.androidx.ui.graphics)
implementation(libs.androidx.ui.tooling.preview)
implementation(libs.androidx.material3)
testImplementation(libs.junit)
androidTestImplementation(libs.androidx.junit)
androidTestImplementation(libs.androidx.espresso.core)
androidTestImplementation(platform(libs.androidx.compose.bom))
androidTestImplementation(libs.androidx.ui.test.junit4)
debugImplementation(libs.androidx.ui.tooling)
debugImplementation(libs.androidx.ui.test.manifest)
implementation(libs.secrets.gradle.plugin)
}



Final [libs.versions.toml]:

[versions]
agp = "8.10.1"
android = "11.13.5"
kotlin = "2.0.21"
coreKtx = "1.16.0"
junit = "4.13.2"
junitVersion = "1.2.1"
espressoCore = "3.6.1"
lifecycleRuntimeKtx = "2.9.2"
activityCompose = "1.10.1"
composeBom = "2024.09.00"
mapsCompose = "11.13.5"
secretsGradlePlugin = "2.0.1"

[libraries]
android = { module = "com.mapbox.maps:android", version.ref = "android" }
androidx-core-ktx = { group = "androidx.core", name = "core-ktx", version.ref = "coreKtx" }
junit = { group = "junit", name = "junit", version.ref = "junit" }
androidx-junit = { group = "androidx.test.ext", name = "junit", version.ref = "junitVersion" }
androidx-espresso-core = { group = "androidx.test.espresso", name = "espresso-core", version.ref = "espressoCore" }
androidx-lifecycle-runtime-ktx = { group = "androidx.lifecycle", name = "lifecycle-runtime-ktx", version.ref = "lifecycleRuntimeKtx" }
androidx-activity-compose = { group = "androidx.activity", name = "activity-compose", version.ref = "activityCompose" }
androidx-compose-bom = { group = "androidx.compose", name = "compose-bom", version.ref = "composeBom" }
androidx-ui = { group = "androidx.compose.ui", name = "ui" }
androidx-ui-graphics = { group = "androidx.compose.ui", name = "ui-graphics" }
androidx-ui-tooling = { group = "androidx.compose.ui", name = "ui-tooling" }
androidx-ui-tooling-preview = { group = "androidx.compose.ui", name = "ui-tooling-preview" }
androidx-ui-test-manifest = { group = "androidx.compose.ui", name = "ui-test-manifest" }
androidx-ui-test-junit4 = { group = "androidx.compose.ui", name = "ui-test-junit4" }
androidx-material3 = { group = "androidx.compose.material3", name = "material3" }
maps-compose = { module = "com.mapbox.extension:maps-compose", version.ref = "mapsCompose" }
secrets-gradle-plugin = { module = "com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin", version.ref = "secretsGradlePlugin" }

[plugins]
android-application = { id = "com.android.application", version.ref = "agp" }
kotlin-android = { id = "org.jetbrains.kotlin.android", version.ref = "kotlin" }
kotlin-compose = { id = "org.jetbrains.kotlin.plugin.compose", version.ref = "kotlin" }


There after now run a sync or rather gradle sync the project, this can be achieved via:
This:


Or by clicking on the  located at the top right of your android studio.



How do you now access it?
This can be achieved by importing the BuildConfig, from your package.
In my case my project package is :


com.jayr.mapboxmap

So importing it would be:

com.jayr.mapboxmap.BuildConfig

Please note that it would not always ned to import it it may prompt you.

Now in your MainActivity, you need to now retreive the respective key after the onCreate as such:


var apiKey = BuildConfig.MAPBOX_API_KEY

Summary:

How to save(best practice) a secret key/ token in an android app.


Important ref:
https://docs.mapbox.com/android/maps/guides/install/#work-with-jetpack-compose-extension 




