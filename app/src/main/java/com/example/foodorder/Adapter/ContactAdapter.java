package com.example.foodorder.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorder.Model.Contact;
import com.example.foodorder.R;
import com.example.foodorder.databinding.ItemContactBinding;
import com.example.foodorder.function.ContactFunction;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private Context context;
    private final List<Contact> contactList;

    public ContactAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        View view = layoutInflater.inflate(R.layout.item_contact, parent, false);
        ItemContactBinding itemContactBinding = ItemContactBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ContactViewHolder(itemContactBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        if (contact == null) {
            return;
        }

        holder.mItemContactBinding.imgContact.setImageResource(contact.getImage());
        switch (contact.getId()) {
            case Contact.FACEBOOK:
                holder.mItemContactBinding.tvContact.setText(R.string.facebook);
                break;
            case Contact.HOTLINE:
                holder.mItemContactBinding.tvContact.setText(context.getString(R.string.hotline));
                break;
            case Contact.GMAIL:
                holder.mItemContactBinding.tvContact.setText(R.string.gmail);
                break;
            case Contact.YOUTUBE:
                holder.mItemContactBinding.tvContact.setText(R.string.youtube);
                break;
            case Contact.ZALO:
                holder.mItemContactBinding.tvContact.setText(R.string.zalo);
                break;
        }

        holder.mItemContactBinding.layoutItemContact.setOnClickListener(v -> {
            switch (contact.getId()) {
                case Contact.FACEBOOK:
                    ContactFunction.onClickOpenFacebook(context);
                    break;
                case Contact.HOTLINE:
                    ContactFunction.onClickOpenHotline(context);
                    break;
                case Contact.GMAIL:
                    ContactFunction.onClickOpenGmail(context);
                    break;
                case Contact.YOUTUBE:
                    ContactFunction.onClickOpenYoutube(context);
                    break;
                case Contact.ZALO:
                    ContactFunction.onClickOpenZalo(context);
                    break;
            }
        });

    }

    @Override
    public int getItemCount() {
        return null == contactList ? 0 : contactList.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        private final ItemContactBinding mItemContactBinding;

        public ContactViewHolder(ItemContactBinding itemContactBinding) {
            super(itemContactBinding.getRoot());
            this.mItemContactBinding = itemContactBinding;
        }
    }
}
