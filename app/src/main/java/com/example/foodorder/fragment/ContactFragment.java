package com.example.foodorder.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.foodorder.Adapter.ContactAdapter;
import com.example.foodorder.Constants.Frag;
import com.example.foodorder.Model.Contact;
import com.example.foodorder.R;
import com.example.foodorder.activity.MainActivity;
import com.example.foodorder.databinding.FragmentContactBinding;

import java.util.ArrayList;
import java.util.List;


public class ContactFragment extends Fragment {
    private FragmentContactBinding mFragmentContactBinding;
    private ContactAdapter mContactAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFragmentContactBinding = FragmentContactBinding.inflate(inflater, container, false);

        setUpUI();
        return mFragmentContactBinding.getRoot();
    }

    private void setUpUI() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        mFragmentContactBinding.rcvContact.setLayoutManager(gridLayoutManager);
        mContactAdapter = new ContactAdapter(getActivity(), getListContact());
        mFragmentContactBinding.rcvContact.setAdapter(mContactAdapter);
    }

    public List<Contact> getListContact() {
        List<Contact> contactArrayList = new ArrayList<>();
        contactArrayList.add(new Contact(Contact.FACEBOOK, R.drawable.ic_facebook));
        contactArrayList.add(new Contact(Contact.HOTLINE, R.drawable.ic_hotline));
        contactArrayList.add(new Contact(Contact.GMAIL, R.drawable.ic_gmail_new_logo));
        contactArrayList.add(new Contact(Contact.YOUTUBE, R.drawable.ic_youtube));
        contactArrayList.add(new Contact(Contact.ZALO, R.drawable.ic_zalo));
        return contactArrayList;
    }

    @Override
    public void onResume() {
        super.onResume();
        initToolbar();
    }

    private void initToolbar() {
        if (getActivity() != null) {
            ((MainActivity) getActivity()).setToolBar(Frag.CONTACT, "Liên hệ");
        }
    }

}