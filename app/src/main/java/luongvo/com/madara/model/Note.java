package luongvo.com.madara.model;

import java.util.ArrayList;
import java.util.Date;
import luongvo.com.madara.utils.ShortDate;
/**
 * Created by Thanh on 11/16/2017.
 */

public class Note {
    private int noteID;
    private String title;
    private String content;
    private ArrayList<String> tags;
    private Date dCreated;
    private String reminderTime ="";

    public Note(){

    }

    public Note(int noteID, String title, String content){
        this.noteID = noteID;
        this.title = title;
        this.content = content;
        this.dCreated = new Date();
    }
    public boolean hasReminderDate(){
        return( reminderTime != null );
    }
    public int getNoteID(){
        return noteID;
    }

    public void setNoteID(int id){
        this.noteID = id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTimeStamp(){
        ShortDate distance = new ShortDate(dCreated);
        return distance.getMyDate();
    }

    public ArrayList<String> getTags(){
        return this.tags;
    }

    public void setTags(ArrayList<String> tags){
        this.tags = tags;
    }

    public String getReminderTime(){
        return reminderTime;
    }

    public void setReminderTime(String remidnerTime){
        this.reminderTime = remidnerTime;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }


}
