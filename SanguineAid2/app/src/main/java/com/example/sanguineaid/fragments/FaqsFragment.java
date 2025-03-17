package com.example.sanguineaid.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sanguineaid.R;

public class FaqsFragment extends Fragment {

    private Button btnQuestion1, btnQuestion2, btnQuestion3, btnQuestion4, btnQuestion5;
    private Button btnQuestion6, btnQuestion7, btnQuestion8, btnQuestion9, btnQuestion10;
    private TextView answer1, answer2, answer3, answer4, answer5;
    private TextView answer6, answer7, answer8, answer9, answer10;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_faqs, container, false);

        btnQuestion1 = view.findViewById(R.id.btn_question_1);
        btnQuestion2 = view.findViewById(R.id.btn_question_2);
        btnQuestion3 = view.findViewById(R.id.btn_question_3);
        btnQuestion4 = view.findViewById(R.id.btn_question_4);
        btnQuestion5 = view.findViewById(R.id.btn_question_5);
        btnQuestion6 = view.findViewById(R.id.btn_question_6);
        btnQuestion7 = view.findViewById(R.id.btn_question_7);
        btnQuestion8 = view.findViewById(R.id.btn_question_8);
        btnQuestion9 = view.findViewById(R.id.btn_question_9);
        btnQuestion10 = view.findViewById(R.id.btn_question_10);

        answer1 = view.findViewById(R.id.answer_1);
        answer2 = view.findViewById(R.id.answer_2);
        answer3 = view.findViewById(R.id.answer_3);
        answer4 = view.findViewById(R.id.answer_4);
        answer5 = view.findViewById(R.id.answer_5);
        answer6 = view.findViewById(R.id.answer_6);
        answer7 = view.findViewById(R.id.answer_7);
        answer8 = view.findViewById(R.id.answer_8);
        answer9 = view.findViewById(R.id.answer_9);
        answer10 = view.findViewById(R.id.answer_10);

        btnQuestion1.setOnClickListener(v -> toggleVisibility(answer1));
        btnQuestion2.setOnClickListener(v -> toggleVisibility(answer2));
        btnQuestion3.setOnClickListener(v -> toggleVisibility(answer3));
        btnQuestion4.setOnClickListener(v -> toggleVisibility(answer4));
        btnQuestion5.setOnClickListener(v -> toggleVisibility(answer5));
        btnQuestion6.setOnClickListener(v -> toggleVisibility(answer6));
        btnQuestion7.setOnClickListener(v -> toggleVisibility(answer7));
        btnQuestion8.setOnClickListener(v -> toggleVisibility(answer8));
        btnQuestion9.setOnClickListener(v -> toggleVisibility(answer9));
        btnQuestion10.setOnClickListener(v -> toggleVisibility(answer10));

        return view;
    }

    private void toggleVisibility(TextView answer) {
        if (answer.getVisibility() == View.GONE) {
            answer.setVisibility(View.VISIBLE);
        } else {
            answer.setVisibility(View.GONE);
        }
    }
}
