package activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import luongvo.com.madara.R;


public class MainActivity extends AppCompatActivity {
    Button toNoteList;
    public static int noteID = 0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Do something else here

        //This is to transition into panel of notes list
         toNoteList = (Button) findViewById(R.id.toNoteListBtn);
        toNoteList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NoteListActivity.class);
                startActivity(intent);
            }
        });


    }
}
