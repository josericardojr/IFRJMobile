package com.josericardojr.quizgame;

import androidx.annotation.StringRes;

public class Question {
    public int resId;
    public boolean answer;

    public Question(@StringRes int textResId, boolean answer){
        resId = textResId;
        this.answer = answer;
    }
}
