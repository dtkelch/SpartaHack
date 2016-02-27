package gvsu.firefind;

/**
 * Created by droidowl on 2/27/16.
 */
public class FireFindItem {
    String desc;
    String name;
    String image;
    double lat;
    double lng;

    public FireFindItem() {
    }

    public FireFindItem(String name, String desc, double lat, double lng) {
        this.desc = desc;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    public FireFindItem(String desc, String name, String image, double lat, double lng) {
        this.desc = desc;
        this.name = name;
        this.image = image;
        this.lat = lat;
        this.lng = lng;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
