package com.christian.pong;

import android.graphics.RectF;

public class Ball {
    //all private fields
    private RectF mRect;
    private float mXVelocity;
    private float mYVelocity;
    private float mBallWidth;
    private float mBallHeight;


    //constructor
    public Ball(int screenX){
        mBallWidth = screenX / 100;
        mBallHeight = screenX / 100;

        mRect = new RectF();
    }

    //getter method
    RectF getRect(){
        return mRect;
    }

    //update method
    void update(long fps){
        mRect.left = mRect.left + (mXVelocity / fps);
        mRect.top = mRect.top + (mYVelocity / fps);

        mRect.right = mRect.left + mBallWidth;
        mRect.bottom = mRect.top + mBallHeight;
    }

    //reverse the vertical direction of travel
    void reverseYVelocity(){
        mYVelocity = -mYVelocity;
    }
    //reverse the horizontal direction of travel
    void reverseXVelocity(){
        mXVelocity = -mXVelocity;
    }
}
