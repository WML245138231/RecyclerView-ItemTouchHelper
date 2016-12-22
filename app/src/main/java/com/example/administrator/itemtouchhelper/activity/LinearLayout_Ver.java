package com.example.administrator.itemtouchhelper.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.administrator.itemtouchhelper.R;
import com.example.administrator.itemtouchhelper.adapter.tAdapter;
import com.example.administrator.itemtouchhelper.itemhelper.MItemTouchHelper;
import com.example.administrator.itemtouchhelper.itemhelper.minterface.OnItemTouchCallbackIm;

import java.util.ArrayList;
import java.util.Collections;

public class LinearLayout_Ver extends AppCompatActivity {
    private RecyclerView mRecycle_ver;
    private ArrayList<Object> strings;
    private tAdapter adapter;

    private MItemTouchHelper mItemTouchHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_layout__ver);
        mRecycle_ver= (RecyclerView) findViewById(R.id.mRecycle_ver);
        strings=new ArrayList<>();
        for (int i=0;i<15;i++){
            strings.add(i,(i+1)+". 纵向布局");
        }
        mRecycle_ver.setItemAnimator(new DefaultItemAnimator());
        mItemTouchHelper=new MItemTouchHelper(new OnItemTouchCallbackIm() {
            /*
             * item拖动回调实现
             * */
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                if (strings == null) {
                    return false;
                }
                Collections.swap(strings, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                adapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }
            /*
             * item滑动回调实现
             * */
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int drection) {
                if (strings == null) {
                    return;
                }
                strings.remove(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        mItemTouchHelper.attachToRecyclerView(mRecycle_ver);
        adapter=new tAdapter(this,strings);
        mRecycle_ver.setLayoutManager(new LinearLayoutManager(this));
        mRecycle_ver.setAdapter(adapter);
    }
}
