package gvsu.firefind;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public class DialogActivity extends AppCompatActivity {

    @App
    FireFindApplication fireFind;

    @ViewById(R.id.name)
    EditText name;

    @ViewById(R.id.desc)
    EditText desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Click(R.id.done_button)
    void doneWasClicked() {
        fireFind.myFirebase.child("item").push().setValue(new FireFindItem(name
                .getText().toString(), desc.getText().toString(),
                44.44, 55.55));
        finish();
    }
}
