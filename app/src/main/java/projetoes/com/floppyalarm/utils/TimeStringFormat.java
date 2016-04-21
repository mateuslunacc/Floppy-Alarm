package projetoes.com.floppyalarm.utils;

public final class TimeStringFormat {

    public static String formataString(int hour, int minute, boolean is24h) {
        if (is24h) {
            return format24Hours(hour, minute);
        } else {
            return formatAmPm(hour, minute);
        }
    }

    private static String format24Hours(int hour, int minute) {
        return String.format("%02d:%02d", hour, minute);
    }

    private static String formatAmPm(int hour, int minute) {
        int hourCheck = hour;
        if (hour == 0) {
            hour = 12;
        } else if (hour > 12) {
            hour = hour - 12;
        }
        return String.format("%02d:%02d %s", hour, minute, hourCheck < 12 ? "AM" : "PM");
    }

}
