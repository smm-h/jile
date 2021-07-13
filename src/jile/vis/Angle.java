package jile.vis;

/** Mutable */
public class Angle {

    /** value >= 0 && value < 360 */
    private double value = 0;

    private Angle() {
    }

    public void setDegrees(double degrees) {

        while (degrees < 0)
            degrees += 360;
        while (degrees >= 360)
            degrees -= 360;

        this.value = degrees;
    }

    public void setRadians(double radians) {
        setDegrees(Math.toDegrees(radians));
    }

    public static Angle fromAngle(Angle angle) {
        var newAngle = new Angle();
        newAngle.value = angle.value;
        return newAngle;
    }

    public static Angle fromLine(float x1, float y1, float x2, float y2) {
        var angle = new Angle();
        angle.setRadians(Math.atan2(y2 - y1, x2 - x1));
        return angle;
    }

    public static Angle fromDegrees(double degrees) {
        var angle = new Angle();
        angle.setDegrees(degrees);
        return angle;
    }

    public static Angle fromRadians(double radians) {
        var angle = new Angle();
        angle.setRadians(radians);
        return angle;
    }

    public double getDegrees() {
        return value;
    }

    public double getRadians() {
        return Math.toRadians(value);
    }

    public Point makePoint(double distance) {
        var radians = getRadians();
        return new Point(Math.cos(radians) * distance, Math.sin(radians) * distance);
    }

    public void addDegrees(double degrees) {
        setDegrees(value + degrees);
    }
}
