package luongvo.com.madara.model;

import java.util.Random;

import luongvo.com.madara.R;
import luongvo.com.madara.utils.Constants;

/**
 * Created by VVLv on 11/26/2017.
 */

public class QuickNote {
    static private final int min = Constants.minTxtQuickNoteContentMaxLines;
    static private final int max = Constants.maxTxtQuickNoteContentMaxLines;
    private String title;
    private String content;

    // Color Id for CardView background
    private int colorId;

    // Number of lines of content to be showed
    private int maxLines;

    private QuickNote() {
        this.colorId = R.color.white;
        this.maxLines = new Random().nextInt((max - min) + 1) + min;
    }

    public QuickNote(String title, String content) {
        this();
        this.title = title;
        this.content = content;
    }

    public QuickNote(String title, String content, int colorId) {
        this(title, content);
        this.colorId = colorId;
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

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public int getMaxLines() {
        return maxLines;
    }

    public void setMaxLines(int maxLines) {
        this.maxLines = maxLines;
    }
}
