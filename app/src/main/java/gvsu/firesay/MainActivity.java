package gvsu.firesay;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.firebase.client.Firebase;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public class MainActivity extends AppCompatActivity {


    @ViewById(R.id.button_up)
    Button upButton;

    @ViewById(R.id.button_down)
            Button downButton;

    @ViewById(R.id.button_left)
            Button leftButton;

    @ViewById(R.id.button_right)
            Button rightButton;

    Firebase myFirebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Firebase.setAndroidContext(this);
        myFirebase = new Firebase("https://incandescent-fire-2307.firebaseio" +
                ".com/");
        myFirebase.child("message").setValue("Hey");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Click(R.id.button_up)
    void upWasClicked() {
        myFirebase.child("up").setValue("clicked");
    }
    @Click(R.id.button_down)
    void downWasClicked() {
        myFirebase.child("down").setValue("clicked");
    }
    @Click(R.id.button_left)
    void leftWasClicked() {
        myFirebase.child("left").setValue("clicked");
    }
    @Click(R.id.button_right)
    void rightWasClicked() {
        myFirebase.child("right").setValue("clicked");
    }
}
