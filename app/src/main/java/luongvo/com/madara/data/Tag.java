package luongvo.com.madara.data;

import com.snappydb.DB;
import com.snappydb.SnappydbException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Lam Le Thanh The on 12/11/2017.
 */

public class Tag {
    String name;
    List<String> belongTo;

    public Tag(String name, List<String> belongTo) {
        this.name = name;
        this.belongTo = belongTo;
    }

    public Tag(String name, DB db) {
        try {
            String[] tmp = db.getArray(name, String.class);
            belongTo = new ArrayList<>(Arrays.asList(tmp));
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public void write(DB db) {
        try {
            db.put(name, belongTo.toArray(new String[belongTo.size()]));
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }
}
