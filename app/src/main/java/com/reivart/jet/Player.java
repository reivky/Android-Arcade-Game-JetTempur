package com.reivart.jet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.ArrayList;

public class Player {

    private Bitmap mBitmap;

    private int mX;
    private int mY;
    private int mSpeed;
    private int mMaxX;
    private int mMinX;
    private int mMaxY;
    private int mMinY;
    private int mMargin = 16;
    private boolean mIsSteerLeft, mIsSteerRight;
    private float mSteerSpeed;
    private Rect mCollision;
    private ArrayList<Peluru> mPelurus;
    private SoundPlayer mSoundPlayer;
    private Context mContext;
    private int mScreenSizeX, mScreenSizeY;

    public Player(Context context, int screenSizeX, int screenSizeY, SoundPlayer soundPlayer) {
        mScreenSizeX = screenSizeX;
        mScreenSizeY = screenSizeY;
        mContext = context;

        mSpeed = 1;
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);
        mBitmap = Bitmap.createScaledBitmap(mBitmap, mBitmap.getWidth() * 3/5, mBitmap.getHeight() * 3/5, false);

        mMaxX = screenSizeX - mBitmap.getWidth();
        mMaxY = screenSizeY - mBitmap.getHeight();
        mMinX = 0;
        mMinY = 0;

        mX = screenSizeX/2 - mBitmap.getWidth()/2;
        mY = screenSizeY - mBitmap.getHeight() - mMargin;

        mPelurus = new ArrayList<>();
        mSoundPlayer = soundPlayer;

        mCollision = new Rect(mX, mY, mX + mBitmap.getWidth(), mY + mBitmap.getHeight());
    }

    public void update(){
        if (mIsSteerLeft){
            mX -= 10 * mSteerSpeed;
            if (mX<mMinX){
                mX = mMinX;
            }
        }else if (mIsSteerRight){
            mX += 10 * mSteerSpeed;
            if (mX>mMaxX){
                mX = mMaxX;
            }
        }

        mCollision.left = mX;
        mCollision.top = mY;
        mCollision.right = mX + mBitmap.getWidth();
        mCollision.bottom = mY + mBitmap.getHeight();

        for (Peluru l : mPelurus) {
            l.update();
        }

        boolean deleting = true;
        while (deleting) {
            if (mPelurus.size() != 0) {
                if (mPelurus.get(0).getY() < 0) {
                    mPelurus.remove(0);
                }
            }

            if (mPelurus.size() == 0 || mPelurus.get(0).getY() >= 0) {
                deleting = false;
            }
        }
    }

    public ArrayList<Peluru> getPelurus() {
        return mPelurus;
    }

    public void fire(){
        mPelurus.add(new Peluru(mContext, mScreenSizeX, mScreenSizeY, mX, mY, mBitmap, false));
        mSoundPlayer.playPeluru();
    }

    public Rect getCollision() {
        return mCollision;
    }

    public void steerRight(float speed){
        mIsSteerLeft = false;
        mIsSteerRight = true;
        mSteerSpeed = Math.abs(speed);
    }

    public void steerLeft(float speed){
        mIsSteerRight = false;
        mIsSteerLeft = true;
        mSteerSpeed = Math.abs(speed);
    }

    public void stay(){
        mIsSteerLeft = false;
        mIsSteerRight = false;
        mSteerSpeed = 0;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public int getX() {
        return mX;
    }

    public int getY() {
        return mY;
    }

    public int getSpeed() {
        return mSpeed;
    }
}
