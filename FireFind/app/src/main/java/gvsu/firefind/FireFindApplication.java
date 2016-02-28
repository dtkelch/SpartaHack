package gvsu.firefind;

import android.app.Application;

import com.cloudinary.Cloudinary;
import com.firebase.client.Firebase;
import com.firebase.geofire.GeoFire;

import org.androidannotations.annotations.EApplication;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by droidowl on 2/27/16.
 */
@EApplication
public class FireFindApplication extends Application {

    Firebase myFirebase;
    GeoFire myGeoFire;

    Cloudinary cloudinary;

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(getApplicationContext());
        myFirebase = new Firebase("https://incandescent-fire-2307.firebaseio" +
                ".com/");
        myGeoFire = new GeoFire(myFirebase);

        Map config = new HashMap();
        config.put("cloud_name", "dwhg3uhwl");
        config.put("api_key", "825657132193814");
        config.put("api_secret", "yVkd9Klr2cHXkDMCe_RDZ3AITRI");
        cloudinary = new Cloudinary(config);
    }

}
