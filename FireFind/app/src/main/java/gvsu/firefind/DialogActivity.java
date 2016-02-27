package gvsu.firefind;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@EActivity
public class DialogActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient
        .OnConnectionFailedListener {

    private static final int PICK_IMAGE_REQUEST = 2;
    private static final int DONE_RESULT = 1;
    @App
    FireFindApplication fireFind;

    @ViewById(R.id.name)
    EditText name;

    @ViewById(R.id.desc)
    EditText desc;

    GoogleApiClient mGoogleApiClient;

    Location mLastLocation;

    String encodedImage;

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
        FireFindItem item = new FireFindItem();
        if (encodedImage != null) {
            item.setImage(encodedImage);
        } else {
            item.setImage("NULL");
        }
        if (mLastLocation != null) {
            item.setLat(mLastLocation.getLatitude());
            item.setLng(mLastLocation.getLongitude());
        } else {
            item.setLat(0.0);
            item.setLng(0.0);
        }
        item.setDesc(desc.getText().toString());
        item.setName(name.getText().toString());
        fireFind.myFirebase.child("item").push().setValue(item);
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
        Intent intent = new Intent();
        // Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                Bitmap bm = BitmapFactory.decodeFile("/path/to/image.jpg");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();


                encodedImage = Base64.encodeToString(b, Base64.DEFAULT);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
