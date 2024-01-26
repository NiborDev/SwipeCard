# SimpleSwipeCard Library for Android

## Gradle Integration

### Step 1: Add JitPack to your build file

Add the following code to your root `build.gradle` file:

```
gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

### Step 2: Add the dependency

Add the following dependency to your app module's build.gradle file, replacing 'Tag' with the desired release or commit tag:

```
dependencies {
    implementation 'com.github.NiborDev:SimpleSwipeCard:Tag'
}
```
