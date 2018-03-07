# Redbull-walmart auto tests

## Install and Run Appium 1.6.3

* Install Homebrew

> `/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"`

* Install Node.js

> `brew install node`

* Install Appium

> `npm install -g appium@latest`

* Install Appium Client

> `npm install wd`

* Start Appium (see alternative below)

> `appium &`


## Launch Appium using Appium Desktop App

* Download Appium Desktop App from

> [http://appium.io/](http://appium.io/)

* Open Appium Desktop App

* Provide General Settings (click on settings wheel icon)

> |General|
> |-------|
> |Server Address: 0.0.0.0|
> |Port: 4723|

* Hit Launch button


## Using Appium Inspector to Inspect App

* Open Appium Desktop App
* Tap Start Server button with default settings
* Tap Start Inspector Session button (magnifying glass icon)
* Add Desired Capabilities for iOS/Android
> |For Android|
> |-----|
> |app: \<path to apk\>|
> |package: \<application id\>|
> |launchActivity: \<fully qualified activity name\> |
> |platformName: Android|
> |automationName: Appium|
> |platformVersion: \<type or select appropriate android version\> e.g. 6.0 Marshmallow (API Level 23)|
> |deviceName: \<name of device\> e.g. AndyOSX|

> |For iOS|
> |-----|
> |app: \<path to app file for simulator and ipa for real device\>|
> |platformName: iOS|
> |automationName: XCUITest|
> |platformVersion: \<type or select appropriate iOS version\> e.g. 11.1|
> |deviceName: \<name of device\> e.g. AndyOSX|
> |nativeInstrumentsLib: true|
> |autoAcceptAlerts: true|
> |nativeWebTap: true|
> |webviewConnectRetries: true|

* Tap Start Session button

## Load Tests into IntelliJ IDEA

* Clone Tests

> `git clone git@github.com:DistilleryTech/Redbull-Wallmart.git`

* Download IntelliJ from

> [https://www.jetbrains.com/idea/download/](https://www.jetbrains.com/idea/download/)

* File \> Open \> Expand the Tests folder, then double-click **pom.xml**

* Click on Project tab to show the Project structure

* Import AppiumTestsCheckStyle.xml by IntelliJ IDEA \> Preferences \> Editor \> Code Style \> Java \> Scheme \> Manage \> Import

* Install flick
> gem install flick

* Install allure
> brew install allure

## Package Test Content
###for Android
* Install Android SDK

> [https://developer.android.com/studio/install.html](https://developer.android.com/studio/install.html)

* Add Android SDK platform-tools, tools, and build-tools to path

> ```
> cd $HOME
> vim .bash_profile
> export ANDROID_HOME=$HOME/Library/Android/sdk
> export PATH=$PATH:$ANDROID_HOME/platform-tools:$ANDROID_HOME/tools:$ANDROID_HOME/build-tools/24.0.1`
> source .bash_profile
> ```

###for iOS
* Install Xcode command tools
> xcode-select --install
> xcode-select --reset
> xcode-select --switch /Applications/Xcode.app

* Install Maven

  * Check Java is installed (note that Java 9 is not supported)

  > `java -version`

  Should return something like:
  > ```
  > java version "1.8.0_151"
  > Java(TM) SE Runtime Environment (build 1.8.0_151-b12)
  > Java HotSpot(TM) 64-Bit Server VM (build 25.151-b12, mixed mode)
  > ```

  * Check $JAVA_HOME variable is set

  > `echo $JAVA_HOME`

  Should return this or similar:
  > `/Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home`

  * Install Java if any of previous steps failed

  > `brew cask install caskroom/versions/java8`

  * Run Maven installation

  > `brew install maven`

## Run Tests Locally

* Get .app file for simulator and .ipa for real device

* Run simulator or connected real device for Mac

* Build or run project
> |------|
> build
> mvn clean package -DskipTests -DapplicationFilePath=/path/to/file/RedBull-Wallmart.app -Ddevice=device_udid -DdeviceName=name_of_device
> run with all tests
> mvn clean package surefire:test -DapplicationFilePath=/path/to/file/RedBull-Wallmart.app -Ddevice=device_udid -DdeviceName=name_of_device
> |------|
> Additional params
> appiumHost default 0.0.0.0
> appiumPort default 4723
> appiumBootstrapPort default 4724
> screenRecording default true
> testingType default frontend (backend for API)
> platformName default iOS 
> platformVersion default 11
> automationName default XCUITest
> simulator default true (false for real device)
> server
> serverPort
> serverAPiBaseUrl

* Run test through IntelliJ or through command line replacing \<test to run\>

> ```
> mvn test -Dtest=<test to run>
> ```

e.g.

> ```
> mvn test -Dtest=SplashKeTest
> mvn test -Dtest=*KeTest
> mvn test -Dtest=CloseDialogTest,Login*Test,SignUp*Test
> ```

* Generate local report
> allure generate /path/to/tests/result/folder/allure-results e.g. /Users/distillery/work/projects/Redbull-Walmart/allure-results

