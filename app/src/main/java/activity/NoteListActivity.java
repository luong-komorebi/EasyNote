package activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import luongvo.com.madara.R;


public class NoteListActivity extends AppCompatActivity {

    public static Context appContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_note);
        appContext = getApplicationContext();

        // Create new fragment and transaction
        NoteListFragment newFragment = new NoteListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add( R.id.fragment_listNote, newFragment );
        // Commit the transaction
        transaction.commit();
    }

}

