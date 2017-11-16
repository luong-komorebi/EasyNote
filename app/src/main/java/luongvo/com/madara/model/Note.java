package luongvo.com.madara.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Thanh on 11/16/2017.
 */

public class Note {
    private int noteID;
    String timeStamp;
    String content;
    String snippet;

    public Note(){

    }

    public Note(int noteID, String content){
        this.noteID = noteID;
        this.content = content;
        /*
         * timeStamp is extracted date from Calendar
         */
        Date date =  Calendar.getInstance().getTime();
        String formattedDate = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(date);
        this.timeStamp = formattedDate;
    }

    public String getSnippet(int length){
        int contentLength = this.content.length();
        length = ( length < contentLength ) ? ( length ) : ( contentLength );
        this.snippet = content.substring(0, length) + "..." ;

        return this.snippet;
    }


    public int getNoteID(){ return noteID; }

    public void setNoteID(int id){ this.noteID = id; }

    public String getContent(){ return content; }

    public void setContent(String content){ this.content = content; }

    public String getTimeStamp(){ return this.timeStamp; }

}
