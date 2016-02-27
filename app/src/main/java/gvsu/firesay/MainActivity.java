package gvsu.firesay;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.LongClick;
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

    FireSayButton up, down, left, right;

    Firebase myFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        up = new FireSayButton("up", false, Color.CYAN, Color.BLUE);
        down = new FireSayButton("down", false, Color
                .YELLOW, Color.GREEN);
        left = new FireSayButton("left", false, Color
                .RED, Color.DKGRAY);
        right = new FireSayButton("right", false, Color
                .MAGENTA, Color.WHITE);
        Firebase.setAndroidContext(this);
        myFirebase = new Firebase("https://incandescent-fire-2307.firebaseio" +
                ".com/");
        myFirebase.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot b : dataSnapshot.getChildren()) {
                    FireSayButton button = b.getValue(FireSayButton
                            .class);
                    Button uiButton = findButtonByName(button.getDirection());
                    if (button.isLongClicked()) {
                        uiButton.setBackgroundColor(button
                                .getColorLongClicked());
                    } else {
                        uiButton.setBackgroundColor(button.getColorClicked());
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private Button findButtonByName(String direction) {
        switch (direction) {
            case "up":
                return upButton;
            case "down":
                return downButton;
            case "left":
                return leftButton;
            case "right":
                return rightButton;
            default:
                return null;
        }
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
        up.setLongClicked(false);
        myFirebase.child("buttons/" + up.getDirection()).setValue(up);
    }
    @Click(R.id.button_down)
    void downWasClicked() {
        down.setLongClicked(false);
        myFirebase.child("buttons/" + down.getDirection()).setValue(down);
    }
    @Click(R.id.button_left)
    void leftWasClicked() {
        left.setLongClicked(false);
        myFirebase.child("buttons/" + left.getDirection()).setValue(left);
    }
    @Click(R.id.button_right)
    void rightWasClicked() {
        right.setLongClicked(false);
        myFirebase.child("buttons/" + right.getDirection()).setValue(right);
    }
    @LongClick(R.id.button_up)
    void upWasLongClicked() {
        up.setLongClicked(true);
        myFirebase.child("buttons/" + up.getDirection()).setValue(up);
    }
    @LongClick(R.id.button_down)
    void downWasLongClicked() {
        down.setLongClicked(true);
        myFirebase.child("buttons/" + down.getDirection()).setValue(down);
    }
    @LongClick(R.id.button_left)
    void leftWasLongClicked() {
        left.setLongClicked(true);
        myFirebase.child("buttons/" + left.getDirection()).setValue(left);
    }
    @LongClick(R.id.button_right)
    void rightWasLongClicked() {
        right.setLongClicked(true);
        myFirebase.child("buttons/" + right.getDirection()).setValue(right);
    }
}
