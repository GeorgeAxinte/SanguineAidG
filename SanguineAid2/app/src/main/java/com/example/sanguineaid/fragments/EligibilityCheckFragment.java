package com.example.sanguineaid.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.sanguineaid.R;

public class EligibilityCheckFragment extends Fragment {

    private RadioGroup optionsGroup;
    private TextView questionTextView, resultTextView, explanationTextView;
    private Button nextButton;
    private int score = 0;
    private int currentQuestionIndex = 0;

    private String[] questions = {
            "Are you at least 18 years old?",
            "Do you weigh at least 50 kg?",
            "Are you in good health today?",
            "Do you have any chronic illnesses or conditions (e.g., diabetes, heart disease)?",
            "Have you donated blood in the past 3 months?",
            "Are you currently taking any medications that might prevent blood donation?",
            "Do you have any recent tattoos or piercings (within the last 6 months)?"
    };

    private String[][] options = {
            {"Yes, I am at least 18", "No, I am under 18"},
            {"Yes, I weigh at least 50 kg", "No, I weigh less than 50 kg"},
            {"Yes, I feel perfectly fine today", "No, I am feeling unwell"},
            {"No, I do not have any chronic illnesses", "Yes, I have chronic illnesses or conditions"},
            {"No, I haven't donated in the last 3 months", "Yes, I have donated in the last 3 months"},
            {"No, I am not taking any medications", "Yes, I am currently taking medications"},
            {"No, I have not had any tattoos or piercings", "Yes, I have had tattoos or piercings"}
    };

    private boolean[] answers = new boolean[7];

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eligibility_check, container, false);

        optionsGroup = view.findViewById(R.id.optionsGroup);
        questionTextView = view.findViewById(R.id.questionTextView);
        resultTextView = view.findViewById(R.id.resultTextView);
        explanationTextView = view.findViewById(R.id.explanationTextView);
        nextButton = view.findViewById(R.id.nextButton);
        LinearLayout resultContainer = view.findViewById(R.id.resultContainer);

        nextButton.setOnClickListener(v -> {
            RadioButton selectedOption = view.findViewById(optionsGroup.getCheckedRadioButtonId());
            if (selectedOption != null) {
                answers[currentQuestionIndex] = selectedOption.getText().toString().startsWith("Yes");
            }

            currentQuestionIndex++;
            if (currentQuestionIndex < questions.length) {
                showQuestion(currentQuestionIndex);
            } else {
                showEligibilityResult();
            }
        });

        showQuestion(currentQuestionIndex);

        return view;
    }

    private void showQuestion(int questionIndex) {
        questionTextView.setText(questions[questionIndex]);

        optionsGroup.removeAllViews();

        for (String option : options[questionIndex]) {
            RadioButton optionButton = new RadioButton(getContext());
            optionButton.setText(option);
            optionsGroup.addView(optionButton);
        }

        nextButton.setVisibility(View.VISIBLE);
    }

    private void showEligibilityResult() {
        String result;
        String explanation;

        boolean isEligible = true;
        for (boolean answer : answers) {
            if (!answer) {
                isEligible = false;
                break;
            }
        }

        if (isEligible) {
            result = "You are eligible to donate blood!";
            explanation = "Thank you for being eligible to donate blood. Please proceed to schedule your appointment.";
        } else {
            result = "You are not eligible to donate blood.";
            explanation = "Based on your answers, you may not meet the criteria for blood donation. " +
                    "This could be due to age, health conditions, recent donations, or other medical reasons. " +
                    "Please consult a doctor for more information.";
        }

        resultTextView.setText(result);
        explanationTextView.setText(explanation);

        resultTextView.setVisibility(View.VISIBLE);
        explanationTextView.setVisibility(View.VISIBLE);

        optionsGroup.setVisibility(View.GONE);
        questionTextView.setVisibility(View.GONE);
        nextButton.setVisibility(View.GONE); 
    }
}
