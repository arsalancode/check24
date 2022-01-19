# Check24 products

## App details:
App to show products

## Tech Stack
1. kotlin, kotlin-coroutines
1. MVVM
1. Multimodular Architecture
1. Hilt
1. LiveData
1. Navigation Components
1. Custom Fonts
1. Lotti for animations
1. Retrofit   
1. Test coverage using Unit & Instrumentation tests


## Architecture

- Backend APIs are called using kotlin-coroutines (flow, suspend)
- Retrofit is used for fetching data and parsing API responses
- UI is updated using Livedata and Data-binding


#### Multi modular project structure
This project aims for showcasing a multi modular architecture based on 2 concepts: Libraries and Features

- **Features** contains all user facing functionality (e.g. splash, home)
- **Libraries** are shared modules that help the building of features (e.g. base, core, navigation, networking, resources)

#### Navigation
- **Between Activities**: To launch activity from different feature, action name is used in intent. 
- **Between Fragments**: Navigation graph is created to move between fragments.  

## To run this project:
You'll need minimum `android studio arctic fox 2020.3.1`

# References
1. [Kotlin + buildSrc](https://handstandsam.com/2018/02/11/kotlin-buildsrc-for-better-gradle-dependency-management/)
1. [Lotti-android](https://github.com/airbnb/lottie-android)
1. [Lotti-files](https://lottiefiles.com)
   
