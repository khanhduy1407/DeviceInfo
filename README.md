<h1 align="center">DeviceInfo</h1>

<div align="center">
  <strong>Android library to get device information in a super easy way.</strong>
  <p>The library is built for simplicity and approachability. It not only eliminates most boilerplate code for dealing with device information, but also provides an easy and simple API to retrieve them</p>
</div>
<br/>
<div align="center">
    <a href="https://jitpack.io/#tk.nkduy/DeviceInfo">
        <img src="https://jitpack.io/v/tk.nkduy/DeviceInfo.svg"/>
    </a>
</div>

<br/>
<br/>

# Including in your project
DeviceInfo is available in the Jitpack, so getting it as simple as adding it as a dependency.

```gradle
dependencies {
    def deviceInfoVersion = {latest version}

    // Base + Ads Bundled Library
    implementation "tk.nkduy:deviceinfo:$deviceInfoVersion"

    // Base Composite
    implementation "tk.nkduy:deviceinfo-base:$deviceInfoVersion"

    // Ads Composite
    implementation "tk.nkduy:deviceinfo-ads:$deviceInfoVersion"
}

```

### Simple example

Now to use them, create an instance of one of the Mods ( **Mod** class ), i.e `ConfigMod`
```java
ConfigMod configMod = new ConfigMod(context);
```
Next call an available function on the ***configMod*** instance such as
```java
String time_in_ms= String.valueOf(configMod.getTime());
```

Now each **Mods** has a certain set of functions you can call on them to retrieve device information. i.e for  **ConfigMod**

|Value|functionName|returns
|---|---|---|
|Is running on emulator|`isRunningOnEmulator()`|boolean
|Time (ms)|`getTime()`|long
|Formatted Time (24Hr)|`getFormattedTime()`|String
|Up Time (ms)|`getUpTime()`|long
|Formatted Up Time (24Hr)|`getFormattedUpTime()`|String

#### Android Studio support
+ Include a required permission check

+ Setup all constants returned in a switch statement
  
  This applies to all annotations bundled with deviceinfo.
  + `@RingerMode`
  + `@DeviceType`
  + `@PhoneType`
  + `@OrientationType`
  + `@NetworkType`
  + `@BatteryHealth`
  + `@ChargingVia`
