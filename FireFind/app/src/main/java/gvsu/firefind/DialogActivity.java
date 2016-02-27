package gvsu.firefind;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public class DialogActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient
        .OnConnectionFailedListener {

    @App
    FireFindApplication fireFind;

    @ViewById(R.id.name)
    EditText name;

    @ViewById(R.id.desc)
    EditText desc;

    GoogleApiClient mGoogleApiClient;

    Location mLastLocation;

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
        if (mLastLocation != null) {
            fireFind.myFirebase.child("item").push().setValue(new FireFindItem(name
                    .getText().toString(), desc.getText().toString(),
                mLastLocation.getLatitude(), mLastLocation.getLongitude()));
        } else {
            fireFind.myFirebase.child("item").push().setValue(new
                    FireFindItem(name.getText().toString(), desc.getText()
                    .toString(), 0.0, 0.0));
        }
        finish();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
