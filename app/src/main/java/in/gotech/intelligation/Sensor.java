package in.gotech.intelligation;

/**
 * Created by anirudh on 09/08/15.
 */
public class Sensor {
    int sensorId;
    String cropName;
    int sensorValue;
    boolean motorStatus;
    boolean autoStatus;
    int pinNumber;
    boolean editing;

    public Sensor(int sensorId, int sensorValue, String cropName, boolean autoStatus, boolean motorStatus, int pinNumber) {
        this.sensorId = sensorId;
        this.sensorValue = sensorValue;
        this.cropName = cropName;
        this.autoStatus = autoStatus;
        this.motorStatus = motorStatus;
        this.pinNumber = pinNumber;
        this.editing = false;
    }

    public int getSensorId() {
        return sensorId;
    }

    public String getCropName() {
        return cropName;
    }

    public int getSensorValue() {
        return sensorValue;
    }

    public boolean getAutoStatus() {
        return autoStatus;
    }

    public boolean getMotorStatus() {
        return motorStatus;
    }
}
