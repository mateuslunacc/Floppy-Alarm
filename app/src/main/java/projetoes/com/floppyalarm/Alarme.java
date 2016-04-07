package projetoes.com.floppyalarm;

public class Alarme {
    private String label;
    private boolean vibrate;

    public Alarme() {
        this.label = "Teste";
        this.vibrate = false;
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
}