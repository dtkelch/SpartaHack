package gvsu.firefind;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity
public class MainActivity extends AppCompatActivity {

    @App
    FireFindApplication fireFind;

    @ViewById(R.id.editText)
    EditText editText;

    @ViewById(R.id.listView)
    ListView listView;

    ItemAdapter listAdapter;

    List<FireFindItem> foundItems;

    List<FireFindItem> allItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase itemBase = fireFind.myFirebase.child("item");
        foundItems = new ArrayList<>();
        allItems = new ArrayList<>();
        listAdapter = new ItemAdapter(this, R.layout.list_view_item, allItems);
        itemBase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Log.e("WOW",dataSnapshot.getValue().toString());
                    FireFindItem item = dataSnapshot.getValue(FireFindItem.class);
                    listAdapter.add(item);
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FireFindItem item = (FireFindItem) parent.getItemAtPosition
                        (position);
                AlertDialog.Builder builder = new AlertDialog.Builder
                        (MainActivity.this);
                builder.setMessage(item.toString());
                builder.setTitle("Tags for the photo are:");
                builder.setPositiveButton("Sweet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                FireFindItem item = (FireFindItem)parent
                        .getItemAtPosition
                        (position);
                fireFind.myFirebase.child("item/" + item.getName())
                        .removeValue();
                listAdapter.remove(item);
                listAdapter.notifyDataSetChanged();
                return false;
            }
        });
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

    @Click(R.id.fab)
    void fabWasClicked() {
        Intent dialog = new Intent(this, DialogActivity_.class);
        startActivityForResult(dialog, 1);
    }

    @Click(R.id.search_button)
    void searchWasClicked() {
        Query query = fireFind.myFirebase.child("item").orderByChild("item");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                foundItems.clear();
                for (DataSnapshot s : dataSnapshot.getChildren()) {
                    if (s.getValue().toString().contains(editText.getText()
                            .toString())) {
                        foundItems.add(s.getValue(FireFindItem.class));
                        ArrayAdapter adapter = new ItemAdapter
                                (MainActivity.this, R.layout
                                        .list_view_item, foundItems);
                        listView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK) {
                updateList();
                listAdapter.notifyDataSetChanged();
            }
    }

    private void updateList() {
        Query query = fireFind.myFirebase.child("item").orderByChild("item");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allItems.clear();
                for (DataSnapshot s : dataSnapshot.getChildren()) {
                        allItems.add(s.getValue(FireFindItem.class));
                        ArrayAdapter adapter = new ItemAdapter
                                (MainActivity.this, R.layout
                                        .list_view_item, allItems);
                        listView.setAdapter(adapter);
                }
            }
                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
    }
}
