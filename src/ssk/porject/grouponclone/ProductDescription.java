package ssk.porject.grouponclone;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ProductDescription extends Activity {
	LinearLayout linearLayout;
	ImageView imageView;
	ImageView imageView2;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_description_layout);

		imageView = new ImageView(this);
		imageView2 = new ImageView(this);
		imageView.setImageResource(R.drawable.cat_food);
		imageView2.setImageResource(R.drawable.view_pager_background);
		linearLayout = (LinearLayout) findViewById(R.id.gallery);
		linearLayout.addView(imageView);
		linearLayout.addView(imageView2);
	}
}
