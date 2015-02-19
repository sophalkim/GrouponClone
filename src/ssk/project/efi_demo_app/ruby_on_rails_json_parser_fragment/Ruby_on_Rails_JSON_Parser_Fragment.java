package ssk.project.efi_demo_app.ruby_on_rails_json_parser_fragment;

import java.util.ArrayList;
import java.util.List;

import ssk.porject.grouponclone.R;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Ruby_on_Rails_JSON_Parser_Fragment extends Fragment {
	ListView postsList;
    ArrayAdapter<Post> adapter;
    String subreddit;
    PostsHolder postsHolder; 
    static List<Post> posts;
    
    /** 
     * Create a new instance of this Fragment. This fragment is a listview containing
     * information from my ruby on rails website. 
     * @return Ruby on Rails Fragment
     */
    public static Fragment newInstance(){
    	Ruby_on_Rails_JSON_Parser_Fragment rf = new Ruby_on_Rails_JSON_Parser_Fragment();
    	posts = new ArrayList<Post>();
    	rf.postsHolder = new PostsHolder(rf.subreddit);
    	return rf;
    }
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.posts, container, false);
        postsList = (ListView) view.findViewById(R.id.posts_list);
        new getJSONTask().execute();
        return view;
    }
     
    /**
     * This method creates the adapter from the list of posts
     * , and assigns it to the list.
     */
    private void createAdapter(){
        // Make sure this fragment is still a part of the activity.
        if(getActivity()==null) return;
        adapter=new ArrayAdapter<Post>(getActivity() ,R.layout.post_item, posts){
            @Override
            public View getView(int position,View convertView, ViewGroup parent) {
            	if(convertView==null){
                    convertView=getActivity().getLayoutInflater().inflate(R.layout.post_item, parent, false);
                }
                TextView postTitle;
                postTitle=(TextView)convertView.findViewById(R.id.post_title);
                postTitle.setText(posts.get(position).title);
                return convertView;
            }
        };
        postsList.setAdapter(adapter);
    }
    
    public class getJSONTask extends AsyncTask<String, Void, List<Post>> {
    	
		@Override
		protected List<Post> doInBackground(String... params) {
			if(posts.size()==0){
				posts.addAll(postsHolder.fetchPosts());
			}
			return posts;
		}
		
		@Override
		protected void onPostExecute(List<Post> posts) {
			createAdapter();
		}
    	
    }
    
}
