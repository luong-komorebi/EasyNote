package luongvo.com.madara.search;

import android.util.Pair;

import com.snappydb.DB;

import java.util.ArrayList;
import java.util.List;

import luongvo.com.madara.data.Note;
import luongvo.com.madara.database.DBHelper;

/**
 * Created by duytu on 15-Nov-17.
 */

public class SearchHelper {
    private List<String> tagList;
    private List<Pair<String, String>> noteList;

    public SearchHelper(DBHelper db) {
        tagList = db.getAllTags();
        noteList = db.getAllNoteName();
    }

    // return tags that match query input
    public List<String> queryByTag(String query) {
        List<String> res = new ArrayList<>();

        for (int i = 0; i < tagList.size(); ++i) {
    	    if (tagList.get(i).contains(query)) {
    	        res.add(tagList.get(i));
            }
        }
        return res;
    }

    // return notes id that match query input
    public List<String> queryByName(String query) {
        List<String> res = new ArrayList<>();

        for (int i = 0; i < noteList.size(); ++i) {
            if (noteList.get(i).second.contains(query)) {
                res.add(noteList.get(i).first);
            }
        }
        return res;
    }

    // May consider to return both tags and notes or only one of them for SearchActivity
    // Depend on UI
    public List<Note> searchFor(String query, DB db) {
        List<Note> res = new ArrayList<>();
        List<String> notes = queryByName(query);

        for (int i = 0; i < notes.size(); ++i) {
            res.add(new Note(notes.get(i), db));
        }
        return res;
    }
}
