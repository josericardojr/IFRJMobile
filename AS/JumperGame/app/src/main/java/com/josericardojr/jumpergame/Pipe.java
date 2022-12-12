package com.josericardojr.jumpergame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.Random;

public class Pipe {
    private static final int PIPE_VEL_X = 10;
    public static final int PIPE_WIDTH = 200;
    private static final int MIN_HOLE = 400;
    private static final int MAX_HOLE = 500;
    private static final int MIN_PIPE_SIZE = 200;
    private int currentPosX;
    private int end_top_pipe;
    private int hole_size;
    private int s_width, s_height;


    public Pipe(int width, int height){
        s_width = width;
        s_height = height;
    }

    public void setPositionX(int posX){
        this.currentPosX = posX;
    }

    public int getPositionX(){
        return currentPosX;
    }

    public void update(){
        currentPosX -= PIPE_VEL_X;
    }

    public void respawn(){
         hole_size = MIN_HOLE + (int)(Math.random() * (MAX_HOLE - MIN_HOLE));
         end_top_pipe = MIN_PIPE_SIZE + (int)(Math.random() * MIN_PIPE_SIZE);
    }

    public void draw(Canvas canvas){
        Paint p = new Paint();
        p.setColor(Color.BLUE);
        canvas.drawRect(currentPosX, 0, currentPosX + PIPE_WIDTH, end_top_pipe, p);

        canvas.drawRect(currentPosX, end_top_pipe + hole_size,
                currentPosX + PIPE_WIDTH,  s_height, p);
    }

    public boolean checkCollision(Rect rect){

        // top pipe
        Rect rectTop = new Rect();
        rectTop.top = 0; rect.left = currentPosX;
        rectTop.bottom = end_top_pipe; rectTop.right = rectTop.left + PIPE_WIDTH;

        if(rectTop.intersect(rect)) return true;

        // bottom pipe
        Rect rectBottom = new Rect();
        rectBottom.top = end_top_pipe + hole_size; rect.left = currentPosX;
        rectBottom.bottom = s_height; rectBottom.right = rectBottom.left + PIPE_WIDTH;

        if (rectBottom.intersect(rect)) return true;

        return false;
    }

}
