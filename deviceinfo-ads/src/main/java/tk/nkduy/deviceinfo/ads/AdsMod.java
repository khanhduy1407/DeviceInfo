package tk.nkduy.deviceinfo.ads;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import tk.nkduy.deviceinfo.common.DeviceInfo;
import java.io.IOException;

/**
 * The type ads mod.
 */
public class AdsMod {

  private final Context context;

  /**
   * Instantiates a new ads mod.
   *
   * @param context
   *     the context
   */
  public AdsMod(Context context) {
    this.context = context;
  }

  /**
   * Gets android ad id.
   *
   * @param callback
   *     the callback
   */
  public final void getAndroidAdId(final AdIdentifierCallback callback) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        retrieveAdId(callback);
      }
    }).start();
  }

  private void retrieveAdId(final AdIdentifierCallback callback) {
    AdvertisingIdClient.Info adInfo;
    try {
      adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context);
      String androidAdId = DeviceInfo.notFoundVal;
      boolean adDoNotTrack = false;
      if (adInfo != null) {
        androidAdId = adInfo.getId();
        adDoNotTrack = adInfo.isLimitAdTrackingEnabled();
        if (androidAdId == null) {
          androidAdId = DeviceInfo.notFoundVal;
        }
      }

      //Send Data to callback
      callback.onSuccess(androidAdId, adDoNotTrack);
    } catch (IOException | GooglePlayServicesNotAvailableException e) {
      // Unrecoverable error connecting to Google Play services (e.g.,
      // the old version of the service doesn't support getting AdvertisingId).
      Log.d(DeviceInfo.nameOfLib, "Google Play Services Not Available Exception", e);
    } catch (GooglePlayServicesRepairableException e) {
      Log.d(DeviceInfo.nameOfLib, "Google Play Services Repairable Exception", e);
    }
  }

  /**
   * The interface Ad identifier callback.
   */
  public interface AdIdentifierCallback {
    /**
     * On success.
     *
     * @param adIdentifier
     *     the ad identifier
     * @param adDonotTrack
     *     the ad donot track
     */
    void onSuccess(String adIdentifier, boolean adDonotTrack);
  }
}
