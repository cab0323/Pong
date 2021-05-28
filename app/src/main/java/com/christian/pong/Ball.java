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

    /**
     *
     * @pre screenX > 0
     */
    public Ball(int screenX){
        mBallWidth = screenX / 100;
        mBallHeight = screenX / 100;

        mRect = new RectF();
    }

    //getter method

    /**
     *
     * @postcondition return mRect;
     */
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

    //reset method
    void reset(int x, int y){
        mRect.left = x / 2;
        mRect.top = 0;
        mRect.right = x / 2 + mBallWidth;
        mRect.bottom = mBallHeight;

        mYVelocity = -(y / 3);
        mXVelocity = (x / 2);
    }

    //method to increase the speed as game progresses
    void increaseVelocity(){
        //increase by 10%
        mXVelocity = mXVelocity * 1.1f;
        mYVelocity = mYVelocity * 1.1f;
    }

    //bounce the bat according to where it hit bat

    /**
     *
     * @pre batPosition
     * @post (YVelocity > 0);
     */
    void batBounce(RectF  batPosition){
        //find center of bat
        float batCenter = batPosition.left + (batPosition.width() / 2);

        //find the center of the ball
        float ballCenter = mRect.left + (mBallWidth / 2);

        //find where the ball hit on the bat
        float relativeIntersect = (batCenter - ballCenter);

        //figure out bounce direction
        if(relativeIntersect < 0){
            //go right
            mXVelocity = Math.abs(mXVelocity);
        }
        else {
            //go left
            mXVelocity = -Math.abs(mXVelocity);
        }

        //the vertical direction will always be up since it is
        //bouncing back from the bat, so simply reversing the
        //vertical velocity is enough
        reverseYVelocity();
    }
}
