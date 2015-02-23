/*******************************************************************************
 * Copyright 2011-2014 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package ssk.porject.grouponclone;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import ssk.project.efi_demo_app.ruby_on_rails_json_parser_fragment.RemoteData;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.sample.Constants;
import com.nostra13.universalimageloader.sample.fragment.AbsListViewBaseFragment;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class ImageListFragment extends AbsListViewBaseFragment implements SwipeRefreshLayout.OnRefreshListener, ListView.OnScrollListener{

	public static final int INDEX = 0;

	ViewHolder holder;
	String[] imageUrls = Constants.IMAGES;
	List<String> list;
	Random r = new Random();

	DisplayImageOptions options;
	ProgressDialog mProgressDialog;
	
	SwipeRefreshLayout swipeLayout;
	ImageAdapter adapter;
	
	// Variables for automatically loading more reddit posts when user reaches bottom of listview
    int currentItemIndex = 0;
    int totalItemCount = 0;
    int currentScrollState = 0;
    boolean isLoading = false;
	
	/*
	 * Adding to make it work with Groupon
	 */
	public static ImageListFragment newInstance() {
		ImageListFragment ilf = new ImageListFragment();
		return ilf;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity())
		.threadPriority(Thread.NORM_PRIORITY - 2)
		.denyCacheImageMultipleSizesInMemory()
		.diskCacheFileNameGenerator(new Md5FileNameGenerator())
		.diskCacheSize(50 * 1024 * 1024) // 50 Mb
		.tasksProcessingOrder(QueueProcessingType.LIFO)
		.writeDebugLogs() // Remove for release app
		.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);

		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error)
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.considerExifParams(true)
//				.displayer(new RoundedBitmapDisplayer(20))
				.build();
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.swipe_refresh_layout, container, false);
        setSwipeLayout(rootView);
        new JsonLoader().execute("https://sophalkim.herokuapp.com/users.json");
        listView = (ListView) rootView.findViewById(R.id.listview1);
        listView.setOnScrollListener(this);
        adapter = new ImageAdapter();
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				startImagePagerActivity(position);
			}
		});
		return rootView;
	}
	
	public void setSwipeLayout(View rootView) {
    	swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
		swipeLayout.setOnRefreshListener(this);
		swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright, 
	            android.R.color.holo_green_light, 
	            android.R.color.holo_orange_light, 
	            android.R.color.holo_red_light);
    }

	@Override
	public void onDestroy() {
		super.onDestroy();
		AnimateFirstDisplayListener.displayedImages.clear();
	}

	private static class ViewHolder {
		TextView text;
		ImageView image;
		Button button;
	}

	class ImageAdapter extends BaseAdapter {

		private LayoutInflater inflater;
		private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

		ImageAdapter() {
			inflater = LayoutInflater.from(getActivity());
		}

		@Override
		public int getCount() {
			if (list == null) {
				return 5;
			} else {
				return list.size() / 2;
			}
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View view = convertView;
			if (convertView == null) {
				view = inflater.inflate(R.layout.item_list_image, parent, false);
				holder = new ViewHolder();
				holder.text = (TextView) view.findViewById(R.id.text);
				holder.image = (ImageView) view.findViewById(R.id.image);
				holder.button = (Button) view.findViewById(R.id.button1);
				holder.button.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getActivity(), ProductDescription.class);
						startActivity(intent);
					}
				});
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			if (list != null) {
				holder.text.setText(list.get(r.nextInt(list.size())));
			} else {
				holder.text.setText("loading");
			}
			holder.button.setText("$" + r.nextInt(100) + ".95");
			if (holder.image.getDrawable() == null) {
				ImageLoader.getInstance().displayImage(imageUrls[r.nextInt(imageUrls.length - 1)], holder.image, options, animateFirstListener);
			}
			return view;
		}
	}

	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
	
	private class JsonLoader extends AsyncTask<String, Integer, List<String>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			swipeLayout.setRefreshing(true);
		}
		
		@Override
		protected List<String> doInBackground(String... params) {
			String raw=RemoteData.readContents(params[0]);
			List<String> list=new ArrayList<String>();
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
		protected void onPostExecute(List<String> results) {
			list = results;
			adapter.notifyDataSetChanged();
			swipeLayout.setRefreshing(false);
		}
    }
	
	@Override
	public void onRefresh() {
		if (list != null) {
			holder.text.setText(list.get(r.nextInt(list.size())));
		}
		new Handler().postDelayed(new Runnable() {
	        @Override public void run() {
	            swipeLayout.setRefreshing(false);
	            adapter.notifyDataSetChanged();
	        }
	    }, 2000);
	}

	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        currentItemIndex = firstVisibleItem + visibleItemCount;
        this.totalItemCount = totalItemCount;
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        this.currentScrollState = scrollState;
        this.isScrollCompleted();
     }

    private void isScrollCompleted() {
        if (currentItemIndex == totalItemCount && this.currentScrollState == SCROLL_STATE_IDLE) {
            if(!isLoading){
                 isLoading = true;
                 new Thread(){
                     public void run(){
                         list.addAll(list);
                         swipeLayout.setRefreshing(true);
                     }
             	}.start();
         		new Handler().postDelayed(new Runnable() {
         	        @Override public void run() {
         	            swipeLayout.setRefreshing(false);
         	            adapter.notifyDataSetChanged();
         	            isLoading = false;
         	        }
         	    }, 2000);
            }
        }
    }
}