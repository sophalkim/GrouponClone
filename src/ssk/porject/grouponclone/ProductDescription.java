package ssk.porject.grouponclone;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import ssk.project.efi_demo_app.ruby_on_rails_json_parser_fragment.RemoteData;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class ProductDescription extends Activity implements View.OnClickListener {
	LinearLayout linearLayout;
	ImageButton imageButton;
	ImageButton imageButton2;
	ImageButton imageButton3;
	DisplayImageOptions options;
	ImageLoadingListener animateFirstListener;
	String[] imageUrls = Constants.IMAGES;
	Random r = new Random();
	Button productBuyButton;
	TextView productDescriptionText;
	List<String> list;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_description_layout);
		setImager();
		setImageButtons();
		setProductBuyButton();
		setProductDescriptionText();
	}

	@Override
	public void onClick(View v) {
		ProductImageFragment winDialog = new ProductImageFragment();
        winDialog.show(getFragmentManager(), null);
	}
	
	public void setImager() {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
		.threadPriority(Thread.NORM_PRIORITY - 2)
		.denyCacheImageMultipleSizesInMemory()
		.diskCacheFileNameGenerator(new Md5FileNameGenerator())
		.diskCacheSize(50 * 1024 * 1024) // 50 Mb
		.tasksProcessingOrder(QueueProcessingType.LIFO)
		.build();
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
		
		animateFirstListener = new AnimateFirstDisplayListener();
	}
	
	public void setImageButtons() {
		imageButton = new ImageButton(this);
		imageButton2 = new ImageButton(this);
		imageButton3 = new ImageButton(this);
		ImageLoader.getInstance().displayImage(imageUrls[r.nextInt(imageUrls.length - 1)], imageButton, options, animateFirstListener);
		ImageLoader.getInstance().displayImage(imageUrls[r.nextInt(imageUrls.length - 1)], imageButton2, options, animateFirstListener);
		ImageLoader.getInstance().displayImage(imageUrls[r.nextInt(imageUrls.length - 1)], imageButton3, options, animateFirstListener);
		imageButton.setScaleType(ImageView.ScaleType.FIT_XY);
		imageButton2.setScaleType(ImageView.ScaleType.FIT_XY);
		imageButton3.setScaleType(ImageView.ScaleType.FIT_XY);
		imageButton.setOnClickListener(this);
		imageButton2.setOnClickListener(this);
		imageButton3.setOnClickListener(this);
		linearLayout = (LinearLayout) findViewById(R.id.gallery);
		linearLayout.addView(imageButton);
		linearLayout.addView(imageButton2);
		linearLayout.addView(imageButton3);
	}
	
	public void setProductBuyButton() {
		productBuyButton = (Button) findViewById(R.id.product_buy_button);
		productBuyButton.setOnClickListener(this);
	}
	
	public void setProductDescriptionText() {
		productDescriptionText = (TextView) findViewById(R.id.product_description_text);
		productDescriptionText.setText("loading");
		new JsonLoader().execute("https://groupon-api.herokuapp.com/products.json");
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
		}
		
		@Override
		protected List<String> doInBackground(String... params) {
			String raw=RemoteData.readContents(params[0]);
			List<String> list=new ArrayList<String>();
	        try {
	            	JSONArray data = new JSONArray(raw);
	            for (int i=0; i < data.length(); i++){
	                JSONObject cur= data.getJSONObject(i);
	                String description = "";
	                description =cur.optString("description");
	                list.add(description);
	            }
	        }catch(Exception e){
	            Log.e("fetchPosts()",e.toString());
	        }
	        return list;
		}

		@Override
		protected void onPostExecute(List<String> results) {
			list = results;
			productDescriptionText.setText(list.get(0));
		}
    }
}
