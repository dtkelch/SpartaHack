package gvsu.firefind;

import android.app.Application;

import com.firebase.client.Firebase;

import org.androidannotations.annotations.EApplication;

/**
 * Created by droidowl on 2/27/16.
 */
@EApplication
public class FireFindApplication extends Application {

    Firebase myFirebase;

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(getApplicationContext());
        myFirebase = new Firebase("https://incandescent-fire-2307.firebaseio" +
                ".com/");
    }

}
