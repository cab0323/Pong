package com.christian.pong;

import android.content.Context;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.Canvas;

public class PongGame extends SurfaceView {
    //variables ////////////////
    private final boolean DEBUGGING = true;
    //drawing variables
    private SurfaceHolder mOurHolder;
    private Canvas mCanvas;
    private Paint mPaint;
    //frames per second
    private long mFPS;
    //milliseconds in a second
    private final int MILLIS_IN_SECOND = 1000;
    //screen resolution
    private int mScreenX;
    private int mScreenY;
    //text size
    private int mFontSize;
    private int mFontMargin;
    //game objects
    private Bat mBat;
    private Ball mBall;
    //score and lives remaining
    private int mScore;
    private int mLives;

    //constructor of PongGame
    public PongGame(Context context, int x, int y){
        super(context);

        //initialize screen resolution
        //this also gives our entire pong class access to the screen resolution
        mScreenX = x;
        mScreenY = y;

        //set font to (1/20th) of screen width
        mFontSize = mScreenX/20;
        //set margin to (1/75th) of screen width
        mFontMargin = mScreenX/75;
        //initializing our objects
        mOurHolder = getHolder();
        mPaint = new Paint();

        //start the game
        startNewGame();
    }

    //starting a new game
    private void startNewGame(){
        //resetting ball to starting position

        //reset player's score and lives
        mScore = 0;
        mLives = 3;
    }
}
