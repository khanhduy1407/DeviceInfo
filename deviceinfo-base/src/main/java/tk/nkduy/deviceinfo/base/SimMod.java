package tk.nkduy.deviceinfo.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresPermission;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import tk.nkduy.deviceinfo.common.DeviceInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Sim Mod Class
 */
public class SimMod {
  private final TelephonyManager tm;
  private final Context context;

  /**
   * Instantiates a new sim mod.
   *
   * @param context
   *     the context
   */
  public SimMod(final Context context) {
    this.context = context;
    tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
  }

  /**
   * Gets country.
   *
   * @return the country
   */
  public final String getCountry() {
    String result;
    if (tm != null && tm.getSimState() == TelephonyManager.SIM_STATE_READY) {
      result = tm.getSimCountryIso().toLowerCase(Locale.getDefault());
    } else {
      Locale locale = Locale.getDefault();
      result = locale.getCountry().toLowerCase(locale);
    }
    return CheckValidityUtil.checkValidData(
        CheckValidityUtil.handleIllegalCharacterInResult(result));
  }

  /**
   * Is sim network locked.
   *
   * @return the boolean
   */
  public final boolean isSimNetworkLocked() {
    return tm != null && tm.getSimState() == TelephonyManager.SIM_STATE_NETWORK_LOCKED;
  }

  /**
   * Gets carrier.
   *
   * @return the carrier
   */
  public final String getCarrier() {
    String result = null;
    if (tm != null && tm.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA) {
      result = tm.getNetworkOperatorName().toLowerCase(Locale.getDefault());
    }
    return CheckValidityUtil.checkValidData(
        CheckValidityUtil.handleIllegalCharacterInResult(result));
  }

  /**
   * Gets imsi.
   *
   * You need to declare the below permission in the manifest file to use this properly
   *
   * <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
   *
   * @return the imsi
   */
  @SuppressLint("HardwareIds")
  @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
  public final String getIMSI() {
    String result = null;
    if (tm != null && PermissionUtil.hasPermission(context, Manifest.permission.READ_PHONE_STATE)) {
      result = tm.getSubscriberId();
    }

    return CheckValidityUtil.checkValidData(result);
  }

  /**
   * Gets SIM serial number.
   *
   * You need to declare the below permission in the manifest file to use this properly
   *
   * <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
   *
   * @return the sim serial
   */
  @SuppressLint("HardwareIds")
  @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
  public final String getSIMSerial() {
    String result = null;
    if (tm != null && PermissionUtil.hasPermission(context, Manifest.permission.READ_PHONE_STATE)) {
      result = tm.getSimSerialNumber();
    }
    return CheckValidityUtil.checkValidData(result);
  }

  /**
   * Is multi sim.
   *
   * You need to declare the below permission in the manifest file to use this properly
   *
   * <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
   *
   * @return the boolean
   */
  @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
  public final boolean isMultiSim() {
    return getActiveMultiSimInfo().size() > 1;
  }

  /**
   * Gets active multi sim info.
   *
   * You need to declare the below permission in the manifest file to use this properly
   *
   * <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
   *
   * @return the active multi sim info
   */
  @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
  public final List<SubscriptionInfo> getActiveMultiSimInfo() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1 && PermissionUtil.hasPermission(
        context, Manifest.permission.READ_PHONE_STATE)) {
      List<SubscriptionInfo> tempActiveSub =
          SubscriptionManager.from(context).getActiveSubscriptionInfoList();
      if (tempActiveSub == null || tempActiveSub.isEmpty()) {
        return new ArrayList<>(0);
      } else {
        return tempActiveSub;
      }
    } else {
      if (DeviceInfo.debuggable) {
        Log.w(DeviceInfo.nameOfLib,
            "Device is running on android version that does not support multi sim functionality!");
      }
    }
    return new ArrayList<>(0);
  }

  /**
   * Gets number of active sim.
   *
   * You need to declare the below permission in the manifest file to use this properly
   *
   * <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
   *
   * @return the number of active sim
   */
  @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
  public final int getNumberOfActiveSim() {
    return getActiveMultiSimInfo().size();
  }
}
