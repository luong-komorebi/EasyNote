package luongvo.com.madara.data;

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
    private String html;

    private Calendar created;
    private Calendar updated;
    private Calendar deleted;

    List<String> tags;

    public Note(String name, String html, List<String> tags) {
        this.name = name;
        this.html = html;
        this.tags = tags;

        id = UUID.randomUUID().toString();

        created = Calendar.getInstance();
        updated = created;
        deleted = null;
    }

    public Note(String id, DB db) {
        try {
            this.id = id;

            name = db.get(id);
            html = db.get(id + "_html");
            created = db.get(id + "_created", Calendar.class);
            updated = db.get(id + "_updated", Calendar.class);

            if (db.exists(id + "_deleted"))
                deleted = db.get(id + "_deleted", Calendar.class);
            else
                deleted = null;

            if (db.exists(id + "_tags")) {
                String[] tmp = db.getArray(id + "_tags", String.class);
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
            db.put(id + "_html", html);
            db.put(id + "_created", created);
            db.put(id + "_updated", updated);

            if (deleted != null)
                db.put(id + "_deleted", deleted);

            if (tags != null)
                db.put(id + "_tags", tags.toArray(new String[tags.size()]));
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public void update() {
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
