package tk.nkduy.deviceinfo.base;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import java.util.List;

/**
 * The type sensor mod.
 */
public class SensorMod {

  private final SensorManager sensorManager;

  /**
   * Instantiates a new sensor mod.
   *
   * @param context
   *     the context
   */
  public SensorMod(final Context context) {
    sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
  }

  /**
   * Gets all sensors.
   *
   * @return the all sensors
   */
  public List<Sensor> getAllSensors() {
    return sensorManager.getSensorList(Sensor.TYPE_ALL);
  }
}
