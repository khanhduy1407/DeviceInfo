package tk.nkduy.deviceinfo.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.annotation.RequiresPermission;

/**
 * Bluetooth Mod Class
 */
public class BluetoothMod {
  private final Context context;

  /**
   * Instantiates a new bluetooth mod.
   *
   * @param context
   *     the context
   */
  public BluetoothMod(final Context context) {
    this.context = context;
  }

  /**
   * Gets Bluetooth MAC Address
   *
   * You need to declare the below permission in the manifest file to use this properly
   *
   * <uses-permission android:name="android.permission.BLUETOOTH"/>
   *
   * @return the bluetooth mac
   *
   * @deprecated
   */
  @SuppressLint("HardwareIds")
  @RequiresPermission(Manifest.permission.BLUETOOTH)
  @Deprecated
  public final String getBluetoothMAC() {
    String result = "00:00:00:00:00:00";
    if (PermissionUtil.hasPermission(context, Manifest.permission.BLUETOOTH)) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
          && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
        // Hardware ID are restricted in Android 6+
        // https://developer.android.com/about/versions/marshmallow/android-6.0-changes.html#behavior-hardware-id
        // Getting bluetooth mac via reflection for devices with Android 6+
        result = android.provider.Settings.Secure.getString(context.getContentResolver(),
            "bluetooth_address");
      } else {
        BluetoothAdapter bta = BluetoothAdapter.getDefaultAdapter();
        result = bta != null ? bta.getAddress() : result;
      }
    }
    return CheckValidityUtil.checkValidData(result);
  }

  /**
   * Has Bluetooth LE advertising
   *
   * @return true if the device has Bluetooth LE advertising features
   */
  @RequiresPermission(Manifest.permission.BLUETOOTH)
  public final boolean hasBluetoothLeAdvertising() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
        && hasBluetoothLe()
        && BluetoothAdapter.getDefaultAdapter().isMultipleAdvertisementSupported();
  }

  /**
   * Has Bluetooth LE
   *
   * @return true if the device has a Bluetooth LE compatible chipset
   */
  public final boolean hasBluetoothLe() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2
        && context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
  }
}
