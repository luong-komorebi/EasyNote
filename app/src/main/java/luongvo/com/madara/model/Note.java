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

public class Note {
    private String name;
    private String id;
    private String content;

    private Calendar created;
    private Calendar updated;
    private Calendar deleted;

    List<String> tags;

    public Note(String name, String content, List<String> tags) {
        id = UUID.randomUUID().toString();

        created = Calendar.getInstance();
        updated = created;
        deleted = null;

        update(name, content, tags);
    }

    public Note(String id, DB db) {
        try {
            this.id = id;

            name = db.get(id);
            content = db.get(id + DBSchema.NOTE_CONTENT);
            created = db.get(id + DBSchema.NOTE_CREATED, Calendar.class);
            updated = db.get(id + DBSchema.NOTE_UPDATED, Calendar.class);

            if (db.exists(id + DBSchema.NOTE_DELETED))
                deleted = db.get(id + DBSchema.NOTE_DELETED, Calendar.class);
            else
                deleted = null;

            if (db.exists(id + DBSchema.NOTE_TAGS)) {
                String[] tmp = db.getArray(id + DBSchema.NOTE_TAGS, String.class);
                tags = new ArrayList<>(Arrays.asList(tmp));
            }
            else
                tags = null;
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public void write(DB db) {
        try {
            db.put(id, name);
            db.put(id + DBSchema.NOTE_CONTENT, content);
            db.put(id + DBSchema.NOTE_CREATED, created);
            db.put(id + DBSchema.NOTE_UPDATED, updated);

            if (deleted != null)
                db.put(id + DBSchema.NOTE_DELETED, deleted);

            if (tags != null)
                db.put(id + DBSchema.NOTE_TAGS, tags.toArray(new String[tags.size()]));
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public void update(String name, String content, List<String> tags) {
        this.name = name;
        this.content = content;
        this.tags = tags;

        updated = Calendar.getInstance();
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
}
