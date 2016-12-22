package com.example.administrator.itemtouchhelper.itemhelper.minterface;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Administrator on 2016/12/15.
 */

public interface OnItemTouchCallbackIm {
    boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target);
    void onSwiped(RecyclerView.ViewHolder viewHolder, int drection);
}
