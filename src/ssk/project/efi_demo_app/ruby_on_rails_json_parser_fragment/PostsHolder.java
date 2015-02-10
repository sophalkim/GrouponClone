package ssk.project.efi_demo_app.ruby_on_rails_json_parser_fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;
 
/**
 * This is the class that creates Post objects out of the Reddit
 * API, and maintains a list of these posts for other classes
 * to use.
 * 
 * @author Hathy
 */
public class PostsHolder {    
         
    /**
     * We will be fetching JSON data from the API.
     */
    private final String URL_TEMPLATE= "https://sophalkim.herokuapp.com/users.json";
     
    String subreddit;
    String url;
    String after;
     
    public PostsHolder(String sr){
        subreddit=sr;    
        after="";
        url = URL_TEMPLATE;
//        generateURL();
    }
     
    /**
     * Generates the actual URL from the template based on the
     * subreddit name and the 'after' property.
     */
    private void generateURL(){
        url=URL_TEMPLATE.replace("SUBREDDIT_NAME", subreddit);
        url=url.replace("AFTER", after);
    }
     
    /**
     * Returns a list of Post objects after fetching data from
     * Reddit using the JSON API.
     * 
     * @return
     */
    public List<Post> fetchPosts(){
        String raw=RemoteData.readContents(url);
        List<Post> list=new ArrayList<Post>();
        try {
            	JSONArray data = new JSONArray(raw);
            for (int i=0; i < data.length(); i++){
                JSONObject cur= data.getJSONObject(i);
                Post p=new Post();
                p.title=cur.optString("name");
                p.url=cur.optString("email");
                if(p.title!=null)
                    list.add(p);
            }
        }catch(Exception e){
            Log.e("fetchPosts()",e.toString());
        }
        return list;
    }
     
    /**
     * This is to fetch the next set of posts
     * using the 'after' property
     * @return
     */
    List<Post> fetchMorePosts(){
        generateURL();
        return fetchPosts();
    }
}
