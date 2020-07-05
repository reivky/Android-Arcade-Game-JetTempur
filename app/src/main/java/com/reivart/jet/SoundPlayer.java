package com.reivart.jet;


import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;

public class SoundPlayer implements Runnable {

    private Thread mSoundThread;
    private volatile boolean mIsPlaying;
    private SoundPool mSoundPool;
    private int mExplodeId, mPeluruId, mCrashId;
    private boolean mIsPeluruPlaying, mIsExplodePlaying, mIsCrashPlaying;

    public SoundPlayer(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            mSoundPool = new SoundPool.Builder()
                    .setMaxStreams(10)
                    .setAudioAttributes(attributes)
                    .build();
        } else {
            mSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);
        }
        mExplodeId = mSoundPool.load(context, R.raw.rock_explode_1, 1);
        mPeluruId = mSoundPool.load(context, R.raw.peluru_sound, 1);
        mCrashId = mSoundPool.load(context, R.raw.rock_explode_2, 1);
    }

    @Override
    public void run() {
        while (mIsPlaying){
            if (mIsPeluruPlaying){
                mSoundPool.play(mPeluruId, 1, 1, 1, 0, 1f);
                mIsPeluruPlaying = false;
            }

            if (mIsExplodePlaying){
                mSoundPool.play(mExplodeId, 1, 1, 1, 0, 1);
                mIsExplodePlaying = false;
            }

            if (mIsCrashPlaying){
                mSoundPool.play(mCrashId, 1, 1, 1, 0, 1);
                mIsCrashPlaying = false;
            }
        }
    }

    public void playCrash(){
        mIsCrashPlaying = true;
    }

    public void playPeluru(){
        mIsPeluruPlaying = true;
    }

    public void playExplode(){
        mIsExplodePlaying = true;
    }

    public void resume(){
        mIsPlaying = true;
        mSoundThread = new Thread(this);
        mSoundThread.start();
    }

    public void pause() throws InterruptedException {
        Log.d("GameThread", "Sound");
        mIsPlaying = false;
        mSoundThread.join();
    }
}
