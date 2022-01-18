package tk.nkduy.deviceinfo.base;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import tk.nkduy.deviceinfo.common.DeviceInfo;

/**
 * App Mod Class
 */
public class AppMod {

  private static final String NAME_NOT_FOUND_EXCEPTION = "Name Not Found Exception";
  private final Context context;

  /**
   * Instantiates a new app mod.
   *
   * @param context
   *     the context
   */
  public AppMod(final Context context) {
    this.context = context;
  }

  /**
   * Gets activity name.
   *
   * @return the activity name
   */
  public final String getActivityName() {
    return CheckValidityUtil.checkValidData(context.getClass().getSimpleName());
  }

  /**
   * Gets package name.
   *
   * @return the package name
   */
  public final String getPackageName() {
    return CheckValidityUtil.checkValidData(context.getPackageName());
  }

  /**
   * Gets store.
   *
   * @return the store
   */
  public final String getStore() {
    String result = context.getPackageManager().getInstallerPackageName(context.getPackageName());
    return CheckValidityUtil.checkValidData(result);
  }

  /**
   * Gets app name.
   *
   * @return the app name
   */
  public final String getAppName() {
    String result;
    final PackageManager pm = context.getPackageManager();
    ApplicationInfo ai = null;
    try {
      ai = pm.getApplicationInfo(context.getPackageName(), 0);
    } catch (PackageManager.NameNotFoundException e) {
      if (DeviceInfo.debuggable) {
        Log.d(DeviceInfo.nameOfLib, NAME_NOT_FOUND_EXCEPTION, e);
      }
    }
    result = ai != null ? (String) pm.getApplicationLabel(ai) : null;
    return CheckValidityUtil.checkValidData(result);
  }

  /**
   * Gets app version.
   *
   * @return the app version
   */
  public final String getAppVersion() {
    String result = null;
    try {
      result = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
    } catch (PackageManager.NameNotFoundException e) {
      if (DeviceInfo.debuggable) {
        Log.e(DeviceInfo.nameOfLib, NAME_NOT_FOUND_EXCEPTION, e);
      }
    }
    return CheckValidityUtil.checkValidData(result);
  }

  /**
   * Gets app version code.
   *
   * @return the app version code
   */
  public final String getAppVersionCode() {
    String result = null;
    try {
      result = String.valueOf(
          context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode);
    } catch (PackageManager.NameNotFoundException e) {
      if (DeviceInfo.debuggable) {
        Log.e(DeviceInfo.nameOfLib, NAME_NOT_FOUND_EXCEPTION, e);
      }
    }
    return CheckValidityUtil.checkValidData(result);
  }

  /**
   * Is permission granted boolean.
   *
   * @param permission
   *     the permission
   *
   * @return the boolean
   */
  public final boolean isPermissionGranted(final String permission) {
    return context.checkCallingPermission(permission) == PackageManager.PERMISSION_GRANTED;
  }

  /**
   * Check if the app with the specified packagename is installed or not
   *
   * @param packageName
   *     the package name
   *
   * @return the boolean
   */
  public final boolean isAppInstalled(String packageName) {
    return context.getPackageManager().getLaunchIntentForPackage(packageName) != null;
  }
}
