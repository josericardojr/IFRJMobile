package com.josericardojr.quizgame;

import androidx.lifecycle.ViewModel;

public class QuizViewModel extends ViewModel {

    private Question[] questions = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    int current_index = 0;
    private boolean isCheater = false;


    public QuizViewModel(){
    }

    public boolean currentQuestionAnswer(){
        return questions[current_index].answer;
    }

    public int currentQuestionText() {
        return questions[current_index].resId;
    }

    public void moveToNext(){
        current_index = (current_index + 1) % questions.length;
    }

    public boolean isCheater() {
        return isCheater;
    }

    public void setCheater(boolean cheater) {
        isCheater = cheater;
    }
}
