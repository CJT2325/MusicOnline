package com.cjt.musiconline.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cjt.musiconline.MainActivity;
import com.cjt.musiconline.R;
import com.cjt.musiconline.bean.MusicInfo;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 陈嘉桐 on 2016/5/28
 * 邮箱: 445263848@qq.com.
 */
public class MusicInfoAdapter extends RecyclerView.Adapter<MusicInfoViewHolder>{
    private List<MusicInfo> datas;
    private Context mContext;
    private MyItemClickListener listener;

    public MusicInfoAdapter(List<MusicInfo> datas, Context context, MyItemClickListener listener) {
        this.datas = datas;
        this.mContext=context;
        this.listener=listener;
    }


    @Override
    public MusicInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.musicinfo_item,parent,false);
        return new MusicInfoViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(final MusicInfoViewHolder holder, int position) {
        Picasso.with(mContext).load(datas.get(position).getImageUrl()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Log.i("CJT","Picasso");
                holder.imageView.setImageBitmap(bitmap);
                Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        Log.i("CJT","Palette");
                        Palette.Swatch s1 = palette.getDarkMutedSwatch();
                        Palette.Swatch s2 = palette.getLightMutedSwatch();
                        if (s1 != null) {
//                            holder.relativeLayout.setBackgroundColor(s1.getRgb());
//                            holder.relativeLayout.getBackground().setAlpha(180);
                            Log.i("CJT",s1.toString());
                            s1.getPopulation();
                        }
                        if (s2 != null) {
                            holder.name.setTextColor(s2.getRgb());
                            holder.author.setTextColor(s2.getRgb());
                        }
                    }
                });
            }
            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
        holder.name.setText(datas.get(position).getName());
        holder.author.setText(datas.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    public interface MyItemClickListener {
        public void onItemClick(View view,int position);
    }
}


class MusicInfoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    CircleImageView imageView;
    TextView name;
    TextView author;
    RelativeLayout relativeLayout;
    private MusicInfoAdapter.MyItemClickListener myItemClickListener;

    public MusicInfoViewHolder(View itemView,MusicInfoAdapter.MyItemClickListener myItemClickListener) {
        super(itemView);
        imageView= (CircleImageView) itemView.findViewById(R.id.image_muisc);
        name= (TextView) itemView.findViewById(R.id.tv_muiscname);
        author= (TextView) itemView.findViewById(R.id.tv_muiscauthor);
        itemView.setOnClickListener(this);
        this.myItemClickListener=myItemClickListener;
    }



    @Override
    public void onClick(View v) {
        if (myItemClickListener!=null){
            myItemClickListener.onItemClick(v,getPosition());
        }
    }


}
