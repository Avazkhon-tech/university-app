package uz.mu.lms.utils;

public class LocationUtils {

    public static Rectangle getBoundingBox(double latitude, double longitude, double radiusInMeters) {
        double earthRadius = 6378137; // Earth radius in meters
        double angularRadius = radiusInMeters / earthRadius;

        double northLat = latitude + Math.toDegrees(angularRadius);
        double southLat = latitude - Math.toDegrees(angularRadius);
        double westLon = longitude - Math.toDegrees(angularRadius / Math.cos(Math.toRadians(latitude)));
        double eastLon = longitude + Math.toDegrees(angularRadius / Math.cos(Math.toRadians(latitude)));

        return new Rectangle(northLat, southLat, eastLon, westLon);
    }

    public static class Rectangle {
        public final double northLat;
        public final double southLat;
        public final double eastLon;
        public final double westLon;

        public Rectangle(double northLat, double southLat, double eastLon, double westLon) {
            this.northLat = northLat;
            this.southLat = southLat;
            this.eastLon = eastLon;
            this.westLon = westLon;
        }
    }
}
