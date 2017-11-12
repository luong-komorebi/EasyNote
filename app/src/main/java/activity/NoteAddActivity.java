package activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import luongvo.com.madara.R;
import model.Note;

import static activity.MainActivity.noteID;

public class NoteAddActivity extends AppCompatActivity {
    ImageButton addButton;
    EditText title;
    EditText author;
    EditText content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_add);
        //initialize views
        addButton = (ImageButton)findViewById(R.id.add_imgBtn);
        title = (EditText)findViewById(R.id.title_editTxt);
        author = (EditText) findViewById(R.id.author_editTxt);
        content = (EditText) findViewById(R.id.content_editTxt);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(title.getText().toString().isEmpty() || content.getText().toString().isEmpty()){
                    Toast.makeText(getApplication(),"Title and content cannot be empty ",Toast.LENGTH_SHORT).show();
                }else {

                    // Extract the first 5 words of content for snippet
                    String Content = content.getText().toString();
                    String[] ContentWords = Content.split("\\s+");
                    int endIndexOfSnippet = 0;

                    for(int i=0;i< (ContentWords.length>=5 ? 5 : ContentWords.length); i++){
                        // display only 5 words of the content
                        endIndexOfSnippet += (ContentWords[i].length());
                    }

                    Note note = new Note(noteID++, author.getText().toString(), title.getText().toString(),
                            Content.substring(0, endIndexOfSnippet), Content, 2);

                    Intent data = new Intent();
                    data.putExtra("note_added", note);
                    setResult(1997, data);
                    finish(); // destroy current activity
                }
            }
        });
    }
}
