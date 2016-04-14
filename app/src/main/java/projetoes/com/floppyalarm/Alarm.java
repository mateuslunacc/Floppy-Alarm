package projetoes.com.floppyalarm;

import android.os.Parcel;
import android.os.Parcelable;

public class Alarm implements Parcelable {
    private boolean active;
    private String label;
    private boolean vibrate;
    //conferir
    private boolean snooze;
    private boolean puzzle;
    private int hour;
    private int minute;
    private String timeDay;

    public Alarm() {
        this.label = "Alarm";
        this.active = false;
        this.vibrate = false;
        //conferir
        this.puzzle = false;
        this.snooze = false;
        this.minute = 0;
        this.hour = 0;
        this.timeDay = "";
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setPuzzle(boolean active) {
        this.active = active;
    }

    public boolean isPuzzle() {
        return active;
    }

    public void setSnooze(boolean active) {
        this.active = active;
    }

    public boolean isSnooze() {
        return active;
    }

    //conferir
    public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
    }

    public boolean isVibrate() {
        return vibrate;
    }

    public void setLabel(String newLabel) {
        this.label = newLabel;
    }

    public String getLabel() {
        return this.label;
    }

    public void setTime(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getMinute() {
        return this.minute;
    }

    public int getHour() {
        return this.hour;
    }

    private Alarm(Parcel in) {
        label = in.readString();
        active = in.readByte() != 0x00;
        vibrate = in.readByte() != 0x00;
        puzzle = in.readByte() != 0x00;
        snooze = in.readByte() != 0x00;
        hour = in.readInt();
        minute = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(label);
        dest.writeByte((byte) (active ? 0x01 : 0x00));
        dest.writeByte((byte) (vibrate ? 0x01 : 0x00));
        //conferir
        dest.writeByte((byte) (puzzle ? 0x01 : 0x00));
        dest.writeByte((byte) (snooze ? 0x01 : 0x00));
        dest.writeInt(hour);
        dest.writeInt(minute);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Alarm> CREATOR = new Parcelable.Creator<Alarm>() {
        @Override
        public Alarm createFromParcel(Parcel in) {
            return new Alarm(in);
        }

        @Override
        public Alarm[] newArray(int size) {
            return new Alarm[size];
        }
    };

    public String getTimeDay() {
        return timeDay;
    }

    public void setTimeDay(String timeDay) {
        this.timeDay = timeDay;
    }
}