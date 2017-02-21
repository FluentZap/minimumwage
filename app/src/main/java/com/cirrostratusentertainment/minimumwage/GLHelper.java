package com.cirrostratusentertainment.minimumwage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.opengl.GLES11;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Toad on 12/29/2016.
 */

public class GLHelper {

    public static int loadTexture(Context context, final int resourceId)
    {
        final int[] textureHandle = new int[1];

        GLES11.glGenTextures(1, textureHandle, 0);

        if (textureHandle[0] != 0)
        {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;   // No pre-scaling

            // Read in the resource
            final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);

            // Bind to the texture in OpenGL
            GLES11.glBindTexture(GLES11.GL_TEXTURE_2D, textureHandle[0]);

            // Set filtering
            GLES11.glTexParameteri(GLES11.GL_TEXTURE_2D, GLES11.GL_TEXTURE_MIN_FILTER, GLES11.GL_NEAREST);
            GLES11.glTexParameteri(GLES11.GL_TEXTURE_2D, GLES11.GL_TEXTURE_MAG_FILTER, GLES11.GL_NEAREST);

            // Load the bitmap into the bound texture.
            GLUtils.texImage2D(GLES11.GL_TEXTURE_2D, 0, bitmap, 0);

            // Recycle the bitmap, since its data has been loaded into OpenGL.
            bitmap.recycle();
        }

        if (textureHandle[0] == 0)
        {
            throw new RuntimeException("Error loading texture.");
        }

        return textureHandle[0];
    }


    public void DrawSprite(int Bitmap, Point cords)
    {




    }

    public static class Square {

        public FloatBuffer vertexBuffer;
        public ShortBuffer drawListBuffer;
        public FloatBuffer texBuffer;

        // number of coordinates per vertex in this array
        static final int COORDS_PER_VERTEX = 3;
        static float squareCoords[] = {
                0.0f,  0.0f, 0.0f,   // bot left
                0.0f, 1.0f, 0.0f,   // top left
                1.0f, 1.0f, 0.0f,   // top right
                1.0f,  0.0f, 0.0f }; // bot right

        static float texCoords[] = {
                0.0f, 1.0f,   // bot left
                0.0f, 0.0f,   // top left
                1.0f, 0.0f,    // top right
                1.0f, 1.0f }; // bot right



        private short drawOrder[] = { 0, 1, 2, 0, 2, 3 }; // order to draw vertices

        public Square() {
            // initialize vertex byte buffer for shape coordinates
            ByteBuffer bb = ByteBuffer.allocateDirect(
                    // (# of coordinate values * 4 bytes per float)
                    squareCoords.length * 4);
            bb.order(ByteOrder.nativeOrder());
            vertexBuffer = bb.asFloatBuffer();
            vertexBuffer.put(squareCoords);
            vertexBuffer.position(0);

            // Initialize texture buffer for skinning
            ByteBuffer tb = ByteBuffer.allocateDirect(
                    // (# of coordinate values * 4 bytes per float)
                    squareCoords.length * 4);
            tb.order(ByteOrder.nativeOrder());
            texBuffer = tb.asFloatBuffer();
            texBuffer.put(texCoords);
            texBuffer.position(0);



            // initialize byte buffer for the draw list
            ByteBuffer dlb = ByteBuffer.allocateDirect(
                    // (# of coordinate values * 2 bytes per short)
                    drawOrder.length * 2);
            dlb.order(ByteOrder.nativeOrder());
            drawListBuffer = dlb.asShortBuffer();
            drawListBuffer.put(drawOrder);
            drawListBuffer.position(0);
        }
    }


}
