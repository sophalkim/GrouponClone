package ssk.project.studiodemo.fragmentWebImages;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import ssk.porject.grouponclone.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class Custom_Image_ArrayAdapter extends ArrayAdapter<String> {
	private final Context mContext;
	private final String[] values;
	DisplayImageOptions options;
	String[] imageUrls = Constants.IMAGES;

	public Custom_Image_ArrayAdapter(Context context, String[] values) {
		super(context, R.layout.custom_row, values);
		mContext = context;
		this.values = values;
//		getImages();
		// Create global configuration and initialize ImageLoader with this config
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).build();
        ImageLoader.getInstance().init(config);
        options = new DisplayImageOptions.Builder()
		.showImageOnLoading(android.R.drawable.ic_menu_camera)
		.showImageForEmptyUri(android.R.drawable.ic_menu_camera)
		.showImageOnFail(android.R.drawable.ic_menu_camera)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.displayer(new RoundedBitmapDisplayer(20))
		.build();
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.custom_row, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.image_view_1);
		textView.setText(values[position]);
		ImageLoader.getInstance().displayImage(imageUrls[position], imageView);
		return rowView;
	}
	
//	public void getImages() {
//		GetBitmapFromURL bitmapTask = new GetBitmapFromURL();
//		try {
//			bitmap1 = bitmapTask.execute("http://sokim209.appspot.com/images/flower2.jpg").get();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			e.printStackTrace();
//		}
//	}
	
	
	public class GetBitmapFromURL extends AsyncTask<String, Void, Bitmap> {

		Bitmap myBitmap;
		
		@Override
		protected Bitmap doInBackground(String... params) {
			try {
	            URL url = new URL(params[0]);
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.setDoInput(true);
	            connection.connect();
	            InputStream input = connection.getInputStream();
	            myBitmap = BitmapFactory.decodeStream(input);
	            return myBitmap;
	        } catch (IOException e) {
	            e.printStackTrace();
	            return null;
	        }
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

}


