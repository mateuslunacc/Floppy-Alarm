package projetoes.com.floppyalarm;

import android.os.Parcel;
import android.os.Parcelable;

public class Alarm implements Parcelable {
    private String label;
    private boolean vibrate;
    private int hour;
    private int minute;
    private String timeDay;

    public Alarm() {
        this.label = "Alarm";
        this.vibrate = false;
        this.minute = 0;
        this.hour = 0;
        this.timeDay = "";
    }

    public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
    }

    public void setLabel(String newLabel) {
        this.label = newLabel;
    }

    public String getLabel() {
        return this.label;
    }

    public boolean isVibrate() {
        return vibrate;
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

    protected Alarm(Parcel in) {
        label = in.readString();
        vibrate = in.readByte() != 0x00;
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
        dest.writeByte((byte) (vibrate ? 0x01 : 0x00));
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