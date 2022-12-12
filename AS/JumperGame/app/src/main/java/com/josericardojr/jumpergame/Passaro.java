package com.josericardojr.jumpergame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Passaro {
    public static final float GRAVITY = 9.8f;
    public static final float RADIUS = 100;
    Point position = new Point();

    public void draw(Canvas canvas){
        Paint p = new Paint();
        p.setColor(Color.RED);
        canvas.drawCircle(position.x, position.y, RADIUS, p);
    }

    public void update(){
        position.y += GRAVITY / 2;
    }

    public void setPosition(int posX, int posY){
        position.x = posX;
        position.y = posY;
    }

    public void pula(){
        this.position.y -= 100;
    }

    public Rect getBoundingVolume(){
        Rect bv = new Rect();
        bv.top = position.y - (int)(RADIUS / 2.0f);
        bv.left = position.x - (int)(RADIUS / 2.0f);
        bv.right = position.x + (int)(RADIUS / 2.0f);
        bv.bottom = position.y + (int)(RADIUS / 2.0f);

        return bv;
    }
}
