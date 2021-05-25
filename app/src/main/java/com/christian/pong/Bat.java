package com.christian.pong;

import android.graphics.RectF;

public class Bat {
    //member variables
    private RectF mRect;
    private float mLength;
    private float mBatSpeed;
    private float mxCoord;
    private int mScreenX;
    //public variables
    final int STOPPED = 0;
    final int LEFT = 1;
    final int RIGHT = 2;

    //keeps track of bats motion
    private int mBatMoving = STOPPED;

    public Bat(int sx, int sy){
        mScreenX = sx;

        mLength = mScreenX / 8;

        //one fourtied the screen height
        float height = sy / 40;

        mxCoord = mScreenX / 2;

        //height of screen from the bottom of screen
        //this will place bat on the bottom pixel of screen
        float mYCoord = sy - height;

        //initialize mRect
        mRect = new RectF(mxCoord, mYCoord, mxCoord + mLength, mYCoord + height);

        //figure out speed of bat
        mBatSpeed = mScreenX;
    }

    //getter method
    RectF getRect(){
        return mRect;
    }

    //update the movement of teh bat
    //state variable will be one of the final variables,
    //that we declared earlier
    void setMovementState(int state){
        mBatMoving = state;
    }

    //update method, called each frame
    void update(long fps){
        //move the bat based on the mbatmoving variable and
        //the speed of last variable
        if (mBatMoving == LEFT){
            mxCoord = mxCoord - mBatSpeed / fps;
        }
        if(mBatMoving == RIGHT){
            mxCoord = mxCoord + mBatSpeed / fps;
        }

        //stop bat from going off screen
        if (mxCoord < 0){
            mxCoord = 0;
        }
        else if(mxCoord + mLength > mScreenX){
            mxCoord = mScreenX - mLength;
        }

        //update the mrect based on the previous code
        mRect.left = mxCoord;
        mRect.right = mxCoord + mLength;
    }

}
