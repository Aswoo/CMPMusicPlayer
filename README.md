# Compose Multiplatform Application

## Before running!
 - check your system with [KDoctor](https://github.com/Kotlin/kdoctor)
 - install JDK 17 or higher on your machine
 - add `local.properties` file to the project root and set a path to Android SDK there

### Android
To run the application on android device/emulator:  
 - open project in Android Studio and run imported android run configuration

To build the application bundle:
 - run `./gradlew :composeApp:assembleDebug`
 - find `.apk` file in `composeApp/build/outputs/apk/debug/composeApp-debug.apk`
Run android UI tests on the connected device: `./gradlew :composeApp:connectedDebugAndroidTest`

### Desktop
Run the desktop application: `./gradlew :composeApp:run`
Run desktop UI tests: `./gradlew :composeApp:jvmTest`

### iOS
To run the application on iPhone device/simulator:
 - Open `iosApp/iosApp.xcproject` in Xcode and run standard configuration
 - Or use [Kotlin Multiplatform Mobile plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile) for Android Studio
Run iOS simulator UI tests: `./gradlew :composeApp:iosSimulatorArm64Test`

### DEMO

<img width="1122" alt="스크린샷 2025-02-03 오후 1 36 02" src="https://github.com/user-attachments/assets/27416a88-5f34-4aa7-b2dc-d2e5d4dd4bd2" />


-----

Libarary

- ktor
- decompose
  - Decompose는 코드를 트리 구조의 라이프사이클 인식 비즈니스 로직 구성 요소(일명 BLoC)로 분해하고 라우팅 기능과 플러그형 UI(Jetpack/Multiplatform Compose, Android Views, SwiftUI, Kotlin/React 등)를 제공하는 Kotlin Multiplatform 라이브러리입니다.
- vlcj
  - Java용 VLC 미디어 플레이어 바인딩 라이브러리
- Coil
- Essenty
  - Kotlin Multiplatform 개발을 위한 유틸리티 라이브러리로,Decompose와 함께 사용하면 상태 관리, 생명주기 관리, 뒤로 가기 처리 등을 쉽게 구현
