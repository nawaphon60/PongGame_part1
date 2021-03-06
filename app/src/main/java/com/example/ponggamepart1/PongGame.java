package com.example.ponggamepart1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class PongGame extends SurfaceView implements Runnable {
    // Are we debugging?
    private final boolean DEBUGGING = true;

    // These objects are needed to do the drawing
    private SurfaceHolder mOurHolder;
    private Canvas mCanvas;
    private Paint mPaint;

    // How many frames per second did we get?
    private long mFPS;

    // The number of milliseconds in a second
    private final int MILLIS_IN_SECOND = 1000;
    // Holds the resolution of the screen
    private int mScreenX;
    private int mScreenY;
    // How big will the text be?
    private int mFontSize;
    private int mFontMargin;

    // The game objects
    //private Bat mBat;
    //private Ball mBall;

    // The current score and lives remaining
    private int mScore;
    private int mLives;
    private Thread mGameThread = null;

    // This volatile variable can be accessed from inside and outside the thread
    private volatile boolean mPlaying;
    private boolean mPaused = true;

    public PongGame(Context context, int x, int y) {

        super(context);
        // Initialize these two members/fields
// With the values passed in as parameters
        mScreenX = x;
        mScreenY = y;
// Font is 5% (1/20th) of screen width
        mFontSize = mScreenX / 20;
// Margin is 1.5% (1/75th) of screen width
        mFontMargin = mScreenX / 75;
// Initialize the objects
// ready for drawing with
// getHolder is a method of SurfaceView
        mOurHolder = getHolder();
        mPaint = new Paint();
// Initialize the bat and ball
// Everything is ready so start the game
        startNewGame();

    }
    private void startNewGame(){

// Put the ball back to the starting position

// Reset the score and the player's chances
        mScore = 0;
        mLives = 3;

    }
    private void draw() {

            if (mOurHolder.getSurface().isValid()) {
                mCanvas = mOurHolder.lockCanvas(); // Lock the canvas (graphics memory)
                mCanvas.drawColor(Color.argb(255, 26, 128, 182));
                mPaint.setColor(Color.argb(255, 255, 255, 255));
                mPaint.setTextSize(mFontSize);
                mCanvas.drawText("Score: " + mScore + " Lives: " + mLives,

                        mFontMargin, mFontSize, mPaint);

                if (DEBUGGING) {
                    printDebuggingText();
                }
                mOurHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    private void printDebuggingText(){
        int debugSize = mFontSize / 2;
        int debugStart = 150;
        mPaint.setTextSize(debugSize);
        mCanvas.drawText("FPS: " + mFPS ,

                10, debugStart + debugSize, mPaint);

    }

    // This method is called by PongActivity when the player quits the game
    public void pause() {

// Set mPlaying to false. Stopping the thread isn’t always instant
        mPlaying = false;
        try {

            mGameThread.join();

        } catch (InterruptedException e) {

            Log.e("Error:", "joining thread");

        }

    }
    // This method is called by PongActivity when the player starts the game
    public void resume() {
        mPlaying = true;
// Initialize the instance of Thread
        mGameThread = new Thread(this);
// Start the thread
        mGameThread.start();

    }
    @Override
    public void run() {
// mPlaying must be true AND the thread running for the main loop to execute
        while (mPlaying) {
// What time is it now at the start of the loop?
            long frameStartTime = System.currentTimeMillis();
// Provided the game isn't paused call the update method
            if(!mPaused){
                update(); // update new positions
                detectCollisions(); // detect collisions
            }
//draw the scene
            draw();
// How long did this frame/loop take?
            long timeThisFrame = System.currentTimeMillis() - frameStartTime;
// check timeThisFrame > 0 ms because dividing by 0 will crashes game
            if (timeThisFrame > 0) {
// Store frame rate to pass to the update methods of mBat and mBall
                mFPS = MILLIS_IN_SECOND / timeThisFrame;
            }
        }
    }
    private void update() {
// Update the bat and the ball
    }

    private void detectCollisions(){
// Has the bat hit the ball?
// Has the ball hit the edge of the screen
// Bottom
// Top
// Left
// Right
    }
}
