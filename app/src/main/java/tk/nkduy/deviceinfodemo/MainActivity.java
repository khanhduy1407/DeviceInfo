package tk.nkduy.deviceinfodemo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.hardware.Sensor;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArrayMap;
import tk.nkduy.deviceinfo.ads.AdsMod;
import tk.nkduy.deviceinfo.base.BatteryHealth;
import tk.nkduy.deviceinfo.base.ChargingVia;
import tk.nkduy.deviceinfo.base.DeviceType;
import tk.nkduy.deviceinfo.base.AppMod;
import tk.nkduy.deviceinfo.base.BatteryMod;
import tk.nkduy.deviceinfo.base.BluetoothMod;
import tk.nkduy.deviceinfo.base.ConfigMod;
import tk.nkduy.deviceinfo.base.CpuMod;
import tk.nkduy.deviceinfo.base.DeviceMod;
import tk.nkduy.deviceinfo.base.DisplayMod;
import tk.nkduy.deviceinfo.base.FingerprintMod;
import tk.nkduy.deviceinfo.base.IdMod;
import tk.nkduy.deviceinfo.base.LocationMod;
import tk.nkduy.deviceinfo.base.MemoryMod;
import tk.nkduy.deviceinfo.base.NetworkMod;
import tk.nkduy.deviceinfo.base.NfcMod;
import tk.nkduy.deviceinfo.base.SensorMod;
import tk.nkduy.deviceinfo.base.SimMod;
import tk.nkduy.deviceinfo.base.NetworkType;
import tk.nkduy.deviceinfo.base.OrientationType;
import tk.nkduy.deviceinfo.base.PhoneType;
import tk.nkduy.deviceinfo.base.RingerMode;
import tk.nkduy.deviceinfo.common.DeviceInfo;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private ArrayAdapter<String> adapter;

  @SuppressLint("MissingPermission")
  @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //Data Array List of Info Object
    final ArrayList<String> data = new ArrayList<>();

    //Add Data
    ArrayMap<String, String> deviceDataMap = new ArrayMap<>();

    // Setup the value to be returned when result is either not found or invalid/null
    DeviceInfo.setNotFoundVal("na");
    // Enable Debugging when in Debug build
    if (BuildConfig.DEBUG) {
      DeviceInfo.debug();
    }

    // Library Info
    data.add(DeviceInfo.getLibraryVersion());

    // ID Mod
    IdMod idMod = new IdMod(this);

    String[] emailIds = idMod.getAccounts();
    StringBuilder emailString = new StringBuilder();
    if (emailIds != null && emailIds.length > 0) {
      for (String e : emailIds) {
        emailString.append(e).append("\n");
      }
    } else {
      emailString.append("-");
    }

    AdsMod adsMod = new AdsMod(this);
    adsMod.getAndroidAdId(new AdsMod.AdIdentifierCallback() {
      @Override
      public void onSuccess(String adIdentifier, boolean adDonotTrack) {
        // Add Data
        data.add("Android Advertiser ID :" + adIdentifier);
        data.add("Ad Do not Track :" + adDonotTrack);
        adapter.notifyDataSetChanged();
      }
    });

    // Config Mod
    ConfigMod configMod = new ConfigMod(this);
    deviceDataMap.put("Time (ms)", String.valueOf(configMod.getTime()));
    deviceDataMap.put("Formatted Time (24Hrs)", configMod.getFormattedTime());
    deviceDataMap.put("UpTime (ms)", String.valueOf(configMod.getUpTime()));
    deviceDataMap.put("Formatted Up Time (24Hrs)", configMod.getFormattedUpTime());
    deviceDataMap.put("Date", String.valueOf(configMod.getCurrentDate()));
    deviceDataMap.put("Formatted Date", configMod.getFormattedDate());
    deviceDataMap.put("SD Card available", String.valueOf(configMod.hasSdCard()));
    deviceDataMap.put("Running on emulator", String.valueOf(configMod.isRunningOnEmulator()));

    @RingerMode int ringermode = configMod.getDeviceRingerMode();
    switch (ringermode) {
      case RingerMode.NORMAL:
        deviceDataMap.put(getString(R.string.ringer_mode), "normal");
        break;
      case RingerMode.VIBRATE:
        deviceDataMap.put(getString(R.string.ringer_mode), "vibrate");
        break;
      case RingerMode.SILENT:
      default:
        deviceDataMap.put(getString(R.string.ringer_mode), "silent");
        break;
    }

    // Fingerprint Mod
    FingerprintMod fingerprintMod = new FingerprintMod(this);
    deviceDataMap.put("Is Fingerprint Sensor present?",
        String.valueOf(fingerprintMod.isFingerprintSensorPresent()));
    deviceDataMap.put("Are fingerprints enrolled",
        String.valueOf(fingerprintMod.areFingerprintsEnrolled()));

    // Sensor Mod
    SensorMod sensorMod = new SensorMod(this);
    List<Sensor> list = sensorMod.getAllSensors();
    for (Sensor s : list) {
      if (s != null) {
        String stringBuilder = "\nVendor : "
            + s.getVendor()
            + "\n"
            + "Version : "
            + s.getVersion()
            + "\n"
            + "Power : "
            + s.getPower()
            + "\n"
            + "Resolution : "
            + s.getResolution()
            + "\n"
            + "Max Range : "
            + s.getMaximumRange();
        deviceDataMap.put("Sensor Name - " + s.getName(), stringBuilder);
      } else {
        deviceDataMap.put("Sensor", "N/A");
      }
    }

    // SIM Mod
    SimMod simMod = new SimMod(this);
    deviceDataMap.put("IMSI", simMod.getIMSI());
    deviceDataMap.put("SIM Serial Number", simMod.getSIMSerial());
    deviceDataMap.put("Country", simMod.getCountry());
    deviceDataMap.put("Carrier", simMod.getCarrier());
    deviceDataMap.put("SIM Network Locked", String.valueOf(simMod.isSimNetworkLocked()));
    deviceDataMap.put("Is Multi SIM", String.valueOf(simMod.isMultiSim()));
    deviceDataMap.put("Number of active SIM", String.valueOf(simMod.getNumberOfActiveSim()));

    if (simMod.isMultiSim()) {
      List<SubscriptionInfo> activeMultiSimInfo = simMod.getActiveMultiSimInfo();
      if (activeMultiSimInfo != null) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < activeMultiSimInfo.size(); i++) {
          stringBuilder.append("\nSIM ")
              .append(i)
              .append(" Info")
              .append("\nPhone Number :")
              .append(activeMultiSimInfo.get(i).getNumber())
              .append("\n")
              .append("Carrier Name :")
              .append(activeMultiSimInfo.get(i).getCarrierName())
              .append("\n")
              .append("Country :")
              .append(activeMultiSimInfo.get(i).getCountryIso())
              .append("\n")
              .append("Roaming :")
              .append(activeMultiSimInfo.get(i).getDataRoaming()
                  == SubscriptionManager.DATA_ROAMING_ENABLE)
              .append("\n")
              .append("Display Name :")
              .append(activeMultiSimInfo.get(i).getDisplayName())
              .append("\n")
              .append("Sim Slot  :")
              .append(activeMultiSimInfo.get(i).getSimSlotIndex())
              .append("\n");
        }
        deviceDataMap.put("Multi SIM Info", stringBuilder.toString());
      }
    }

    // Device Mod
    DeviceMod deviceMod = new DeviceMod(this);
    deviceDataMap.put("Language", deviceMod.getLanguage());
    deviceDataMap.put("Android ID", idMod.getAndroidID());
    deviceDataMap.put("IMEI", deviceMod.getIMEI());
    deviceDataMap.put("User-Agent", idMod.getUA());
    deviceDataMap.put("GSF ID", idMod.getGSFID());
    deviceDataMap.put("Pseudo ID", idMod.getPseudoUniqueID());
    deviceDataMap.put("Device Serial", deviceMod.getSerial());
    deviceDataMap.put("Manufacturer", deviceMod.getManufacturer());
    deviceDataMap.put("Model", deviceMod.getModel());
    deviceDataMap.put("OS Codename", deviceMod.getOSCodename());
    deviceDataMap.put("OS Version", deviceMod.getOSVersion());
    deviceDataMap.put("Display Version", deviceMod.getDisplayVersion());
    deviceDataMap.put("Phone Number", deviceMod.getPhoneNo());
    deviceDataMap.put("Radio Version", deviceMod.getRadioVer());
    deviceDataMap.put("Product ", deviceMod.getProduct());
    deviceDataMap.put("Device", deviceMod.getDevice());
    deviceDataMap.put("Board", deviceMod.getBoard());
    deviceDataMap.put("Hardware", deviceMod.getHardware());
    deviceDataMap.put("BootLoader", deviceMod.getBootloader());
    deviceDataMap.put("Device Rooted", String.valueOf(deviceMod.isDeviceRooted()));
    deviceDataMap.put("Fingerprint", deviceMod.getFingerprint());
    deviceDataMap.put("Build Brand", deviceMod.getBuildBrand());
    deviceDataMap.put("Build Host", deviceMod.getBuildHost());
    deviceDataMap.put("Build Tag", deviceMod.getBuildTags());
    deviceDataMap.put("Build Time", String.valueOf(deviceMod.getBuildTime()));
    deviceDataMap.put("Build User", deviceMod.getBuildUser());
    deviceDataMap.put("Build Version Release", deviceMod.getBuildVersionRelease());
    deviceDataMap.put("Screen Display ID", deviceMod.getScreenDisplayID());
    deviceDataMap.put("Build Version Codename", deviceMod.getBuildVersionCodename());
    deviceDataMap.put("Build Version Increment", deviceMod.getBuildVersionIncremental());
    deviceDataMap.put("Build Version SDK", String.valueOf(deviceMod.getBuildVersionSDK()));
    deviceDataMap.put("Build ID", deviceMod.getBuildID());

    @DeviceType int deviceType = deviceMod.getDeviceType(this);
    switch (deviceType) {
      case DeviceType.WATCH:
        deviceDataMap.put(getString(R.string.device_type), "watch");
        break;
      case DeviceType.PHONE:
        deviceDataMap.put(getString(R.string.device_type), "phone");
        break;
      case DeviceType.PHABLET:
        deviceDataMap.put(getString(R.string.device_type), "phablet");
        break;
      case DeviceType.TABLET:
        deviceDataMap.put(getString(R.string.device_type), "tablet");
        break;
      case DeviceType.TV:
        deviceDataMap.put(getString(R.string.device_type), "tv");
        break;
      default:
        //do nothing
        break;
    }

    @PhoneType int phoneType = deviceMod.getPhoneType();
    switch (phoneType) {

      case PhoneType.CDMA:
        deviceDataMap.put(getString(R.string.phone_type), "CDMA");
        break;
      case PhoneType.GSM:
        deviceDataMap.put(getString(R.string.phone_type), "GSM");
        break;
      case PhoneType.NONE:
        deviceDataMap.put(getString(R.string.phone_type), "None");
        break;
      default:
        deviceDataMap.put(getString(R.string.phone_type), "Unknown");
        break;
    }

    @OrientationType int orientationType = deviceMod.getOrientation(this);
    switch (orientationType) {
      case OrientationType.LANDSCAPE:
        deviceDataMap.put(getString(R.string.orientation), "Landscape");
        break;
      case OrientationType.PORTRAIT:
        deviceDataMap.put(getString(R.string.orientation), "Portrait");
        break;
      case OrientationType.UNKNOWN:
      default:
        deviceDataMap.put(getString(R.string.orientation), "Unknown");
        break;
    }

    // App Mod
    AppMod appMod = new AppMod(this);
    deviceDataMap.put("Installer Store", appMod.getStore());
    deviceDataMap.put("App Name", appMod.getAppName());
    deviceDataMap.put("Package Name", appMod.getPackageName());
    deviceDataMap.put("Activity Name", appMod.getActivityName());
    deviceDataMap.put("App version", appMod.getAppVersion());
    deviceDataMap.put("App versioncode", appMod.getAppVersionCode());
    deviceDataMap.put("Does app have Camera permission?",
        String.valueOf(appMod.isPermissionGranted(Manifest.permission.CAMERA)));

    //Network Mod
    NetworkMod networkMod = new NetworkMod(this);
    deviceDataMap.put("WIFI MAC Address", networkMod.getWifiMAC());
    deviceDataMap.put("WIFI LinkSpeed", networkMod.getWifiLinkSpeed());
    deviceDataMap.put("WIFI SSID", networkMod.getWifiSSID());
    deviceDataMap.put("WIFI BSSID", networkMod.getWifiBSSID());
    deviceDataMap.put("IPv4 Address", networkMod.getIPv4Address());
    deviceDataMap.put("IPv6 Address", networkMod.getIPv6Address());
    deviceDataMap.put("Network Available", String.valueOf(networkMod.isNetworkAvailable()));
    deviceDataMap.put("Wi-Fi enabled", String.valueOf(networkMod.isWifiEnabled()));

    @NetworkType int networkType = networkMod.getNetworkType();

    switch (networkType) {
      case NetworkType.CELLULAR_UNKNOWN:
        deviceDataMap.put(getString(R.string.network_type), "Cellular Unknown");
        break;
      case NetworkType.CELLULAR_UNIDENTIFIED_GEN:
        deviceDataMap.put(getString(R.string.network_type), "Cellular Unidentified Generation");
        break;
      case NetworkType.CELLULAR_2G:
        deviceDataMap.put(getString(R.string.network_type), "Cellular 2G");
        break;
      case NetworkType.CELLULAR_3G:
        deviceDataMap.put(getString(R.string.network_type), "Cellular 3G");
        break;
      case NetworkType.CELLULAR_4G:
        deviceDataMap.put(getString(R.string.network_type), "Cellular 4G");
        break;

      case NetworkType.WIFI_WIFIMAX:
        deviceDataMap.put(getString(R.string.network_type), "Wifi/WifiMax");
        break;
      case NetworkType.UNKNOWN:
      default:
        deviceDataMap.put(getString(R.string.network_type), "Unknown");
        break;
    }

    // Battery Mod
    BatteryMod batteryMod = new BatteryMod(this);
    deviceDataMap.put("Battery Percentage",
        String.valueOf(batteryMod.getBatteryPercentage()) + "%");
    deviceDataMap.put("Is device charging", String.valueOf(batteryMod.isDeviceCharging()));
    deviceDataMap.put("Battery present", String.valueOf(batteryMod.isBatteryPresent()));
    deviceDataMap.put("Battery technology", String.valueOf(batteryMod.getBatteryTechnology()));
    deviceDataMap.put("Battery temperature",
        String.valueOf(batteryMod.getBatteryTemperature()) + " deg C");
    deviceDataMap.put("Battery voltage",
        String.valueOf(batteryMod.getBatteryVoltage()) + " mV");

    @BatteryHealth int batteryHealth = batteryMod.getBatteryHealth();
    switch (batteryHealth) {
      case BatteryHealth.GOOD:
        deviceDataMap.put("Battery health", "Good");
        break;
      case BatteryHealth.HAVING_ISSUES:
      default:
        deviceDataMap.put("Battery health", "Having issues");
        break;
    }

    @ChargingVia int isChargingVia = batteryMod.getChargingSource();
    switch (isChargingVia) {
      case ChargingVia.AC:
        deviceDataMap.put(getString(R.string.device_charging_via), "AC");
        break;
      case ChargingVia.USB:
        deviceDataMap.put(getString(R.string.device_charging_via), "USB");
        break;
      case ChargingVia.WIRELESS:
        deviceDataMap.put(getString(R.string.device_charging_via), "Wireless");
        break;
      case ChargingVia.UNKNOWN_SOURCE:
      default:
        deviceDataMap.put(getString(R.string.device_charging_via), "Unknown Source");
        break;
    }

    //Bluetooth Mod
    BluetoothMod bluetoothMod = new BluetoothMod(this);
    deviceDataMap.put("BT MAC Address", bluetoothMod.getBluetoothMAC());

    // Display Mod
    DisplayMod displayMod = new DisplayMod(this);
    deviceDataMap.put("Display Resolution", displayMod.getResolution());
    deviceDataMap.put("Screen Density", displayMod.getDensity());
    deviceDataMap.put("Screen Size", String.valueOf(displayMod.getPhysicalSize()));
    deviceDataMap.put("Screen Refresh rate",
        String.valueOf(displayMod.getRefreshRate()) + " Hz");

    deviceDataMap.put("Email ID", emailString.toString());

    // Location Mod
    LocationMod locationMod = new LocationMod(this);
    double[] l = locationMod.getLatLong();
    String lat = String.valueOf(l[0]);
    String lon = String.valueOf(l[1]);
    deviceDataMap.put("Latitude", lat);
    deviceDataMap.put("Longitude", lon);

    // Memory Mod
    MemoryMod memoryMod = new MemoryMod(this);
    deviceDataMap.put("Total RAM",
        String.valueOf(memoryMod.convertToGb(memoryMod.getTotalRAM())) + " Gb");
    deviceDataMap.put("Available Internal Memory",
        String.valueOf(memoryMod.convertToGb(memoryMod.getAvailableInternalMemorySize()))
            + " Gb");
    deviceDataMap.put("Available External Memory",
        String.valueOf(memoryMod.convertToGb(memoryMod.getAvailableExternalMemorySize()))
            + " Gb");
    deviceDataMap.put("Total Internal Memory",
        String.valueOf(memoryMod.convertToGb(memoryMod.getTotalInternalMemorySize()))
            + " Gb");
    deviceDataMap.put("Total External memory",
        String.valueOf(memoryMod.convertToGb(memoryMod.getTotalExternalMemorySize()))
            + " Gb");

    // CPU Mod
    CpuMod cpuMod = new CpuMod();
    deviceDataMap.put("Supported ABIS", cpuMod.getStringSupportedABIS());
    deviceDataMap.put("Supported 32 bit ABIS", cpuMod.getStringSupported32bitABIS());
    deviceDataMap.put("Supported 64 bit ABIS", cpuMod.getStringSupported64bitABIS());

    // NFC Mod
    NfcMod nfcMod = new NfcMod(this);
    deviceDataMap.put("is NFC present", String.valueOf(nfcMod.isNfcPresent()));
    deviceDataMap.put("is NFC enabled", String.valueOf(nfcMod.isNfcEnabled()));

    for (String key : deviceDataMap.keySet()) {
      data.add(key + " : " + deviceDataMap.get(key));
    }

    ListView lv = findViewById(R.id.listview);
    adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
    lv.setAdapter(adapter);
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    finish();
  }
}
