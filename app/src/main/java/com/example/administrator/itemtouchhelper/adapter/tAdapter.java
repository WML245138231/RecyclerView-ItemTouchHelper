package com.example.administrator.itemtouchhelper.adapter;

import android.content.Context;
import android.support.v7.widget.MenuItemHoverListener;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.itemtouchhelper.R;
import com.example.administrator.itemtouchhelper.adapter.viewholder.HolderText;
import com.example.administrator.itemtouchhelper.itemhelper.MItemTouchListener;
import com.example.administrator.itemtouchhelper.tools.MRelativeLayout;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/21.
 */

public class tAdapter extends RecyclerView.Adapter<HolderText> implements MItemTouchListener.Callback{
    private Context context;
    private ArrayList<Object> contents;
    private RecyclerView mrecyclerView;
    public tAdapter(Context context, ArrayList<Object> contents) {
        this.contents = contents;
        this.context = context;
    }

    @Override
    public HolderText onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_text, parent, false);
        return new HolderText(view);
    }

    @Override
    public void onBindViewHolder(HolderText holder, int position) {
        holder.mtextView.setText(contents.get(position)==null?"":(String)contents.get(position));
    }

    /*
    * 绑定ItemTouch监听
    * */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mrecyclerView=recyclerView;
        recyclerView.addOnItemTouchListener(new MItemTouchListener(context,this));
    }

    @Override
    public int getItemCount() {
        return contents == null ? 0 : contents.size();
    }

    /*
    * 通过X，Y获取Recyclerviwe中的Item
    * */
    @Override
    public MRelativeLayout getSwipLayout(float x, float y) {
        return (MRelativeLayout) mrecyclerView.findChildViewUnder(x,y);
    }
}
