package gvsu.firesay;

/**
 * Created by droidowl on 2/27/16.
 */
public class FireSayButton {
    String direction;
    boolean longClicked;
    int colorClicked;
    int colorLongClicked;
    public FireSayButton() {
    }

    public FireSayButton(String direction, boolean longClicked) {
        this.direction = direction;
        this.longClicked = longClicked;
    }

    public FireSayButton(String direction, boolean longClicked, int
            colorClicked,
                         int
            colorLongClicked) {
        this.direction = direction;
        this.longClicked = longClicked;
        this.colorClicked = colorClicked;
        this.colorLongClicked = colorLongClicked;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public boolean isLongClicked() {
        return longClicked;
    }

    public void setLongClicked(boolean longClicked) {
        this.longClicked = longClicked;
    }

    public int getColorClicked() {
        return colorClicked;
    }

    public void setColorClicked(int colorClicked) {
        this.colorClicked = colorClicked;
    }

    public int getColorLongClicked() {
        return colorLongClicked;
    }

    public void setColorLongClicked(int colorLongClicked) {
        this.colorLongClicked = colorLongClicked;
    }
}
