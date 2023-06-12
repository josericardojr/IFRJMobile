package com.josericardojr.quizgame;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.josericardojr.quizgame.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private QuizViewModel quizViewModel;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    Button trueButton, falseButton, cheatButton, nextButton;
    TextView questionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            quizViewModel.setCheater(result.getData().getBooleanExtra(
                                    CheatActivity.EXTRA_ANSWER_SHOWN, false));
                        }
                    }
                });


                // Get the ViewModel
                quizViewModel = new ViewModelProvider(this).get(QuizViewModel.class);



        // Get a reference to UI Views
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        cheatButton = findViewById(R.id.cheat_button);
        nextButton = findViewById(R.id.next_button);
        questionTextView = findViewById(R.id.questionTextView);

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean answerIsTrue = quizViewModel.currentQuestionAnswer();

                Intent intent = new Intent(MainActivity.this, CheatActivity.class);
                intent.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);

                activityResultLauncher.launch(intent);

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quizViewModel.moveToNext();
                updateQuestion();
            }
        });

        updateQuestion();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            blurCheatButton();
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private void blurCheatButton(){
        RenderEffect eff =
                RenderEffect.createBlurEffect(10.0f, 10.0f, Shader.TileMode.CLAMP);
        cheatButton.setRenderEffect(eff);
    }

    private void updateQuestion(){
        int questionTextResId = quizViewModel.currentQuestionText();
        questionTextView.setText(questionTextResId);
    }

    private void checkAnswer(boolean userAnswer){
        boolean correct = quizViewModel.currentQuestionAnswer();

        int messageResId = 0;

        if (quizViewModel.isCheater()) messageResId = R.string.judgment_toast;
        else if (userAnswer == correct) messageResId = R.string.correct_toast;
        else messageResId = R.string.incorrect_toast;

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }
}