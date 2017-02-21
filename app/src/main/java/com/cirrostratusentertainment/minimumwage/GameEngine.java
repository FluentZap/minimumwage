package com.cirrostratusentertainment.minimumwage;

import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

/**
 * Created by Toad on 12/29/2016.
 */

public class GameEngine {

    int order_Speed = 120;
    int burger_time = 600;
    int order_Advance;
    Random rand;

    public class order {
        public boolean ketchup;
        public boolean mustard;
        public boolean mayo;
        public double theta;

        public order(boolean k, boolean mu, boolean ma) {
            ketchup = k;
            mustard = mu;
            mayo = ma;
            theta = 0;
        }

        public order() {
            ketchup = rand.nextBoolean();
            mustard = rand.nextBoolean();
            mayo = rand.nextBoolean();
            theta = 0;
        }
    }


    LinkedList<order> orders;

    public GameEngine() {
        orders = new LinkedList<>();
        rand = new Random(System.currentTimeMillis());

    }


    static final long FPS = 60;
    public boolean Loop_Active = true;
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

        //if (System.currentTimeMillis() > startTime + ticksPS) {
        //double delta = (System.currentTimeMillis() - startTime) / 10;


            update_orders(1);
        //Log.i("FPSCounter", "f" + delta);

        //startTime = System.currentTimeMillis();
        //}



        //if (screen_Scroll.x > 300)
        //    screen_Scroll.x = 0;

        //if (current_Map == null)
        //    current_Map = new battle_Map(new Point(8, 8));
    }


    private void update_orders(double d) {
        //Add a new order once it is time
        if (order_Advance >= order_Speed) {
            order_Advance = 0;
            orders.add(new order());
        } else
            order_Advance += 1;

        //process orders
        ListIterator<order> it = orders.listIterator();
        while (it.hasNext()) {
            order o = it.next();
            o.theta += d;
            if (o.theta >= burger_time) {
                it.remove();
            }
        }


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
