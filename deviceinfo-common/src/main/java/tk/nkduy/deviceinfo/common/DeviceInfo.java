package tk.nkduy.deviceinfo.common;

/**
 * Device info class.
 */
public class DeviceInfo {

  /**
   * The Name.
   */
  public static final String nameOfLib = "DeviceInfo";
  /**
   * The constant debuggable.
   */
  public static boolean debuggable = false;
  /**
   * The Not found val.
   */
  public static String notFoundVal = "unknown";

  private DeviceInfo() {

  }

  /**
   * Instantiates a new device info.
   *
   * @param notFoundVal
   *     the not found val
   */
  public static void setNotFoundVal(String notFoundVal) {
    DeviceInfo.notFoundVal = notFoundVal;
  }

  /**
   * Instantiates a new Easy device info.
   *
   * @param notFoundVal
   *     the not found val
   * @param debugFlag
   *     the debug flag
   */
  public static void setConfigs(String notFoundVal, boolean debugFlag) {
    DeviceInfo.notFoundVal = notFoundVal;
    DeviceInfo.debuggable = debugFlag;
  }

  /**
   * Gets library version.
   *
   * @return the library version
   */
  public static String getLibraryVersion() {
    return nameOfLib + " : v1.0 [build-v1]";
  }

  /**
   * Debug.
   */
  public static void debug() {
    DeviceInfo.debuggable = true;
  }
}
