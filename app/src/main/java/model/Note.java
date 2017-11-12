package model;


import android.graphics.Color;

import java.io.Serializable;

@SuppressWarnings("serial") // to hide compier's warning - it's annoying

public class Note implements Serializable {
    private int ID;
    private String author;
    private String title;
    private String snippet;
    private String content;
    private int timeStamp;
    private CharSequence picture;
    private int color = Color.MAGENTA;
    private boolean read = false;

    public Note(int ID, String author, String title, String snippet, String content, int timeStamp){
        this.ID = ID;
        this.author = author;
        this.title = title;
        this.snippet = snippet;
        this.content = content;
        this.timeStamp = timeStamp;
    }

    public int getID(){ return ID;}

    public String getContent(){ return content;}

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getSnippet() {
        return snippet;
    }

    public int getTimeStamp() { return timeStamp; }

    public CharSequence getPicture() { return null; }

    public int getColor() { return color; }

    public boolean isRead() { return read; }

    public void setRead(boolean read) { this.read = read; }
}
