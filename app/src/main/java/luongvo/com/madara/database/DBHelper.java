package luongvo.com.madara.database;

import android.content.Context;
import android.util.Pair;

import luongvo.com.madara.R;
import luongvo.com.madara.model.Note;
import luongvo.com.madara.model.Notebook;
import luongvo.com.madara.model.Tag;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Lam Le Thanh The on 12/11/2017.
 */

/*
 * For testing:
        DBHelper.open(MainActivity.this);
        List<Notebook> notebooks = DBHelper.getNotebooks();
        List<Note> notes = DBHelper.getNotes(notebooks.get(1).getId());
        DBHelper.close();

 */

public class DBHelper {
    private static DB db = null;

    public static void open(Context context) {
        try {
            close();
            db = DBFactory.open(context);

            if (!db.exists(DBSchema.DB_SETUP)) {
                db.put(DBSchema.DB_SETUP, Calendar.getInstance());
                createSampleData();
            }
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public static void close() {
        try {
            if (db != null)
                db.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public static List<Notebook> getNotebooks() {
        try {
            List<Notebook> res = new ArrayList<>();
            String[] notebookIds = db.getArray(DBSchema.NOTEBOOK_IDS, String.class);
            for (String tmp : notebookIds) {
                res.add(new Notebook(tmp, db));
            }
            return res;
        } catch (SnappydbException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Note> getNotes(String notebookId) {
        try {
            List<Note> res = new ArrayList<>();
            String[] noteIds = db.getArray(notebookId + DBSchema.NOTEBOOK_NOTE_IDS, String.class);
            for (String tmp : noteIds) {
                res.add(new Note(tmp, db));
            }
            return res;
        } catch (SnappydbException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get all notes' names for searching
    // Pair<id, name>
    public List<Pair<String, String>> getAllNoteNames() {
        try {
            List<Pair<String, String>> res = new ArrayList<>();
            String[] notebooks = db.getArray(DBSchema.NOTEBOOK_IDS, String.class);

            for (int i = 0; i < notebooks.length; ++i) {
                List<String> noteIds = new ArrayList<>(Arrays.asList(db.getArray(notebooks[i] + DBSchema.NOTEBOOK_NOTE_IDS, String.class)));
                for (int j = 0 ; j < noteIds.size(); ++j) {
                    res.add(new Pair<>(noteIds.get(j), db.get(noteIds.get(j))));
                }
            }
            return res;
        } catch (SnappydbException e) {
            e.printStackTrace();
            return null;
        }
    }

    // get all notes by tag, called in search activity if user pressed a tag result
    public List<Note> getNotesByTag(String tag) {
        try {
            List<Note> res = new ArrayList<>();
            String[] tmp = db.getArray(DBSchema.TAG_PREFIX + tag, String.class);
            List<String> notes = new ArrayList<>(Arrays.asList(tmp));

            for (int i = 0 ; i < notes.size(); ++i) {
                res.add(new Note(notes.get(i), db));
            }
            return res;
        } catch (SnappydbException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get all available tag for querying and searching
    public List<String> getAllTags() {
    	try {
    		List<String> res = new ArrayList<>();
    		String[] keys = db.findKeys(DBSchema.TAG_PREFIX);

    		for (String tmp : keys) {
    			res.add(tmp.substring(7, tmp.length()));
    		}
    		return res;
    	} catch (SnappydbException e) {
    		e.printStackTrace();
    		return null;
    	}
    }

    // In Note edit activity
		/*
		if (newnote) {
    		create new blank note = Note("", "", null)
    	} else if (editnote) {
    		create new blank note = Note(id, db);
    	}
		press save button -> call saveNote (pass created note)
    	*/
    public void saveNote(Note note, String name, String content, List<String> tags) {
    	note.update(name, content, tags);
    	note.write(db);
    }

    private static void createSampleData() {
        try {
            Notebook madara = new Notebook("Madara", R.drawable.notebook_cover_1, "Shenlong", null);
            madara.write(db);

            List<String> tags = new ArrayList<>();
            tags.add("first");
            Note firstNote = new Note("My First Note", "This is my very first note!", tags);
            firstNote.write(db);

            Note delNote = new Note("Deleted Note", "An example of a deleted note.", null);
            delNote.delete();
            delNote.write(db);

            List<String> noteIds = new ArrayList<>();
            noteIds.add(firstNote.getId());
            noteIds.add(delNote.getId());
            Notebook firstNB = new Notebook("My First Notebook", R.drawable.notebook_cover_8, "", noteIds);
            firstNB.write(db);

            List<String> firstBelongTo = new ArrayList<>();
            firstBelongTo.add(firstNote.getId());
            Tag firstTag = new Tag("first", firstBelongTo);
            firstTag.write(db);

            db.put(DBSchema.NOTEBOOK_IDS, new String[]{madara.getId(), firstNB.getId()});
            db.put(DBSchema.TRASH_NOTES, new String[]{delNote.getId()});
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }
}