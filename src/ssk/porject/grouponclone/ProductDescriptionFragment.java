package ssk.porject.grouponclone;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ProductDescriptionFragment extends Fragment {

	public static ProductDescriptionFragment newInstance() {
		ProductDescriptionFragment pdf = new ProductDescriptionFragment();
		return pdf;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.product_description_layout, container, false);
		return rootView;
	}
}
