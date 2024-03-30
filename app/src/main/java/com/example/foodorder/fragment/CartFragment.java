package com.example.foodorder.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodorder.Constants.Frag;
import com.example.foodorder.activity.MainActivity;
import com.example.foodorder.databinding.FragmentCartBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {
    FragmentCartBinding cartBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        InitToolbar();
    }

    private void InitToolbar(){
        if(getActivity()!=null){
            ((MainActivity)getActivity()).setToolBar(Frag.CART,"Food");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        cartBinding=FragmentCartBinding.inflate(inflater,container,false);
        return cartBinding.getRoot();
    }
}