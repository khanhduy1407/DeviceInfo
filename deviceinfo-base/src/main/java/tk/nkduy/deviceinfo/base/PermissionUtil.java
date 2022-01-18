package tk.nkduy.deviceinfo.base;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import tk.nkduy.deviceinfo.common.DeviceInfo;

/**
 * Permission Util Class
 */
final class PermissionUtil {
  private PermissionUtil() {
    // private constructor for utility class
  }

  /**
   * Has permission method.
   *
   * @param context
   *     the context
   * @param permission
   *     the permission
   *
   * @return the boolean
   */
  static boolean hasPermission(final Context context, final String permission) {
    boolean permGranted =
        context.checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    if (DeviceInfo.debuggable && !permGranted) {
      Log.e(DeviceInfo.nameOfLib, ">\t" + permission);
      Log.w(DeviceInfo.nameOfLib,
          "\nPermission not granted/missing!\nMake sure you have declared the permission in your manifest file (and granted it on API 23+).\n");
    }
    return permGranted;
  }
}
