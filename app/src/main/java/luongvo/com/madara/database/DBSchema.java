package luongvo.com.madara.database;

/**
 * Created by Lam Le Thanh The on 18/11/2017.
 */

public class DBSchema {
    public static final String DB_SETUP = "db_init";

    private static final String KEY_SEPARATOR = "_";

    public static final String NOTEBOOK_IDS = "notebooks";

    public static final String NOTEBOOK_COVER = KEY_SEPARATOR + "cover";
    public static final String NOTEBOOK_CREATED = KEY_SEPARATOR + "created";
    public static final String NOTEBOOK_DELETED = KEY_SEPARATOR + "deleted";
    public static final String NOTEBOOK_PASS = KEY_SEPARATOR + "pass";
    public static final String NOTEBOOK_NOTE_IDS = KEY_SEPARATOR + "notes";

    public static final String NOTE_CONTENT = KEY_SEPARATOR + "content";
    public static final String NOTE_CREATED = KEY_SEPARATOR + "created";
    public static final String NOTE_DELETED = KEY_SEPARATOR + "deleted";
    public static final String NOTE_UPDATED = KEY_SEPARATOR + "updated";
    public static final String NOTE_TAGS = KEY_SEPARATOR + "tags";

    public static final String TAG_PREFIX = "tag" + KEY_SEPARATOR;

    private static final String TRASH_PREFIX = "trash";
    public static final String TRASH_NOTES = TRASH_PREFIX + KEY_SEPARATOR + "notes";
    public static final String TRASH_NOTEBOOKS = TRASH_PREFIX + KEY_SEPARATOR + "notebooks";
}
