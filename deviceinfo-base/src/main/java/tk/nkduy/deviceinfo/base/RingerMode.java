package tk.nkduy.deviceinfo.base;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({
            RingerMode.SILENT, RingerMode.NORMAL, RingerMode.VIBRATE
        })
@Retention(RetentionPolicy.CLASS)
public @interface RingerMode {
  int SILENT = 0;
  int NORMAL = 1;
  int VIBRATE = 2;
}
