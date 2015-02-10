package ssk.project.studiodemo.fragmentWebImages;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

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

public class Custom_Image_ArrayAdapter extends ArrayAdapter<String> {
	private final Context mContext;
	private final String[] values;
	public static Bitmap bitmap1 = null;

	public Custom_Image_ArrayAdapter(Context context, String[] values) {
		super(context, R.layout.custom_row, values);
		mContext = context;
		this.values = values;
		getImages();
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.custom_row, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.image_view_1);
		textView.setText(values[position]);
		if (position == 0) {
			imageView.setImageBitmap(bitmap1);
		} else {
			if (position == 1) {
				imageView.setImageResource(R.drawable.jacket);
			} else {
				imageView.setImageResource(R.drawable.cat_food);
			}
			
		}
		return rowView;
	}
	
	public void getImages() {
		GetBitmapFromURL bitmapTask = new GetBitmapFromURL();
		try {
			bitmap1 = bitmapTask.execute("http://sokim209.appspot.com/images/flower2.jpg").get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	
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

}


