package ssk.porject.grouponclone;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import ssk.project.efi_demo_app.ruby_on_rails_json_parser_fragment.RemoteData;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class SearchActivity2 extends Activity {

    private ListView lv;
    ArrayAdapter<String> adapter;
    EditText inputSearch;
    ArrayList<String> productNameList;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity_layout2);
        new JsonLoader().execute("https://sophalkim.herokuapp.com/users.json");
        if (productNameList == null) {
			productNameList = new ArrayList<String>();
			productNameList.add("loading");
			productNameList.add("loading");
			productNameList.add("loading");
		}
        lv = (ListView) findViewById(R.id.searchListView);
        inputSearch = (EditText) findViewById(R.id.searchEditText);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, productNameList);
        lv.setAdapter(adapter);  
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                adapter.getFilter().filter(cs);   
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                    int arg3) { 
            }
             
            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });
         
    }
    
    private class JsonLoader extends AsyncTask<String, Integer, ArrayList<String>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		
		@Override
		protected ArrayList<String> doInBackground(String... params) {
			String raw=RemoteData.readContents(params[0]);
			ArrayList<String> list=new ArrayList<String>();
	        try {
	            	JSONArray data = new JSONArray(raw);
	            for (int i=0; i < data.length(); i++){
	                JSONObject cur= data.getJSONObject(i);
	                String name = "";
	                name =cur.optString("name");
	                list.add(name);
	            }
	        }catch(Exception e){
	            Log.e("fetchPosts()",e.toString());
	        }
	        return list;
		}

		@Override
		protected void onPostExecute(ArrayList<String> results) {
			Log.i("Task results:", results.toString());
			productNameList = results;
			adapter.clear();
			adapter.addAll(productNameList);
			adapter.notifyDataSetChanged();
		}
    }
     
}
