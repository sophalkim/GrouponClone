package ssk.project.studiodemo.fragmentWebImages;

import ssk.porject.grouponclone.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class WebImagesFragment extends Fragment {
     
    WebImagesFragment(){
    }    
     
    public static WebImagesFragment newInstance(){
        WebImagesFragment instance = new WebImagesFragment();        
        return instance;
    }
     
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.web_images_layout
                                , container
                                , false);
        String[] x = new String[3];
        for (int i=0; i<x.length; i++) {
        	x[i] = "" + i;
        }
        ListView lv = (ListView) v.findViewById(R.id.list_view_1);
        Custom_Image_ArrayAdapter adapter = new Custom_Image_ArrayAdapter(getActivity(), x);
        lv.setAdapter(adapter);
        return v;
    }
  
}
