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

public class Notebook {
    private String name;
    private String id;
    private String coverImg;
    private String password;

    private Calendar created;
    private Calendar deleted;

    private List<String> noteIds;

    public Notebook(String name, String coverImg, String password, List<String> noteIds) {
        this.name = name;
        this.coverImg = coverImg;
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
            coverImg = db.get(id + "_cover");
            created = db.get(id + "_created", Calendar.class);

            if (db.exists(id + "_deleted"))
                deleted = db.get(id + "_deleted", Calendar.class);
            else
                deleted = null;

            if (db.exists(id + "_pass"))
                password = db.get(id + "_pass");
            else
                password = "";

            if (db.exists(id + "_notes")) {
                String[] tmp = db.getArray(id + "_notes", String.class);
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
            db.put(id + "_created", created);
            db.put(id + "_cover", coverImg);

            if (password != null && !password.isEmpty())
                db.put(id + "_pass", password);

            if (deleted != null)
                db.put(id + "_deleted", deleted);

            if (noteIds != null)
                db.put(id + "_notes", noteIds.toArray(new String[noteIds.size()]));

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
}
