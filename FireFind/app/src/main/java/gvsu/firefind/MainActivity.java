package gvsu.firefind;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseListAdapter;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public class MainActivity extends AppCompatActivity {

    @App
    FireFindApplication fireFind;

    FirebaseListAdapter listAdapter;
    @ViewById(R.id.listView)
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase itemBase = fireFind.myFirebase.child("item");
        listAdapter = new FirebaseListAdapter<FireFindItem>(this,
                FireFindItem.class,
                android.R.layout.two_line_list_item, itemBase) {
            @Override
            protected void populateView(View view, FireFindItem item, int i) {
                ((TextView)view.findViewById(android.R.id.text1)).setText
                        (item.getName());
                ((TextView)view.findViewById(android.R.id.text2)).setText
                        (item.getDesc());

            }
        };
        listView.setAdapter(listAdapter);
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
        startActivity(dialog);
    }
}
