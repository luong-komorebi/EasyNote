package luongvo.com.madara.search;

import android.util.Pair;

import luongvo.com.madara.database.DBHelper;
import luongvo.com.madara.model.Note;
import luongvo.com.madara.model.Notebook;

import com.snappydb.DB;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by duytu on 15-Nov-17.
 */

public class SearchHelper {
    private List<String> tagList;
    private List<Pair<String, String>> noteList;
    private List<Pair<String, String>> notebookList;

    public SearchHelper(DBHelper db) {
        tagList = db.getAllTags();
        noteList = db.getAllNoteNames();
        notebookList = db.getAllNotebookNames();
    }

    // return tags that match query
    public List<Pair<String, String>> queryByTag(String query) {
        List<Pair<String, String>> res = new ArrayList<>();

        for (int i = 0; i < tagList.size(); ++i) {
    	    if (tagList.get(i).toLowerCase().contains(query.toLowerCase())) {
    	        res.add(new Pair<>("tag", tagList.get(i)));
            }
        }
        return res;
    }

    // return notes id that match query
    private List<Pair<String, String>> queryByName(String query) {
        List<Pair<String, String>> res = new ArrayList<>();

        for (int i = 0; i < noteList.size(); ++i) {
            if (noteList.get(i).second.toLowerCase().contains(query.toLowerCase())) {
                res.add(noteList.get(i));
            }
        }
        return res;
    }

    // returns notebooks that match query
    private List<Pair<String, String>> queryNoteBook(String query) {
        List<Pair<String, String>> res = new ArrayList<>();

        for (int i = 0; i < notebookList.size(); ++i) {
            if (notebookList.get(i).second.toLowerCase().contains(query.toLowerCase())) {
                res.add(notebookList.get(i));
            }
        }
        return res;
    }

    public List<Pair<String, String>> searchFor(String query, DB db) {
        List<Pair<String, String>> res = new ArrayList<>();

        res.addAll(queryByTag(query));
        res.addAll(queryNoteBook(query));
        res.addAll(queryByName(query));

        return res;
    }
}
