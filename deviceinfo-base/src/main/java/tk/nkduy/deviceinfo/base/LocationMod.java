package tk.nkduy.deviceinfo.base;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import androidx.annotation.RequiresPermission;

/**
 * Location Mod Class
 *
 * You need to declare the below permission in the manifest file to use this properly
 *
 * For Network based location
 * <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
 *
 * For more accurate location updates via GPS and network both
 * <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
 */

public class LocationMod {
  private final boolean hasFineLocationPermission;
  private final boolean hasCoarseLocationPermission;
  private LocationManager lm;

  /**
   * Instantiates a new location mod.
   *
   * @param context
   *     the context
   */
  public LocationMod(Context context) {
    hasFineLocationPermission =
        PermissionUtil.hasPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
    hasCoarseLocationPermission =
        PermissionUtil.hasPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);

    if (hasCoarseLocationPermission || hasFineLocationPermission) {
      lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }
  }

  /**
   * Get lat long double [ ].
   *
   * @return the double [ ]
   */
  @RequiresPermission(anyOf = {
      Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
  })
  public final double[] getLatLong() {
    double[] gps = new double[2];
    gps[0] = 0;
    gps[1] = 0;

    if (hasCoarseLocationPermission && lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
      Location lastKnownLocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
      if (lastKnownLocation != null) {
        gps[0] = lastKnownLocation.getLatitude();
        gps[1] = lastKnownLocation.getLongitude();
      }
    } else if (hasFineLocationPermission) {
      boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
      boolean isNetworkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

      Location lastKnownLocationNetwork = null;
      Location lastKnownLocationGps = null;
      Location betterLastKnownLocation = null;

      if (isNetworkEnabled) {
        lastKnownLocationNetwork = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
      }

      if (isGPSEnabled) {
        lastKnownLocationGps = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
      }
      if (lastKnownLocationGps != null && lastKnownLocationNetwork != null) {
        betterLastKnownLocation = getBetterLocation(lastKnownLocationGps, lastKnownLocationNetwork);
      }

      if (betterLastKnownLocation == null) {
        betterLastKnownLocation = lm.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
      }

      if (betterLastKnownLocation != null) {
        gps[0] = betterLastKnownLocation.getLatitude();
        gps[1] = betterLastKnownLocation.getLongitude();
      }
    }
    return gps;
  }

  private Location getBetterLocation(final Location location1, final Location location2) {
    if (location1.getAccuracy() >= location2.getAccuracy()) {
      return location1;
    } else {
      return location2;
    }
  }
}
