package com.cirrostratusentertainment.minimumwage;

import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

import java.util.LinkedList;

/**
 * Created by Toad on 12/29/2016.
 */

public class GameEngine {


    public GameEngine() {

    }


    static final long FPS = 60;
    public boolean Loop_Active = true;
    //public battle_Map current_Map;
    long startTime;

    public Point screen_Scroll = new Point(0, 0);
    public Float Scale = new Float(2.0f);

    public void Process(LinkedList<MotionEvent> inputStack) {
        //long ticksPS = 1000 / FPS;
        //long startTime;
        //long sleepTime;
        if (inputStack.size() != 0)
            process_ui(inputStack);

        process_logic();
    }


    private void process_logic() {

        long ticksPS = 1000 / FPS;

        if (System.currentTimeMillis() > startTime + ticksPS) {
            //  screen_Scroll.x += 8;
            startTime = System.currentTimeMillis();
        }

        //if (screen_Scroll.x > 300)
        //    screen_Scroll.x = 0;

        //if (current_Map == null)
        //    current_Map = new battle_Map(new Point(8, 8));
    }

    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float mPreviousX;
    private float mPreviousY;
    private float mSizeThetaStart;
    private float mOldScale;

    private float mscx;
    private float mscy;


    private void process_ui(LinkedList<MotionEvent> inputStack) {
        MotionEvent e = inputStack.poll();

        //if (e.getPointerCount() == 1)
            switch (e.getAction()) {
                case MotionEvent.ACTION_POINTER_DOWN:
                case MotionEvent.ACTION_DOWN:

                    mPreviousX = e.getX();
                    mPreviousY = -e.getY();
                    mscx = screen_Scroll.x;
                    mscy = screen_Scroll.y;

                case MotionEvent.ACTION_MOVE:
                    screen_Scroll.x = (int) (mscx + (e.getX() - mPreviousX));
                    screen_Scroll.y = (int) (mscy + (-e.getY() - mPreviousY));

                case MotionEvent.ACTION_UP:

            }

        if (e.getPointerCount() == 2)

            switch (e.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:

                case MotionEvent.ACTION_POINTER_DOWN:
                    mOldScale = Scale;
                    mSizeThetaStart = Math.abs(e.getX(0) - e.getX(1)) + Math.abs(e.getY(0) - e.getY(1));
                    Log.i("motion", Float.toString(e.getX(0)));

                case MotionEvent.ACTION_MOVE:

                    float theta = Math.abs(e.getX(0) - e.getX(1)) + Math.abs(e.getY(0) - e.getY(1));
                    Scale = mOldScale * (theta / mSizeThetaStart);
                    if (Scale < 1) Scale = 1.0f;
                    if (Scale > 10) Scale = 10.0f;

                case MotionEvent.ACTION_UP:

            }


    }


}
