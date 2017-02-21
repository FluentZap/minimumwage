package com.cirrostratusentertainment.minimumwage;

import android.content.Context;
import android.graphics.Point;
import android.opengl.GLES11;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.LinkedList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

/**
 * Created by Toad on 12/28/2016.
 */

public class GLRender implements GLSurfaceView.Renderer {
    private GLHelper.Square square;
    private int tex0;
    private Context context;
    public Point screen_size;
    public GameEngine G;

    private LinkedList<MotionEvent> inputStack;

    private FPSCounter fpscounter;

    public GLRender(Context mcontext, GameEngine game) {
        context = mcontext;
        G = game;
        inputStack = new LinkedList<>();
    }


    public class FPSCounter {
        long startTime = System.nanoTime();
        int frames = 0;

        public void logFrame() {
            frames++;
            if (System.nanoTime() - startTime >= 1000000000) {
                Log.i("FPSCounter", "fps: " + frames);
                frames = 0;
                startTime = System.nanoTime();
            }
        }
    }

    public void attach_input(GLSurfaceView glView) {
        glView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                inputStack.add(event);
                return true;

            }
        });
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES11.glClearColor(0.2f, 0.6f, 0.6f, 1.0f);
        square = new GLHelper.Square();
        tex0 = GLHelper.loadTexture(context, R.drawable.box);


        GLES11.glEnable(GLES11.GL_TEXTURE_2D);
        GLES11.glDisable(GLES11.GL_DITHER);
        GLES11.glDisable(GLES11.GL_LIGHTING);
        GLES11.glBlendFunc(GLES11.GL_SRC_ALPHA, GLES11.GL_ONE_MINUS_SRC_ALPHA);


        GLES11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        GLES11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);

        GLES11.glFrontFace(GL11.GL_CW);

        fpscounter = new FPSCounter();

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        screen_size = new Point(width, height);

        GLES11.glViewport(0, 0, width, height);

        //float ratio = (float) width / height;
        gl.glMatrixMode(GL10.GL_PROJECTION);        // set matrix to projection mode
        gl.glLoadIdentity();                        // reset the matrix to its default state
        gl.glOrthof(0.0f, width, 0.0f, height, -1.0f, 1.0f);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        //gl.glFrustumf(-ratio, ratio, -1, 1, 3, 7);  // apply the projection matrix

    }

    @Override
    public void onDrawFrame(GL10 gl) {

        G.Process(inputStack);


        //Render

        GLES11.glClear(GLES11.GL_COLOR_BUFFER_BIT);
        GLES11.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
        GLES11.glVertexPointer(3, GL11.GL_FLOAT, 0, square.vertexBuffer);
        GLES11.glTexCoordPointer(2, GLES11.GL_FLOAT, 0, square.texBuffer);


        draw_battle_map();
        fpscounter.logFrame();


/*
        GLES11.glVertexPointer(3, GL11.GL_FLOAT, 0, square.vertexBuffer);
        GLES11.glBindTexture(GLES11.GL_TEXTURE_2D, tex0);
        GLES11.glTexCoordPointer(2, GLES11.GL_FLOAT, 0, square.texBuffer);

        GLES11.glPushMatrix();
        //GLES11.glTranslatef(0, 500, 0);
        GLES11.glScalef(64.0f * 2, 64.0f * 2, 0);

        GLES11.glDrawElements(GLES11.GL_TRIANGLES, 6, GLES11.GL_UNSIGNED_SHORT, square.drawListBuffer);

        GLES11.glPopMatrix();

        //y += 1;
        //if (y >= 500) y = 0;

        GLES11.glPushMatrix();
        GLES11.glTranslatef(0, 300, 0);
        GLES11.glScalef(64.0f * 2, 64.0f * 2, 0);

        GLES11.glDrawElements(GLES11.GL_TRIANGLES, 6, GLES11.GL_UNSIGNED_SHORT, square.drawListBuffer);

        GLES11.glPopMatrix();
        */
    }

    private void draw_battle_map() {

        GLES11.glBindTexture(GLES11.GL_TEXTURE_2D, tex0);


        GLES11.glPushMatrix();
        GLES11.glTranslatef(0, 300, 0);
        GLES11.glScalef(64.0f * 1, 64.0f * 1, 0);

        //GLES11.glDrawElements(GLES11.GL_TRIANGLES, 6, GLES11.GL_UNSIGNED_SHORT, square.drawListBuffer);

        GLES11.glPopMatrix();
        float size = G.Scale * 64;
        Point s = G.screen_Scroll;
        /*for (int x = 0; x < G.current_Map.size.x; x++) {
            for (int y = 0; y < G.current_Map.size.y; y++) {

                GLES11.glPushMatrix();
                GLES11.glTranslatef(0, screen_size.y - size, 0);

                GLES11.glTranslatef(s.x + x * size, s.y + y * -size, 0);

                GLES11.glScalef(size, size, 0);
                GLES11.glDrawElements(GLES11.GL_TRIANGLES, 6, GLES11.GL_UNSIGNED_SHORT, square.drawListBuffer);
                GLES11.glPopMatrix();

            }
        }
*/

    }

}
