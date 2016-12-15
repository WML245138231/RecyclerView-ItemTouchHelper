package com.example.administrator.itemtouchhelper;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/15.
 */

public class mAdapter extends RecyclerView.Adapter<HolderImg> {
    private Context context;
    private ArrayList<String> contents;
    private int screenWeith;
    public mAdapter(Context context, ArrayList<String> contents) {
        this.contents = contents;
        this.context = context;
        /*
        * 获取屏幕高宽
        * */
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        screenWeith=wm.getDefaultDisplay().getWidth();
    }

    @Override
    public HolderImg onCreateViewHolder(ViewGroup parent, int viewType) {
        /*
        * 根据屏幕宽度设置item的高宽
        * */
        View view = LayoutInflater.from(context).inflate(R.layout.item_img, parent, false);
        LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight=screenWeith/3;
        params.height=screenWeith/3;
        params.setMargins(2,2,2,2);
        view.setLayoutParams(params);
        return new HolderImg(view);
    }

    @Override
    public void onBindViewHolder(HolderImg holder, int position) {
        if (contents.get(position) != null) {
            holder.mimg.setImageURI(Uri.parse(contents.get(position)));
        } else {
            holder.mimg.setImageURI(Uri.parse("file://" + R.drawable.pic));
        }
    }

    @Override
    public int getItemCount() {
        return contents == null ? 0 : contents.size();
    }
}
