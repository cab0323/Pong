package com.christian.pong;

import android.graphics.RectF;

public class Obstacle {
    private RectF mRect;
    private float mLength;
    private float obstacleSpeed;
    private int mScreenX;
    //private int XVelocity;
    private int mxCoord;
    private int mYCoord;
    private float height;

    //constructor

    /**
     *
     * @pre (sx > 0 && sy > 0)
     * @param sy
     */
    public Obstacle(int sx, int sy){
        mScreenX = sx;

        //set as same size as bat
        mLength = (mScreenX / 8);

        //set on the top of screen
        mYCoord = 0;

        //set the height of the obstacle, same height as bat
        height = sy / 40;

        //set to left of screen
        mxCoord = 0;

        mRect = new RectF();

        //obstacleSpeed = mScreenX;
    }

    //getter method
    RectF getRect(){
        return mRect;
    }

    //update method
    void update(long fps){
        mRect.left = mRect.left + (obstacleSpeed / fps);
        mRect.right = mRect.left + mLength;
    }

    //bounce method, when hitting the sides of the screen
    void bounce(){
        //XVelocity = -XVelocity;
        obstacleSpeed = -obstacleSpeed;
    }

    void reset(int x){
        mRect.left = mxCoord;
        mRect.top = mYCoord;
        mRect.right = mxCoord + mLength;
        mRect.bottom = mYCoord + height;

       // XVelocity = (x / 2);
        obstacleSpeed = (x / 2);
    }

}
