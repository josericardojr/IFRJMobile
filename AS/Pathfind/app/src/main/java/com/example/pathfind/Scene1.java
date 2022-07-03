package com.example.pathfind;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.concurrent.LinkedBlockingQueue;

public class Scene1 extends GameLoop{

    Grid grid;
    Grid.Cell startCell = null;
    Grid.Cell endCell = null;

    public final static int MODE_START = 0;
    public final static int MODE_END = 1;
    public final static int MODE_BLOCK = 2;



    int current_mode = MODE_START;

    public int getCurrent_mode() {
        return current_mode;
    }

    public void setCurrent_mode(int current_mode) {
        this.current_mode = current_mode;
    }



    public Scene1(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public Scene1(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public Scene1(Context context) {
        super(context);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //build();

        int [] loc = new int[2];
        getLocationOnScreen(loc);

        grid = new Grid(right - left, bottom - top,  128, 0, 0);
    }

    @Override
    public void processInput(LinkedBlockingQueue<MotionEvent> eventQueue) {

        if (eventQueue.isEmpty())
            return;
        int [] loc = new int[2];
        getLocationOnScreen(loc);

        MotionEvent mv = eventQueue.remove();

        if (mv.getAction() == MotionEvent.ACTION_DOWN){
            float _x = mv.getX();
            float _y = mv.getY();

            if (_x < loc[0] || _y < loc[1]) return;

            _x -= loc[0];
            _y -= loc[1];

            switch (current_mode){
                case MODE_START:
                    if (startCell != null) startCell.clearColor();
                    startCell = grid.getCellOnPosition(_x, _y);
                    grid.setStartColor(startCell.getId());
                    break;

                case MODE_END:
                    if (endCell != null) endCell.clearColor();
                    endCell = grid.getCellOnPosition(_x, _y);
                    grid.setEndColor(endCell.getId());
                    break;

                case MODE_BLOCK:
                    grid.setBlocked(grid.getCellOnPosition(_x, _y).getId());
                    break;
            }
        }

        eventQueue.clear();
    }

    @Override
    public void onUpdate(float dt) {

    }

    @Override
    public void onRender(Canvas canvas) {
        grid.render(canvas);
    }

    public void clearPath(){
        grid.clearPath();
    }

    public void makePath(){
        grid.makePath(startCell, endCell);
    }
}
