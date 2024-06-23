package sypztep.sypwid.client.util;


import sypztep.sypwid.client.SypWidConfig;

public class SpeedConvert {
    public static String speedText(double speed, SypWidConfig.SpeedUnit speedUnit) {
        return switch (speedUnit) {
            case BLOCKS_PER_SECOND -> String.format("%.2f blocks/sec", metersPerSecond(speed));
            case KILOMETERS_PER_HOUR -> String.format("%.2f km/h", kilometersPerSecond(speed));
        };
    }

    private static double metersPerSecond(double speed) {
        return speed / 0.05F;
    }

    private static double kilometersPerSecond(double speed) {
        return metersPerSecond(speed) * 3.6;
    }
}