package ssk.porject.grouponclone;

import java.io.IOException;

import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
		String url = "http://groupon-api.herokuapp.com/products.json";
		new AddProductTask().execute(name, description, price, url);
	}
	
	public class AddProductTask extends AsyncTask<String, Void, String> {
    	
		@Override
		protected String doInBackground(String... params) {
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(params[3]);
			JSONObject holder = new JSONObject();
			JSONObject product = new JSONObject();
			String response = null;
//			JSONObject json = new JSONObject();
			try {
				try {
//					json.put("success", false);
//					json.put("info", "Something went wrong. Retry!");
					product.put("name", params[0]);
					product.put("description", params[1]);
					product.put("price", params[2]);
					holder.put("product", product);
					StringEntity entity = new StringEntity(holder.toString());
					post.setEntity(entity);
					post.setHeader("Accept", "application/json");
					post.setHeader("Content-Type", "application/json");
					ResponseHandler<String> responseHandler = new BasicResponseHandler();
					response = client.execute(post, responseHandler);
					Log.i("response", response);
//					json = new JSONObject(response);
				} catch (HttpResponseException e) {
					e.printStackTrace();
					Log.e("ClientProtocol", "" + e);
				} catch (IOException e) {
					e.printStackTrace();
					Log.e("IO", "" + e);
				}
			} catch (JSONException e) {
				e.printStackTrace();
				Log.e("JSON", "" + e);
			}
			return response;
		}
		
		@Override
		protected void onPostExecute(String response) {
			if (response.contains("created_at")) {
				Toast.makeText(getActivity(), "Uploaded", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
			}
		}
    	
    }

	
}
