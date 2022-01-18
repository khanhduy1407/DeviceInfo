package tk.nkduy.deviceinfo.base;

import android.os.Build;
import tk.nkduy.deviceinfo.common.DeviceInfo;

/**
 * Cpu Mod Class
 */
public class CpuMod {
  /**
   * Get supported abis string [ ].
   *
   * @return the string [ ]
   */
  public final String[] getSupportedABIS() {
    String[] result = new String[] { DeviceInfo.notFoundVal };
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      result = Build.SUPPORTED_ABIS;
    }
    return CheckValidityUtil.checkValidData(result);
  }

  /**
   * Gets string supported abis.
   *
   * @return the string supported abis
   */
  public final String getStringSupportedABIS() {
    String result = null;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      String[] supportedABIS = Build.SUPPORTED_ABIS;
      StringBuilder supportedABIString = new StringBuilder();
      if (supportedABIS.length > 0) {
        for (String abis : supportedABIS) {
          supportedABIString.append(abis).append("_");
        }
        supportedABIString.deleteCharAt(supportedABIString.lastIndexOf("_"));
      } else {
        supportedABIString.append("");
      }
      result = supportedABIString.toString();
    }
    return CheckValidityUtil.checkValidData(
        CheckValidityUtil.handleIllegalCharacterInResult(result));
  }

  /**
   * Gets string supported 32 bit abis.
   *
   * @return the string supported 32 bit abis
   */
  public final String getStringSupported32bitABIS() {
    String result = null;

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      String[] supportedABIS = Build.SUPPORTED_32_BIT_ABIS;

      StringBuilder supportedABIString = new StringBuilder();
      if (supportedABIS.length > 0) {
        for (String abis : supportedABIS) {
          supportedABIString.append(abis).append("_");
        }
        supportedABIString.deleteCharAt(supportedABIString.lastIndexOf("_"));
      } else {
        supportedABIString.append("");
      }

      result = supportedABIString.toString();
    }

    return CheckValidityUtil.checkValidData(
        CheckValidityUtil.handleIllegalCharacterInResult(result));
  }

  /**
   * Gets string supported 64 bit abis.
   *
   * @return the string supported 64 bit abis
   */
  public final String getStringSupported64bitABIS() {
    String result = null;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      String[] supportedABIS = Build.SUPPORTED_64_BIT_ABIS;

      StringBuilder supportedABIString = new StringBuilder();
      if (supportedABIS.length > 0) {
        for (String abis : supportedABIS) {
          supportedABIString.append(abis).append("_");
        }
        supportedABIString.deleteCharAt(supportedABIString.lastIndexOf("_"));
      } else {
        supportedABIString.append("");
      }
      result = supportedABIString.toString();
    }
    return CheckValidityUtil.checkValidData(
        CheckValidityUtil.handleIllegalCharacterInResult(result));
  }

  /**
   * Get supported 32 bit abis string [ ].
   *
   * @return the string [ ]
   */
  public final String[] getSupported32bitABIS() {
    String[] result = new String[] { DeviceInfo.notFoundVal };
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      result = Build.SUPPORTED_32_BIT_ABIS;
    }
    return CheckValidityUtil.checkValidData(result);
  }

  /**
   * Get supported 64 bit abis string [ ].
   *
   * @return the string [ ]
   */
  public final String[] getSupported64bitABIS() {
    String[] result = new String[] { DeviceInfo.notFoundVal };
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      result = Build.SUPPORTED_64_BIT_ABIS;
    }
    return CheckValidityUtil.checkValidData(result);
  }
}
