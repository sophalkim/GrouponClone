package ssk.porject.grouponclone;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ProductDescription extends Activity {
	LinearLayout linearLayout;
	ImageButton imageButton;
	ImageButton imageButton2;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_description_layout);

		imageButton = new ImageButton(this);
		imageButton2 = new ImageButton(this);
		imageButton.setImageResource(R.drawable.cat_food);
		imageButton2.setImageResource(R.drawable.view_pager_background);
		imageButton.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		imageButton2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		imageButton2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ProductImageFragment winDialog = new ProductImageFragment();
		        winDialog.show(getFragmentManager(), null);
			}
		});
		linearLayout = (LinearLayout) findViewById(R.id.gallery);
		linearLayout.addView(imageButton);
		linearLayout.addView(imageButton2);
		
		
	}
}
