package gvsu.firefind;

import java.util.List;
import java.util.Map;

/**
 * Created by droidowl on 2/27/16.
 */
public class FireFindItem {
    String desc;
    String name;
    Map uploadResult;
    double lat;
    double lng;
    List<String> tags;

    public FireFindItem() {
    }

    public FireFindItem(String name, String desc, double lat, double lng) {
        this.desc = desc;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    public FireFindItem(String desc, String name, Map uploadResult, double lat, double
            lng) {
        this.desc = desc;
        this.name = name;
        this.uploadResult = uploadResult;
        this.lat = lat;
        this.lng = lng;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Map getUploadResult() {
        return uploadResult;
    }

    public void setUploadResult(Map uploadResult) {
        this.uploadResult = uploadResult;
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

    @Override
    public String toString() {
        String fmt ="";
        if (tags == null)
            return "";
        for (String t : tags){
            fmt += t + "\t";
        }
        return fmt;
    }
}
