package gvsu.firefind;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;

import com.clarifai.api.RecognitionRequest;
import com.clarifai.api.RecognitionResult;
import com.clarifai.api.Tag;
import com.cloudinary.utils.ObjectUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EActivity
public class DialogActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient
        .OnConnectionFailedListener {

    private static final int PICK_IMAGE_REQUEST = 2;
    private static final int DONE_RESULT = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 3;
    @App
    FireFindApplication fireFind;

    @ViewById(R.id.name)
    EditText name;

    @ViewById(R.id.desc)
    EditText desc;

    GoogleApiClient mGoogleApiClient;

    Location mLastLocation;

    Map uploadResult;

    File photoFile;

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        verifyStoragePermissions(this);
        buildGoogleApiClient();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
    }

    @Click(R.id.done_button)
    void doneWasClicked() {

        Intent returnIntent = new Intent();
        returnIntent.putExtra("result",DONE_RESULT);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    @Override
    public void onConnected(Bundle bundle) {
        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        } catch (SecurityException e) {
            Log.e("WOW", e.getLocalizedMessage());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Click(R.id.photo_button)
    void photoButtonWasClicked() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Show only images, no videos or anything else
        // Always show the chooser (if there are multiple options available)
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Click(R.id.take_button)
    void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                photoFile = Utils.createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK &&
                data != null && data.getData() != null) {
            Uri uri = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(uri,filePathColumn,
                    null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            File f = new File(picturePath);
            uploadPhoto(f);
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode ==
                RESULT_OK) {
            // photoFile = file path of pic
            if (photoFile != null)
                uploadPhoto(photoFile);
        }
    }

    @Background
    void uploadPhoto(File f) {
        try {
            uploadResult = fireFind.cloudinary.uploader().upload
                    (f,
                            ObjectUtils
                                    .emptyMap());
            FireFindItem item = new FireFindItem();
            List<RecognitionResult> results =  fireFind.clarifai.recognize
                    (new RecognitionRequest(f));
            List<String> stringTags = new ArrayList<>();
            for (RecognitionResult r : results) {
                List<Tag> tags = r.getTags();
                for (Tag t : tags){
                    stringTags.add(t.getName());
                }
            }
            item.setTags(stringTags);
            item.setUploadResult(uploadResult);
            if (mLastLocation != null) {
                //fireFind.myGeoFire.setLocation("Nearby Finds",new GeoLocation(mLastLocation.getLatitude(),mLastLocation.getLongitude()));
                item.setLat(mLastLocation.getLatitude());
                item.setLng(mLastLocation.getLongitude());
            } else {
                item.setLat(0.0);
                item.setLng(0.0);
            }
            item.setDesc(desc.getText().toString());
            item.setName(name.getText().toString());
            fireFind.myFirebase.child("item").push().setValue(item);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
