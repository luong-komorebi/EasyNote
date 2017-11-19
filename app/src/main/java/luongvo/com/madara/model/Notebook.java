package luongvo.com.madara.model;
import luongvo.com.madara.database.DBSchema;
import com.snappydb.DB;
import com.snappydb.SnappydbException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * Created by Lam Le Thanh The on 12/11/2017.
 */

public class Notebook {
    private String name;
    private String id;
    private int cover;
    private String password;

    private Calendar created;
    private Calendar deleted;

    private List<String> noteIds;

    public Notebook(String name, int cover, String password, List<String> noteIds) {
        this.name = name;
        this.cover = cover;
        this.password = password;
        this.noteIds = noteIds;

        id = UUID.randomUUID().toString();

        created = Calendar.getInstance();
        deleted = null;
    }

    public Notebook(String id, DB db) {
        try {
            this.id = id;

            name = db.get(id);
            cover = db.getInt(id + DBSchema.NOTEBOOK_COVER);
            created = db.get(id + DBSchema.NOTEBOOK_CREATED, Calendar.class);

            if (db.exists(id + DBSchema.NOTEBOOK_DELETED))
                deleted = db.get(id + DBSchema.NOTEBOOK_DELETED, Calendar.class);
            else
                deleted = null;

            if (db.exists(id + DBSchema.NOTEBOOK_PASS))
                password = db.get(id + DBSchema.NOTEBOOK_PASS);
            else
                password = "";

            if (db.exists(id + DBSchema.NOTEBOOK_NOTE_IDS)) {
                String[] tmp = db.getArray(id + DBSchema.NOTEBOOK_NOTE_IDS, String.class);
                noteIds = new ArrayList<>(Arrays.asList(tmp));
            }
            else
                noteIds = null;
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public void write(DB db) {
        try {
            db.put(id, name);
            db.put(id + DBSchema.NOTEBOOK_CREATED, created);
            db.putInt(id + DBSchema.NOTEBOOK_COVER, cover);

            if (password != null && !password.isEmpty())
                db.put(id + DBSchema.NOTEBOOK_PASS, password);

            if (deleted != null)
                db.put(id + DBSchema.NOTEBOOK_DELETED, deleted);

            if (noteIds != null)
                db.put(id + DBSchema.NOTEBOOK_NOTE_IDS, noteIds.toArray(new String[noteIds.size()]));

        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        deleted = Calendar.getInstance();
    }

    public void undelete() {
        deleted = null;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCover() {
        return cover;
    }
}
