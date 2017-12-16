package luongvo.com.madara;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.snappydb.DB;

import luongvo.com.madara.database.DBHelper;
import luongvo.com.madara.model.QuickNote;
import luongvo.com.madara.utils.Constants;

public class QuickNoteEditorActivity extends AppCompatActivity {

    private QuickNote quickNote;
    private boolean editMode = false;
    private EditText txtEditQuickNoteTitle, txtEditQuickNoteContent;
    private AlertDialog choseColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_note_editor);


        loadQuickNote();
        addControls();
        setupUI();
    }

    private void setupUI() {
        int themeColor = getColor(getResources()
            .getIdentifier(quickNote.getColor(), "color", this.getPackageName()));
        int themeColorDark = getColor(getResources()
            .getIdentifier(quickNote.getColor() + "_dark", "color", this.getPackageName()));

        Window window = getWindow();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // Change theme color
        window.setStatusBarColor(themeColorDark);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(themeColor));
        findViewById(R.id.layoutQuickNoteEditor).setBackgroundColor(themeColor);

        // Remove ActionBar title
        getSupportActionBar().setTitle("");

        // Load default value if in EditMode
        if (editMode) {
            txtEditQuickNoteTitle.setText(quickNote.getTitle());
            txtEditQuickNoteContent.setText(quickNote.getContent());
        }
    }

    private void loadQuickNote() {
        String quickNoteId = getIntent().getStringExtra(Constants.intentQuickNoteId);
        if (quickNoteId != null) {
            // Edit mode
            quickNote = DBHelper.getQuickNote(quickNoteId);
            editMode = true;
        } else {
            quickNote = new QuickNote();
        }
    }

    private void addControls() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtEditQuickNoteTitle = findViewById(R.id.txtEditQuickNoteTitle);
        txtEditQuickNoteContent = findViewById(R.id.txtEditQuickNoteContent);
        setupChangeColorDialog();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onDoneEditing();
                break;
            case R.id.actionDeleteQuickNote:
                //TODO: delete current quickNote
                break;
            case R.id.actionChangeColorQuickNote:
                choseColor.show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setupChangeColorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose color");

        final String[] colors = getResources().getStringArray(R.array.colors);
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                quickNote.setColor(colors[which]);
                setupUI();
            }
        });

        choseColor = builder.create();
    }


    private void onDoneEditing() {
        quickNote.setTitle(String.valueOf(txtEditQuickNoteTitle.getText()));
        quickNote.setContent(String.valueOf(txtEditQuickNoteContent.getText()));
        if (!(quickNote.getContent().equals(""))) {
            DBHelper.saveQuickNote(quickNote,
                quickNote.getTitle(),
                quickNote.getContent(),
                quickNote.getColor());
        }
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_quicknote_editor, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        onDoneEditing();
        super.onBackPressed();
    }
}
