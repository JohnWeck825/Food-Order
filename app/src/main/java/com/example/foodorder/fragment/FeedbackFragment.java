package com.example.foodorder.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.foodorder.Constants.Frag;
import com.example.foodorder.Model.Feedback;
import com.example.foodorder.activity.MainActivity;
import com.example.foodorder.databinding.FragmentFeedbackBinding;
import com.example.foodorder.function.ContactFunction;
import com.example.foodorder.utils.StringUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;

public class FeedbackFragment extends Fragment {
    private FragmentFeedbackBinding feedbackBinding;

    FirebaseAuth mAuth;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        feedbackBinding = FragmentFeedbackBinding.inflate(inflater, container, false);
        feedbackBinding.tvSendFeedback.setOnClickListener(v -> onClickSendFeedback());
        mAuth = FirebaseAuth.getInstance();
        return feedbackBinding.getRoot();
    }

    private void onClickSendFeedback() {
        if (getActivity() == null) {
            return;
        }

        MainActivity mainActivity = (MainActivity) getActivity();

        String fbName = feedbackBinding.feedbackName.getText().toString();
        String fbPhone = feedbackBinding.feedbackPhone.getText().toString();
        String fbEmail = feedbackBinding.feedbackEmail.getText().toString();
        String fbComment = feedbackBinding.feedbackComment.getText().toString();

        if (StringUtils.isEmpty(fbName)) {
            ContactFunction.showToastMessage(mainActivity, "Vui lòng nhập Họ và Tên của bạn");
        } else if (StringUtils.isEmpty(fbComment)) {
            ContactFunction.showToastMessage(mainActivity, "Vui lòng nhập phản hồi của bạn");
        } else {
            LocalDate currentDate = LocalDate.now();
            int day = currentDate.getDayOfMonth();
            int month = currentDate.getMonthValue();
            int year = currentDate.getYear();
            String createDate = String.valueOf(day + "-" + month + "-" + year);
//            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//            String uid = firebaseUser.getUid();
//            FirebaseDatabase firebaseDatabase1 = FirebaseDatabase.getInstance();
//            DatabaseReference reference = firebaseDatabase1.getReference("Account").child(uid);
//            reference.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    if (snapshot.exists()) {
//                        String email = snapshot.child("email").getValue(String.class);
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
            Feedback feedback = new Feedback(fbName, fbPhone, fbEmail, fbComment, createDate);
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference("feedback");
            databaseReference.push().setValue(feedback).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    ContactFunction.showToastMessage(mainActivity, "ok");
                    feedbackBinding.feedbackName.setText(null);
                    feedbackBinding.feedbackPhone.setText(null);
                    feedbackBinding.feedbackEmail.setText(null);
                    feedbackBinding.feedbackComment.setText(null);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initToolbar();
    }

    private void initToolbar() {
        if (getActivity() != null) {
            ((MainActivity) getActivity()).setToolBar(Frag.FEEDBACK, "Phản hồi");
        }
    }
}