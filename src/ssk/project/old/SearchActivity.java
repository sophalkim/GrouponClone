package ssk.project.old;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import ssk.porject.grouponclone.R;
import ssk.project.efi_demo_app.ruby_on_rails_json_parser_fragment.RemoteData;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

public class SearchActivity extends Activity {

	ListView lv;
	ArrayAdapter<String> adapter;
	ArrayList<String> productNameList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_activity_layout);
		lv = (ListView) findViewById(R.id.listViewSearch);
		new JsonLoader().execute("https://sophalkim.herokuapp.com/users.json");
		if (productNameList == null) {
			productNameList = new ArrayList<String>();
			productNameList.add("loading");
			productNameList.add("loading");
			productNameList.add("loading");
		}
		Log.i("onCreate: ", productNameList.toString());
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, productNameList);
		lv.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.search, menu);
//		MenuItem item = menu.add("Search");
//		item.setIcon(R.drawable.icon_search);
//		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//		SearchView searchView = new SearchView(this);
//		searchView.setFocusable(true);
//	    searchView.setIconified(false);
//	    searchView.requestFocusFromTouch();
//	    item.setActionView(searchView);
		// Get the SearchView and set the searchable configuration
	    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
	    // Assumes current activity is the searchable activity
	    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	    searchView.setFocusable(true);
	    searchView.setIconifiedByDefault(false);
	    searchView.setIconified(false);
	    searchView.requestFocus();
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void getQuery(Intent intent) {
		intent = getIntent();
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			searchDatabase(query);
		}
	}
	
	public void searchDatabase(String query) {
		
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
