package luongvo.com.madara.Model;



public class Note {
     private int ID;
     private String author;
     private String title;
     private String content;
     private String imageURL;

     public Note(int ID, String author, String title, String content, String imageURL){
         this.ID = ID;
         this.author = author;
         this.title = title;
         this.content = content;
         this.imageURL = imageURL;
     }
    public int getID(){ return ID;}
    public String getAuthor() { return author; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getImageURL() { return imageURL; }

}
