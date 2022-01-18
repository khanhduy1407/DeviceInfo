package tk.nkduy.deviceinfo.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import androidx.annotation.RequiresPermission;

/**
 * The type fingerprint mod.
 */
public class FingerprintMod {

  private FingerprintManager fingerprintManager = null;

  /**
   * Instantiates a new fingerprint mod.
   *
   * You need to declare the below permission in the manifest file to use this properly
   *
   * <uses-permission android:name="android.permission.USE_FINGERPRINT" />
   *
   * @param context
   *     the context
   */
  @TargetApi(23)
  @RequiresPermission(Manifest.permission.USE_FINGERPRINT)
  public FingerprintMod(final Context context) {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
      fingerprintManager =
          (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
    }
  }

  /**
   * Is fingerprint sensor present boolean.
   *
   * You need to declare the below permission in the manifest file to use this properly
   *
   * <uses-permission android:name="android.permission.USE_FINGERPRINT" />
   *
   * @return the boolean
   */
  @SuppressLint("NewApi")
  @RequiresPermission(Manifest.permission.USE_FINGERPRINT)
  public final boolean isFingerprintSensorPresent() {
    return fingerprintManager != null && fingerprintManager.isHardwareDetected();
  }

  /**
   * Are fingerprints enrolled boolean.
   *
   * You need to declare the below permission in the manifest file to use this properly
   *
   * <uses-permission android:name="android.permission.USE_FINGERPRINT" />
   *
   * @return the boolean
   */
  @SuppressLint("NewApi")
  @RequiresPermission(Manifest.permission.USE_FINGERPRINT)
  public final boolean areFingerprintsEnrolled() {
    return fingerprintManager != null && fingerprintManager.hasEnrolledFingerprints();
  }
}
