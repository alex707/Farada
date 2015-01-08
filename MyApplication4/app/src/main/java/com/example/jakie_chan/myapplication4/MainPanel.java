package com.example.jakie_chan.myapplication4;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class MainPanel extends SurfaceView implements
        SurfaceHolder.Callback {

    private static final String TAG = MainPanel.class.getSimpleName();

    private MainThread thread;
    private Droid droid;

    public MainPanel(Context context) {
        super(context);
        getHolder().addCallback(this);
        droid = new Droid(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher), 48, 48);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "Surface is being destroyed");
        boolean retry = true;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
        Log.d(TAG, "Thread was shut down cleanly");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            droid.handleActionDown((int)event.getX(), (int)event.getY());

            if (event.getY() > getHeight() - 50) {
                thread.setRunning(false);
                ((Activity)getContext()).finish();
            } else {
                Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
            }
        } if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (droid.isTouched()) {
                droid.setX((int)event.getX());
                droid.setY((int)event.getY());
            }
        } if (event.getAction() == MotionEvent.ACTION_UP) {
            if (droid.isTouched()) {
                droid.setTouched(false);
            }
        }
        return true;
    }

    public void render(Canvas canvas) {
        canvas.drawColor(Color.DKGRAY);
        droid.draw(canvas);
    }

}
