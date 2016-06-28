package com.cjt.musiconline;


import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * 作者: 陈嘉桐 on 2016/5/26
 * 邮箱: 445263848@qq.com.
 */
public class Player implements OnBufferingUpdateListener, OnCompletionListener, OnPreparedListener {

    public MediaPlayer mediaPlayer;//媒体播放器
    private SeekBar seekBar;//拖动条
    private TextView startTime,endTime;
    private Timer mTimer = new Timer();//计时器
    private boolean isCompletion=false;

    public boolean isCompletion() {
        return isCompletion;
    }

    public void setCompletion(boolean completion) {
        isCompletion = completion;
    }

    public int getDuration(){
        return mediaPlayer.getDuration();
    }
    public int getCurrentPosition(){
        return mediaPlayer.getCurrentPosition();
    }

    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            if (mediaPlayer == null) {
                return;
            }
            if (mediaPlayer.isPlaying() && seekBar.isPressed() == false) {
                handler.sendEmptyMessage(0);

            }
        }
    };
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int position = mediaPlayer.getCurrentPosition();
            int duration = mediaPlayer.getDuration();
            if (duration > 0) {
                long pos = seekBar.getMax() * position / duration;
                seekBar.setProgress((int) pos);
                startTime.setText(getDurationString(position,false));
                endTime.setText(getDurationString(duration,false));
            }
        }
    };

    public Player(SeekBar seekBar, TextView starttime, TextView endtime) {
        super();
        this.seekBar = seekBar;
        this.startTime=starttime;
        this.endTime=endtime;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);// 设置媒体流类型
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mTimer.schedule(timerTask, 0, 1000);
    }

    public void play() {
        mediaPlayer.start();
    }

    public void playUrl(String url) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        seekBar.setSecondaryProgress(percent);
        int currentProgress = seekBar.getMax() * mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration();
        Log.i(currentProgress + "% play", percent + " buffer");
        if (percent==100){
            setCompletion(true);
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.i("MediaPlayer", "onCompletion");
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.i("MediaPlayer", "onPrepared");
        Log.i("MediaPlayer","全长："+getDuration());
        Log.i("MediaPlayer","位置："+getCurrentPosition());
    }

    public  String getDurationString(long durationMs, boolean negativePrefix) {
        return String.format(Locale.getDefault(), "%s%01d:%02d",
                negativePrefix ? "-" : "",
                TimeUnit.MILLISECONDS.toMinutes(durationMs),
                TimeUnit.MILLISECONDS.toSeconds(durationMs) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(durationMs))
        );
    }
}
