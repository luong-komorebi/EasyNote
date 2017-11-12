package luongvo.com.madara.database;

import android.content.Context;

import luongvo.com.madara.data.Note;
import luongvo.com.madara.data.Notebook;
import luongvo.com.madara.data.Tag;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import java.util.ArrayList;
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

            if (!db.exists("db_init")) {
                db.put("db_init", Calendar.getInstance());
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
            String[] notebookIds = db.getArray("notebooks", String.class);
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
            String[] noteIds = db.getArray(notebookId + "_notes", String.class);
            for (String tmp : noteIds) {
                res.add(new Note(tmp, db));
            }
            return res;
        } catch (SnappydbException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void createSampleData() {
        try {
            Notebook madara = new Notebook("Madara", "madara.png", "Shenlong", null);
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
            Notebook firstNB = new Notebook("My First Notebook", "default.png", "", noteIds);
            firstNB.write(db);

            List<String> firstBelongTo = new ArrayList<>();
            firstBelongTo.add(firstNote.getId());
            Tag firstTag = new Tag("first", firstBelongTo);
            firstTag.write(db);

            db.put("notebooks", new String[]{madara.getId(), firstNB.getId()});
            db.put("trash_notes", new String[]{delNote.getId()});
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }
}
