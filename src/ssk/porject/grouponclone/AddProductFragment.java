package ssk.porject.grouponclone;

import java.util.List;

import ssk.project.efi_demo_app.ruby_on_rails_json_parser_fragment.Post;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddProductFragment extends Fragment implements View.OnClickListener {

	EditText editTextName;
	EditText editTextDescription;
	EditText editTextPrice;
	Button buttonAddProduct;
	
	public static AddProductFragment newInstance() {
		AddProductFragment fragment = new AddProductFragment();
		return fragment;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_product_layout, container, false);
        editTextName = (EditText) rootView.findViewById(R.id.editTextName);
        editTextDescription = (EditText) rootView.findViewById(R.id.editTextDescription);
        editTextPrice = (EditText) rootView.findViewById(R.id.editTextPrice);
        buttonAddProduct = (Button) rootView.findViewById(R.id.buttonAddProduct);
        buttonAddProduct.setOnClickListener(this);
        return rootView;
    }

	@Override
	public void onClick(View v) {
		String name = editTextName.getText().toString();
		String description = editTextDescription.getText().toString();
		String price = editTextPrice.getText().toString();
		new AddProductTask().execute(name, description, price);
	}
	
	public class AddProductTask extends AsyncTask<String, Void, String> {
    	
		@Override
		protected String doInBackground(String... params) {
			return "success";
		}
		
		@Override
		protected void onPostExecute(String result) {
			Toast.makeText(getActivity(), "Uploaded", Toast.LENGTH_SHORT).show();
		}
    	
    }

	
}
