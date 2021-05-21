package com.christian.pong;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.Canvas;

public class PongGame extends SurfaceView implements Runnable{
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

    //thread variables
    private Thread mGameThread = null;
    //this can be accessed inside and outsdie the thread
    private volatile boolean mPlaying;
    private boolean mPaused = true;

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

    //run method
    @Override
    public void run() {

    }
    //starting a new game
    private void startNewGame(){
        //resetting ball to starting position

        //reset player's score and lives
        mScore = 0;
        mLives = 3;
    }

    //draw method
    private void draw(){
        if(mOurHolder.getSurface().isValid()){
            mCanvas = mOurHolder.lockCanvas();

            //fill screen with color
            mCanvas.drawColor(Color.argb(255,255,255,255));

            //color to paint with
            mPaint.setColor(Color.argb(255,255,255,255));

            //draw bat and ball
            //choose font size
            mPaint.setTextSize(mFontSize);

            //draw HUD
            mCanvas.drawText("Score" + mScore + " Lives: " + mLives, mFontMargin,
                    mFontSize, mPaint);
        if(DEBUGGING){
            printDebuggingText();
        }

        //display the drawing on screen
        mOurHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    //pause method for when the player quits the game
    public void pause(){
        //set mPlaying to false
        mPlaying = false;
        try{
            //stop the thread
            mGameThread.join();
        }catch (InterruptedException e ){
            Log.e("Error", "joining thread");
        }
    }

    //resume method, called by pongactivity when the player starts the game
    public void resume(){
        mPlaying = true;

        //initialize the instance of the thread
        mGameThread = new Thread(this);

        //start the thread
        mGameThread.start();
    }
    //debugging text
    private void printDebuggingText(){
        int debugSize = mFontSize / 2;
        int debugStart = 150;
        mPaint.setTextSize(debugSize);
        mCanvas.drawText("FPS: " + mFPS, 10, debugStart + debugSize, mPaint);

    }

}
