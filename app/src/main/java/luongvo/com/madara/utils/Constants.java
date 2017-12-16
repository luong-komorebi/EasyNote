package luongvo.com.madara.utils;

/**
 * Created by VVLv on 11/15/2017.
 * This class contains all constants used in project.
 */

public class Constants {
    // Name of SharedPreferences file on device's storage
    public static final String sPFileName = "madara";
    // Name of preference: Lock Pattern defined by user to unlock app
    public static final String sPLockPattern = "sPLockPattern";
    public static final int maxTxtNotebookTitleLength = 25;
    // Number of columns of QuickNotes StaggeredGridLayoutManager
    public static final int staggeredGridSpanCount = 2;
    // Max & Min number of lines of txtQuickNoteContent
    public static final int minTxtQuickNoteContentMaxLines = 5;
    public static final int maxTxtQuickNoteContentMaxLines = 10;

    public static final String intentQuickNoteId = "intentQuickNoteId";
}
