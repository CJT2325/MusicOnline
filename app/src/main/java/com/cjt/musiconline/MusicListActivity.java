package com.cjt.musiconline;

import android.content.Intent;
import android.graphics.Rect;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.cjt.musiconline.adapter.MusicInfoAdapter;
import com.cjt.musiconline.bean.MusicInfo;

import java.util.ArrayList;
import java.util.List;

public class MusicListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private RecyclerView recyclerView;
    private List<MusicInfo> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        recyclerView= (RecyclerView) findViewById(R.id.recycleview_musiclist);
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        initDatas();
        MusicInfoAdapter musicInfoAdapter=new MusicInfoAdapter(datas, this, new MusicInfoAdapter.MyItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                ActivityOptionsCompat activityOptions= ActivityOptionsCompat.makeSceneTransitionAnimation(
                        MusicListActivity.this,
                        new Pair<View, String>(view.findViewById(R.id.image_muisc),"image")
                );
                Intent intent=new Intent(MusicListActivity.this,MainActivity.class);
                intent.putExtra("imageurl",datas.get(position).getImageUrl());
                intent.putExtra("name",datas.get(position).getName());
                intent.putExtra("url",datas.get(position).getUrl());
                ActivityCompat.startActivity(MusicListActivity.this,intent,activityOptions.toBundle());
            }
        });
        recyclerView.setAdapter(musicInfoAdapter);
        //设置item之间的间隔
//        SpacesItemDecoration decoration=new SpacesItemDecoration(0);
//        recyclerView.addItemDecoration(decoration);
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, "Home").setActiveColor(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.drawable.ic_book_white_24dp, "Books").setActiveColor(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.drawable.ic_music_note_white_24dp, "Music").setActiveColor(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.drawable.ic_videogame_asset_white_24dp, "Games").setActiveColor(R.color.colorPrimary))
                .initialise();
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);

    }

    private void initDatas() {
        datas=new ArrayList<MusicInfo>();

        MusicInfo m1=new MusicInfo();
        m1.setImageUrl("http://music.sise.cn/upload/2014/06/12/4518c2d8c3444c4b38ef7bef50c0ba96.jpg");
        m1.setUrl("http://music.sise.cn/upload/2014/06/12/b655f118cfaa1e1db68e0d3990945dc0.mp3");
        m1.setName("阿牛");
        m1.setAuthor("陈奕迅");

        MusicInfo m2=new MusicInfo();
        m2.setImageUrl("http://music.sise.cn/upload/2014/06/12/5f4267d4e6943fae892261827bc2563d.jpg");
        m2.setUrl("http://music.sise.cn/upload/2014/06/12/94e81c30846faec1cdf1faa325662c47.mp3");
        m2.setName("命硬");
        m2.setAuthor("侧田");

        MusicInfo m3=new MusicInfo();
        m3.setImageUrl("http://music.sise.cn/upload/2014/06/12/0bf04249db7083994a583d9ff7226dd0.jpg");
        m2.setUrl("http://music.sise.cn/upload/2014/06/12/edfcd3af1d275979310884e226507e85.mp3");
        m3.setName("Living Things");
        m3.setAuthor("Linkin Park");

        MusicInfo m4=new MusicInfo();
        m4.setImageUrl("http://music.sise.cn/upload/2014/06/12/47c08aa8737d0ea1b94fd2aa0ab0c5be.jpg");
        m4.setUrl("http://music.sise.cn/upload/2014/06/12/0441e9cfd2e08960d3b4cd3f593aa7ca.mp3");
        m4.setName("北极光");
        m4.setAuthor("莫文蔚");

        MusicInfo m5=new MusicInfo();
        m5.setImageUrl("http://music.sise.cn/upload/2014/06/12/9098843f0251f80caacf00eecd40cddd.jpg");
        m5.setUrl("http://music.sise.cn/upload/2014/06/12/562cb4f45b4dee32ab4c4f00b530b9e1.mp3");
        m5.setName("MMLP2");
        m5.setAuthor("Eminem");

        MusicInfo m6=new MusicInfo();
        m6.setImageUrl("http://music.sise.cn/upload/2014/06/12/e4612f9fe548eab8e97bf2e70abaff14.jpg");
        m6.setUrl("http://music.sise.cn/upload/2014/06/12/d2999c87535d5cf295f93dc6edee5aa6.mp3");
        m6.setName("圆舞曲");
        m6.setAuthor("许哲佩");

        MusicInfo m7=new MusicInfo();
        m7.setImageUrl("http://music.sise.cn/upload/2014/06/12/e4612f9fe548eab8e97bf2e70abaff14.jpg");
        m7.setUrl("http://music.sise.cn/upload/2014/06/12/d2999c87535d5cf295f93dc6edee5aa6.mp3");
        m7.setName("圆舞曲");
        m7.setAuthor("许哲佩");

        MusicInfo m8=new MusicInfo();
        m8.setImageUrl("http://music.sise.cn/upload/2014/06/12/e4612f9fe548eab8e97bf2e70abaff14.jpg");
        m8.setUrl("http://music.sise.cn/upload/2014/06/12/d2999c87535d5cf295f93dc6edee5aa6.mp3");
        m8.setName("圆舞曲");
        m8.setAuthor("许哲佩");

        MusicInfo m9=new MusicInfo();
        m9.setImageUrl("http://music.sise.cn/upload/2014/06/12/e4612f9fe548eab8e97bf2e70abaff14.jpg");
        m9.setUrl("http://music.sise.cn/upload/2014/06/12/d2999c87535d5cf295f93dc6edee5aa6.mp3");
        m9.setName("圆舞曲");
        m9.setAuthor("许哲佩");

        datas.add(m1);
        datas.add(m2);
        datas.add(m3);
        datas.add(m4);
        datas.add(m5);
        datas.add(m6);
        datas.add(m7);
        datas.add(m8);
        datas.add(m9);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
}
/*class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public SpacesItemDecoration(int space) {
        this.space=space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left=space;
        outRect.right=space;
        outRect.bottom=space;
        if(parent.getChildAdapterPosition(view)==0){
            outRect.top=space;
        }
    }
}*/
