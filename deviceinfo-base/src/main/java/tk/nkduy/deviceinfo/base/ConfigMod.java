package tk.nkduy.deviceinfo.base;

import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.media.AudioManager.RINGER_MODE_NORMAL;
import static android.media.AudioManager.RINGER_MODE_SILENT;
import static android.media.AudioManager.RINGER_MODE_VIBRATE;

/**
 * Config Mod Class
 */
public class ConfigMod {
  private final Context context;

  /**
   * Instantiates a new config mod.
   *
   * @param context
   *     the context
   */
  public ConfigMod(final Context context) {
    this.context = context;
  }

  /**
   * Checks if the device has sd card
   *
   * @return the boolean
   */
  public final boolean hasSdCard() {
    return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
  }

  /**
   * Is running on emulator boolean.
   *
   * @return the boolean
   */
  public final boolean isRunningOnEmulator() {
    boolean isGenyMotion = Build.MANUFACTURER.contains("Genymotion")
        || Build.PRODUCT.contains("vbox86p")
        || Build.DEVICE.contains("vbox86p")
        || Build.HARDWARE.contains("vbox86");
    boolean isGenericEmulator = Build.BRAND.contains("generic")
        || Build.DEVICE.contains("generic")
        || Build.PRODUCT.contains("sdk")
        || Build.HARDWARE.contains("goldfish");

    return isGenericEmulator || isGenyMotion;
  }

  /**
   * Gets Device Ringer Mode.
   *
   * @return Device Ringer Mode
   */
  @RingerMode
  public final int getDeviceRingerMode() {
    int ringerMode = RingerMode.NORMAL;
    AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    if (audioManager != null) {
      switch (audioManager.getRingerMode()) {
        case RINGER_MODE_NORMAL:
          ringerMode = RingerMode.NORMAL;
          break;
        case RINGER_MODE_SILENT:
          ringerMode = RingerMode.SILENT;
          break;
        case RINGER_MODE_VIBRATE:
          ringerMode = RingerMode.VIBRATE;
          break;
        default:
          //do nothing
          break;
      }
    }

    return ringerMode;
  }

  /**
   * Gets time.
   *
   * @return the time
   */
  public final long getTime() {
    return System.currentTimeMillis();
  }

  /**
   * Gets formatted time.
   *
   * @return the formatted time
   */
  public final String getFormattedTime() {
    DateFormat timeInstance = SimpleDateFormat.getTimeInstance();
    return timeInstance.format(Calendar.getInstance().getTime());
  }

  /**
   * Gets up time.
   *
   * @return the up time
   */
  public final long getUpTime() {
    return SystemClock.uptimeMillis();
  }

  /**
   * Gets formatted up time.
   *
   * @return the formatted up time
   */
  public final String getFormattedUpTime() {
    DateFormat timeInstance = SimpleDateFormat.getTimeInstance();
    return timeInstance.format(SystemClock.uptimeMillis());
  }

  /**
   * Gets date from milliseconds
   *
   * @return the date
   */
  public final Date getCurrentDate() {
    return new Date(System.currentTimeMillis());
  }

  /**
   * Gets formatted date.
   *
   * @return the formatted date
   */
  public final String getFormattedDate() {
    DateFormat dateInstance = SimpleDateFormat.getDateInstance();
    return dateInstance.format(Calendar.getInstance().getTime());
  }
}

