package com.josericardojr.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.josericardojr.quizgame.databinding.ActivityCheatBinding;

public class CheatActivity extends AppCompatActivity {

    public final static String EXTRA_ANSWER_IS_TRUE = "com.josericardojr.quizgame.answer_is_true";
    public final static String EXTRA_ANSWER_SHOWN = "com.josericardojr.quizgame.answer_shown";

    private boolean answerIsTrue = false;

    Button showAnswerButton;
    TextView answerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        // Recover UI Views
        showAnswerButton = findViewById(R.id.show_answer_button);
        answerTextView = findViewById(R.id.answer_text_view);

        answerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        showAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int answerText = answerIsTrue ? R.string.true_button : R.string.false_button;
                answerTextView.setText(answerText);

                setAnswerShowResult(true);
            }
        });
    }

    private void setAnswerShowResult(boolean isAnswerShown) {
        getIntent().putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(Activity.RESULT_OK, getIntent());
    }
}