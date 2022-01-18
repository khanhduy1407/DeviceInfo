package tk.nkduy.deviceinfo.base;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({
            BatteryHealth.GOOD, BatteryHealth.HAVING_ISSUES
        })
@Retention(RetentionPolicy.CLASS)
public @interface BatteryHealth {
  int HAVING_ISSUES = 0;
  int GOOD = 1;
}
