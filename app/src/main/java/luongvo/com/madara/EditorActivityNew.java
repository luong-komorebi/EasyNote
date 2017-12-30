package luongvo.com.madara;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.irshulx.Editor;
import com.github.irshulx.EditorListener;
import com.github.irshulx.models.EditorTextStyle;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import luongvo.com.madara.database.DBHelper;
import luongvo.com.madara.model.NoteCuaThanh;
import luongvo.com.madara.utils.Constants;


/**
 * Created by Thanh on 12/11/2017.
 */

public class EditorActivityNew extends AppCompatActivity {



    private Editor editor;
    private boolean doubleBackToExitPressedOnce = false;
    private EditText subjectEditTxt;
    private String tempImageURL;

    private NoteCuaThanh note;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_editor);

        String noteId = getIntent().getStringExtra(Constants.intentNoteId);
        if (noteId != null) {
            note = DBHelper.getNote(noteId);
        }
        else {
          note = new NoteCuaThanh("", "<p data-tag=\"input\"></p>", null);
        }

        subjectEditTxt = findViewById(R.id.subject);
        addEditor();
    }


    private void addStyleButton() {

        findViewById(R.id.action_save_note).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String noteSubject = String.valueOf(subjectEditTxt.getText());
                String content = editor.getContentAsHTML();
                if(noteSubject.equals("")){

                    Toast.makeText(getApplicationContext(), getString(R.string.subject_require_str), Toast.LENGTH_SHORT).show();

                }else{
                    //TODO: Save note to database here
                    DBHelper.saveNote(note, noteSubject, content, null);
                    System.out.println("saved note activated");
                }
            }
        });



        findViewById(R.id.action_bold).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                editor.updateTextStyle(EditorTextStyle.BOLD);
            }
        });

        findViewById(R.id.action_italic).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                editor.updateTextStyle(EditorTextStyle.ITALIC);
            }
        });


        findViewById(R.id.action_heading1).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                editor.updateTextStyle(EditorTextStyle.H1);
            }
        });

        findViewById(R.id.action_heading2).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                editor.updateTextStyle(EditorTextStyle.H2);
            }
        });

        findViewById(R.id.action_indent).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                editor.updateTextStyle(EditorTextStyle.INDENT);
            }
        });

        findViewById(R.id.action_outdent).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                editor.updateTextStyle(EditorTextStyle.OUTDENT);
            }
        });

        findViewById(R.id.action_insert_bullets).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                editor.insertList(false);
            }
        });

        findViewById(R.id.action_insert_numbers).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                editor.insertList(true);
            }
        });

        findViewById(R.id.action_insert_image).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                editor.openImagePicker();
            }
        });

        findViewById(R.id.action_insert_link).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                editor.insertLink();
            }
        });

        editor.setEditorListener(new EditorListener() {
            @Override
            public void onTextChanged(EditText editText, Editable text) {
                // Toast.makeText(EditorTestActivity.this, text, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUpload(Bitmap image, String uuid) {
                Toast.makeText(EditorActivityNew.this, uuid, Toast.LENGTH_LONG).show();
                editor.onImageUploadComplete(tempImageURL, uuid);
            }
        });
    }

    private void addEditor() {
        editor = findViewById(R.id.editor);
        editor.setPadding(10, 10, 10, 10);
        addStyleButton();

        //TODO: Populate html data here in case of editting - call render with HtmlString argument
        subjectEditTxt.setText(note.getName());
        editor.render(note.getContent());
        // In case the user is making a new note
        //editor.render();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == editor.PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK&& data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                tempImageURL = uri.toString();
                // can't use Picasso on this, something about Asynchronous call is not welcome
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                int nh = (int) ( bitmap.getHeight() * (512.0 / bitmap.getWidth()) );
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                editor.insertImage(scaled);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (resultCode == Activity.RESULT_CANCELED) {

        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            // TODO: Save the note to cache here ( if wanted )
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.doubleClickToExit_str, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
