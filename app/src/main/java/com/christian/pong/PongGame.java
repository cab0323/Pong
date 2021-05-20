package com.christian.pong;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

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
    }
}
