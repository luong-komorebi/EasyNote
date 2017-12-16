package luongvo.com.madara.model;

import com.snappydb.DB;
import com.snappydb.SnappydbException;

import java.util.Random;
import java.util.UUID;

import luongvo.com.madara.R;
import luongvo.com.madara.database.DBSchema;
import luongvo.com.madara.utils.Constants;

/**
 * Created by VVLv on 11/26/2017.
 */

public class QuickNote {
    static private final int min = Constants.minTxtQuickNoteContentMaxLines;
    static private final int max = Constants.maxTxtQuickNoteContentMaxLines;
    private String title;
    private String content;
    private String id;

    // Color Id for CardView background
    private String color;

    // Number of lines of content to be showed
    private int maxLines;

    public QuickNote() {
        id = UUID.randomUUID().toString();

        this.color = "white";
        this.maxLines = new Random().nextInt((max - min) + 1) + min;
    }

    public QuickNote(String title, String content) {
        this();
        this.title = title;
        this.content = content;
    }

    public QuickNote(String title, String content, String color) {
        this(title, content);
        this.color = color;
    }

    public QuickNote(String id, DB db) {
        try {
            this.id = id;
            this.title = db.get(DBSchema.QUICKNOTE_SEARCH + id);
            this.content = db.get(DBSchema.QUICKNOTE_CONTENT + id);
            this.color = db.get(DBSchema.QUICKNOTE_COLOR + id);
            this.maxLines = new Random().nextInt((max - min) + 1) + min;
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public void write(DB db) {
        try {
            db.put(DBSchema.QUICKNOTE_SEARCH + id, title);
            db.put(DBSchema.QUICKNOTE_CONTENT + id, content);
            db.put(DBSchema.QUICKNOTE_COLOR + id, color);

        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getMaxLines() {
        return maxLines;
    }

    public void setMaxLines(int maxLines) {
        this.maxLines = maxLines;
    }

    public String getId() {
        return id;
    }
}