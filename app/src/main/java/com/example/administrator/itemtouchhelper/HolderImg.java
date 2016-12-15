package com.example.administrator.itemtouchhelper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by Administrator on 2016/12/15.
 */

public class HolderImg extends RecyclerView.ViewHolder {
    public SimpleDraweeView mimg;
    public HolderImg(View itemView) {
        super(itemView);
        mimg= (SimpleDraweeView) itemView.findViewById(R.id.mimg);
    }
}
