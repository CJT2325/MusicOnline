package com.cjt.musiconline;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    boolean isPlay=false;
    ImageButton btn_playonline;
    CircleImageView mCircleImageView;

    TextView starttime,endtime;
    SeekBar seekbar;
    RelativeLayout images;
    ImageView imagemusic;
    TextView muiscname;

    Player player;
    String url;
    Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent=getIntent();

        btn_playonline= (ImageButton) findViewById(R.id.btn_playonline);
        seekbar= (SeekBar) findViewById(R.id.seekbar);
        starttime= (TextView) findViewById(R.id.starttime);
        endtime= (TextView) findViewById(R.id.endtime);
        player=new Player(seekbar,starttime,endtime);

        imagemusic= (ImageView) findViewById(R.id.imagemusic);
        mCircleImageView= (CircleImageView) findViewById(R.id.circleimageview);
        images= (RelativeLayout) findViewById(R.id.images);

        Picasso.with(this).load(intent.getStringExtra("imageurl")).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                mCircleImageView.setImageBitmap(bitmap);
                Bitmap blurbitmap=FastBlur.doBlur(bitmap,10,false);
                imagemusic.setImageBitmap(blurbitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
        url=intent.getStringExtra("url");
        getSupportActionBar().setTitle(intent.getStringExtra("name"));


        ViewCompat.setTransitionName(findViewById(R.id.imagemusic),"image");

        seekbar.setOnSeekBarChangeListener(new SeekBarChangeEvent());
        btn_playonline.setOnClickListener(this);

        anim = AnimationUtils.loadAnimation(this, R.anim.my_rotate);
        LinearInterpolator lir = new LinearInterpolator();
        anim.setInterpolator(lir);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//
//            }
//        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (url != null) {
                    player.playUrl(url);
                }
            }
        }).start();

        Log.i("CJT",player.getDuration()+"");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == android.R.id.home)
        {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_playonline:
                if (!isPlay) {
                    player.play();
                    images.startAnimation(anim);
                    btn_playonline.setBackgroundResource(R.drawable.evp_action_pause);
                    isPlay=true;
                }else{
                    player.pause();
                    images.clearAnimation();
                    btn_playonline.setBackgroundResource(R.drawable.evp_action_play);
                    isPlay=false;
                }
                break;
        }
    }


    class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener{
        int progress;
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
            // 原本是(progress/seekBar.getMax())*player.mediaPlayer.getDuration()
            this.progress = progress * player.mediaPlayer.getDuration()/seekBar.getMax();
            if (player.isCompletion()){
                MainActivity.this.seekbar.setSecondaryProgress(100);
            }
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // seekTo()的参数是相对与影片时间的数字，而不是与seekBar.getMax()相对的数字
            player.mediaPlayer.seekTo(progress);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.stop();
        Log.i("CJT","onDestroy");
    }

}
