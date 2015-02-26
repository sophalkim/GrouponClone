package ssk.porject.grouponclone;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

public class SearchActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_activity_layout);
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
}
