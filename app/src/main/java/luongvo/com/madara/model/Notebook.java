package luongvo.com.madara.model;

/**
 * Created by VVLv on 11/7/2017.
 */

public class Notebook {
    private int coverId;
    private String title;

    public Notebook() {

    }

    public Notebook(int coverId, String title) {
        this.coverId = coverId;
        this.title = title;
    }

    public int getCoverId() {
        return coverId;
    }

    public void setCoverId(int coverId) {
        this.coverId = coverId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
