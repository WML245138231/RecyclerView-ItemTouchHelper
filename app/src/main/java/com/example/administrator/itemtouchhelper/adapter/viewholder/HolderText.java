package com.example.administrator.itemtouchhelper.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.itemtouchhelper.R;

/**
 * Created by Administrator on 2016/12/21.
 */

public class HolderText extends RecyclerView.ViewHolder {
    public TextView mtextView;
    public HolderText(View itemView) {
        super(itemView);
        mtextView= (TextView) itemView.findViewById(R.id.mtextView);
    }
}
