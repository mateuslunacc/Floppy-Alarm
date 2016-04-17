package projetoes.com.floppyalarm;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Alarm implements Parcelable {
    private boolean active;
    private String label;
    private boolean vibrate;
    private boolean snooze;
    private boolean repeat;
    private boolean puzzle;
    private int hour;
    private int minute;
    private List<Integer> selectedDays;
    private String ringtoneUriString;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isVibrate() {
        return vibrate;
    }

    public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public boolean isSnooze() {
        return snooze;
    }

    public void setSnooze(boolean snooze) {
        this.snooze = snooze;
    }

    public boolean isPuzzle() {
        return puzzle;
    }

    public void setPuzzle(boolean puzzle) {
        this.puzzle = puzzle;
    }

    public int getHour() {
        return hour;
    }

    public void setTime(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getMinute() {
        return minute;
    }

    public List<Integer> getSelectedDays() {
        return selectedDays;
    }

    public void setSelectedDays(List<Integer> selectedDays) {
        this.selectedDays = selectedDays;
    }

    public String getRingtoneUriString() {
        return ringtoneUriString;
    }

    public void setRingtoneUriString(String ringtoneUriString) {
        this.ringtoneUriString = ringtoneUriString;
    }

    public Alarm() {
        this.active = false;
        this.label = "Alarm";
        this.vibrate = false;
        this.snooze = false;
        this.repeat = false;
        this.puzzle = false;
        this.hour = 0;
        this.minute = 0;
        this.selectedDays = new ArrayList<Integer>();
        this.ringtoneUriString = "";
    }

    protected Alarm(Parcel in) {
        active = in.readByte() != 0x00;
        label = in.readString();
        vibrate = in.readByte() != 0x00;
        snooze = in.readByte() != 0x00;
        repeat = in.readByte() != 0x00;
        puzzle = in.readByte() != 0x00;
        hour = in.readInt();
        minute = in.readInt();
        selectedDays = in.readArrayList(null);
        ringtoneUriString = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (active ? 0x01 : 0x00));
        dest.writeString(label);
        dest.writeByte((byte) (vibrate ? 0x01 : 0x00));
        dest.writeByte((byte) (snooze ? 0x01 : 0x00));
        dest.writeByte((byte) (repeat ? 0x01 : 0x00));
        dest.writeByte((byte) (puzzle ? 0x01 : 0x00));
        dest.writeInt(hour);
        dest.writeInt(minute);
        dest.writeList(selectedDays);
        dest.writeString(ringtoneUriString);
    }

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
}