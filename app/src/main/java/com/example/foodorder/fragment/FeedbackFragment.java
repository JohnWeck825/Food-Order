package com.example.foodorder.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.foodorder.Constants.Frag;
import com.example.foodorder.Model.Feedback;
import com.example.foodorder.R;
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
import java.util.Objects;

import pl.droidsonroids.gif.GifImageView;

public class FeedbackFragment extends Fragment {
    private FragmentFeedbackBinding feedbackBinding;
    private String starRating;
    FirebaseAuth mAuth;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        feedbackBinding = FragmentFeedbackBinding.inflate(inflater, container, false);
        feedbackBinding.tvSendFeedback.setOnClickListener(v -> onClickSendFeedback());
        feedbackBinding.tvRateUs.setOnClickListener(v -> onClickRateUs());
        mAuth = FirebaseAuth.getInstance();
        return feedbackBinding.getRoot();
    }

//    @Override
//    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//        if (rating <= 1) {
//            gifImageView.setImageResource(R.drawable.emotion_1);
//        } else if (rating <= 2) {
//            gifImageView.setImageResource(R.drawable.emotion_2);
//        } else if (rating <= 3) {
//            gifImageView.setImageResource(R.drawable.emotion_3);
//        } else if (rating <= 4) {
//            gifImageView.setImageResource(R.drawable.emotion_4);
//        } else if (rating <= 5) {
//            gifImageView.setImageResource(R.drawable.emotion_5);
//        } else if (rating <= 6) {
//            gifImageView.setImageResource(R.drawable.emotion_6);
//        }
//        starRating = (int) rating + " star";
//
//        animateGifImage(gifImageView);
//    }

    public String getStarRating() {
        return starRating;
    }

    private void animateGifImage(GifImageView ratingGifImage) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1f, 0, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(200);
        ratingGifImage.startAnimation(scaleAnimation);
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

    private void onClickRateUs() {
        if (getActivity() == null) {
            return;
        }
        Dialog dialog = new Dialog(getActivity());
        View viewDialog = getLayoutInflater().inflate(R.layout.rating_star, null);
        dialog.setContentView(viewDialog);

        GifImageView gifImageView = viewDialog.findViewById(R.id.ratingImg);
        RatingBar ratingBar = viewDialog.findViewById(R.id.ratingBar);
        TextView SendRatingStar = viewDialog.findViewById(R.id.tv_send_rating_star);

//        dialog.setContentView(R.layout.rating_star);
//        GifImageView gifImageView = dialog.findViewById(R.id.ratingImg);
//        RatingBar ratingBar = dialog.findViewById(R.id.ratingBar);
//        TextView SendRatingStar = dialog.findViewById(R.id.tv_send_rating_star);

        starRating = "0 star";
//        ratingBar.setOnRatingBarChangeListener(this);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating <= 1) {
                    gifImageView.setImageResource(R.drawable.emotion_1);
                } else if (rating <= 2) {
                    gifImageView.setImageResource(R.drawable.emotion_2);
                } else if (rating <= 3) {
                    gifImageView.setImageResource(R.drawable.emotion_3);
                } else if (rating <= 4) {
                    gifImageView.setImageResource(R.drawable.emotion_4);
                } else if (rating <= 5) {
                    gifImageView.setImageResource(R.drawable.emotion_5);
                } else if (rating <= 6) {
                    gifImageView.setImageResource(R.drawable.emotion_6);
                }
                starRating = (int) rating + " star";
//                feedbackBinding.testStar.setText(starRating);
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference("feedback").child("rating");
                SendRatingStar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        if (starRating.equals("0 star")) {
//                            ContactFunction.showToastMessage(getActivity(), "Vui lòng chon số sao (>=1)");
//                        } else {
                        databaseReference.push().setValue(starRating).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                ratingBar.setRating(3);
                            }
                        });
                        ContactFunction.showToastMessage(getActivity(), "Cảm ơn bạn đã đánh giá");
//                        }
                    }
                });
                animateGifImage(gifImageView);
            }
        });

        Objects.requireNonNull(dialog.getWindow()).setLayout(1000, 1140);
        dialog.show();
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