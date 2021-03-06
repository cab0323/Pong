package com.christian.pong;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.Canvas;

public class PongGame extends SurfaceView implements Runnable{
    //variables ////////////////
    //debugging variable
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
    private Obstacle mObstacle;
    //score and lives remaining
    private int mScore;
    private int mLives;

    //thread variables
    private Thread mGameThread = null;
    //this can be accessed inside and outside the thread
    private volatile boolean mPlaying;
    private boolean mPaused = true;

    //constructor of PongGame
    /**
     *
     * @pre (x > 0 && y > 0)
     * @post x
     * @param y
     */
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

        //initialize the bat and ball
        mBall = new Ball(mScreenX);
        mBat = new Bat(mScreenX, mScreenY);
        mObstacle = new Obstacle(mScreenX, mScreenY);

        //start the game
        startNewGame();
    }

    //run method
    @Override
    public void run() {
        while(mPlaying){
            long frameStartTime = System.currentTimeMillis();

            //provided game is not paused
            if(!mPaused){
                update();
                //this will get bat and ball to new positions
                //check if any collisions have occurred
                detectCollisions();
            }

            draw();

            //check how long this frame/loop took
            long timeThisFrame = System.currentTimeMillis() - frameStartTime;
            //make sure timeThisFrame is at least 1 millisecond so game wont crash
            if(timeThisFrame > 0){
                mFPS = MILLIS_IN_SECOND / timeThisFrame;
            }
        }
    }

    //update method
    private void update(){
        //update the ball
        mBall.update(mFPS);
        mBat.update(mFPS);
        mObstacle.update(mFPS);
    }

    //detect collision method
    private void detectCollisions(){
        //did bat hit the ball
        if(RectF.intersects(mBat.getRect(), mBall.getRect())){
            //bounce
            mBall.batBounce(mBat.getRect());
            mBall.increaseVelocity();
            mScore++;
        }
        //did obstacle hit ball
        if(RectF.intersects(mObstacle.getRect(), mBall.getRect())){
            //bounce the ball
            mBall.batBounce(mObstacle.getRect());
           // mBall.increaseVelocity();
        }
        //bottom
        if(mBall.getRect().bottom > mScreenY){
            mBall.reverseYVelocity();
            mLives--;
            if(mLives == 0){
                mPaused = true;
                startNewGame();
            }
        }
        //top
        if(mBall.getRect().top < 0){
            mBall.reverseYVelocity();
        }
        //left
        if(mBall.getRect().left < 0){
            mBall.reverseXVelocity();
        }
        if(mObstacle.getRect().left < 0){
            mObstacle.bounce();
        }
        //right
        if(mBall.getRect().right > mScreenX){
            mBall.reverseXVelocity();
        }
        if(mObstacle.getRect().right > mScreenX){
            mObstacle.bounce();
        }
    }
    //starting a new game
    private void startNewGame(){
        //resetting ball to starting position
        mBall.reset(mScreenX, mScreenY);

        //reset the obstacle position
        mObstacle.reset(mScreenX);

        //reset player's score and lives
        mScore = 0;
        mLives = 3;
    }

    //draw method
    private void draw(){
        if(mOurHolder.getSurface().isValid()){
            mCanvas = mOurHolder.lockCanvas();

            //fill screen with color
            mCanvas.drawColor(Color.argb(255,26,128,182));

            //color to paint with
            mPaint.setColor(Color.argb(255,255,255,255));

            //draw bat, ball, and obstacle
            mCanvas.drawRect(mBall.getRect(), mPaint);
            mCanvas.drawRect(mBat.getRect(), mPaint);
            mCanvas.drawRect(mObstacle.getRect(), mPaint);

            //choose font size
            mPaint.setTextSize(mFontSize);

            //draw HUD
            mCanvas.drawText("Score: " + mScore + " Lives: " + mLives, mFontMargin,
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

    //handle all the screen touches
    public boolean onTouchEvent(MotionEvent motionEvent){
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
            //player has put finger on screen
            case MotionEvent.ACTION_DOWN:
                //if game was paused, then unpause
                mPaused = false;
                //find where touch happened
                if(motionEvent.getX() > mScreenX / 2){
                    //touch was on the right
                    mBat.setMovementState(mBat.RIGHT);
                }
                else {
                    //touch was on the left
                    mBat.setMovementState(mBat.LEFT);
                }
                break;
            //player lifted finger from screen
            case MotionEvent.ACTION_UP:
                //stop the bat moving
                mBat.setMovementState(mBat.STOPPED);
                break;
        }
        return true;
    }
}
