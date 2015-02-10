package ssk.project.efi_demo_app.ruby_on_rails_json_parser_fragment;


public class Post {
    
    String subreddit;
    public String title;
    String author;
    int points;
    int numComments;
    String permalink;
    String url;    
    String domain;
    String id;
    public String thumbnail;
     
    String getDetails(){
        String details=author
                       +" posted this and got "
                       +numComments
                       +" replies";
        return details;    
    }
     
    String getTitle(){
        return title;
    }
     
    String getScore(){
        return Integer.toString(points);
    }
    
    String getthumbnail() {
    	return thumbnail;
    }
}
