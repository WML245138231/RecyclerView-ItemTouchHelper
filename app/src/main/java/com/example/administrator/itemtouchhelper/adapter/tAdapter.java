package com.example.administrator.itemtouchhelper.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.itemtouchhelper.R;
import com.example.administrator.itemtouchhelper.adapter.viewholder.HolderText;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/21.
 */

public class tAdapter extends RecyclerView.Adapter<HolderText> {
    private Context context;
    private ArrayList<Object> contents;

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

    @Override
    public int getItemCount() {
        return contents == null ? 0 : contents.size();
    }
}
