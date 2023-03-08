package com.josericardojr.gamelib;

import android.app.Activity;
import android.content.Context;
import android.view.SurfaceView;

public abstract class GameLoop extends SurfaceView {
    public GameLoop(Context context) {
        super(context);
    }

    public abstract void onRender();

    public abstract void onUpdate();
}
