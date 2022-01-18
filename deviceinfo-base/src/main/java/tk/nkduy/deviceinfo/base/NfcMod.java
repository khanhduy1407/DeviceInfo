package tk.nkduy.deviceinfo.base;

import android.content.Context;
import android.nfc.NfcAdapter;

/**
 * Nfc Mod Class
 */
public class NfcMod {

  private NfcAdapter nfcAdapter = null;

  /**
   * Instantiates a new nfc mod.
   *
   * @param context
   *     the context
   */
  public NfcMod(final Context context) {
    nfcAdapter = NfcAdapter.getDefaultAdapter(context);
  }

  /**
   * Is nfc present boolean.
   *
   * @return the boolean
   */
  public final boolean isNfcPresent() {
    return nfcAdapter != null;
  }

  /**
   * Is nfc enabled boolean.
   *
   * @return the boolean
   */
  public final boolean isNfcEnabled() {
    return nfcAdapter != null && nfcAdapter.isEnabled();
  }
}
