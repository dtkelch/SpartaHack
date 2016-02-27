package gvsu.firesay;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.ui.FirebaseListAdapter;

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

    @ViewById(R.id.listView)
    ListView listView;

    @ViewById
    EditText editText;

    FireSayButton up, down, left, right;

    Firebase myFirebase;
    Firebase fireChat;
    FirebaseListAdapter mAdapter;

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
        fireChat = myFirebase.child("chat");
        Firebase fireButtons = myFirebase.child("buttons");
        fireButtons.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                setButtonColors(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                setButtonColors(dataSnapshot);
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
        mAdapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage
                .class, android.R.layout.two_line_list_item, fireChat) {
            @Override
            protected void populateView(View view, ChatMessage chatMessage, int i) {
                ((TextView)view.findViewById(android.R.id.text1)).setText
                        (chatMessage.getMessage());
                ((TextView)view.findViewById(android.R.id.text2)).setText
                        (chatMessage.getName());

            }
        };
        listView.setAdapter(mAdapter);
    }

    private void setButtonColors(DataSnapshot dataSnapshot) {
        FireSayButton button = dataSnapshot.getValue(FireSayButton
                    .class);
        Button uiButton = findButtonByName(button.getDirection());
        if (button.isLongClicked()) {
            uiButton.setBackgroundColor(button
                    .getColorLongClicked());
        } else {
            uiButton.setBackgroundColor(button.getColorClicked());
        }
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

    @Click(R.id.button_go)
    void goWasClicked() {
        fireChat.push().setValue(new ChatMessage("me", editText.getText()
                .toString()));
        editText.setText("");
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
