package gvsu.firesay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.ui.auth.core.AuthProviderType;
import com.firebase.ui.auth.core.FirebaseLoginBaseActivity;
import com.firebase.ui.auth.core.FirebaseLoginError;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

@EActivity
public class LoginActivity extends FirebaseLoginBaseActivity {


    @Click(R.id.button_login)
    void login() {
        showFirebaseLoginPrompt();
    }
    @Override
    protected void onStart() {
        super.onStart();
        // All providers are optional! Remove any you don't want.
        setEnabledAuthProvider(AuthProviderType.FACEBOOK);
        setEnabledAuthProvider(AuthProviderType.GOOGLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Firebase.setAndroidContext(this);
    }

    @Override
    protected Firebase getFirebaseRef() {
        return new Firebase("https://incandescent-fire-2307.firebaseio.com/");
    }

    @Override
    protected void onFirebaseLoginProviderError(FirebaseLoginError firebaseLoginError) {

    }

    @Override
    protected void onFirebaseLoginUserError(FirebaseLoginError firebaseLoginError) {

    }

    @Override
    protected void onFirebaseLoggedIn(AuthData authData) {
        super.onFirebaseLoggedIn(authData);
        Intent intent = new Intent(this, MainActivity_.class);
        startActivity(intent);
    }
}
