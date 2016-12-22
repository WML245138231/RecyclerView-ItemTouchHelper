package com.example.administrator.itemtouchhelper.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.administrator.itemtouchhelper.itemhelper.MItemTouchHelper;
import com.example.administrator.itemtouchhelper.itemhelper.minterface.OnItemTouchCallbackIm;
import com.example.administrator.itemtouchhelper.R;
import com.example.administrator.itemtouchhelper.adapter.mAdapter;
import com.example.administrator.itemtouchhelper.tools.DisplayUtils;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private mAdapter madapter;
    private RecyclerView recyclerView;
    private ArrayList<Object> img;

    private Button bt_llVertiacal;
    private Button bt_llHorizontal;
    /*
    * item监听器
    * */
    private MItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.mRecycle);
        bt_llVertiacal= (Button) findViewById(R.id.bt_llVertiacal);
        bt_llHorizontal= (Button) findViewById(R.id.bt_llHorizontal);
        bt_llVertiacal.setOnClickListener(this);
        bt_llHorizontal.setOnClickListener(this);

        img = new ArrayList<>();
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
                if (itemPosition != parent.getChildCount() - 1) {
                    outRect.bottom = DisplayUtils.dpToPx(0.5f);
                }
            }
        });
        /*
        * 获取ItemTouchHelper
        * */
        mItemTouchHelper=new MItemTouchHelper(new OnItemTouchCallbackIm() {
            /*
             * item拖动回调实现
             * */
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                if (img == null) {
                    return false;
                }
                Collections.swap(img, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                madapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }
            /*
             * item滑动回调实现
             * */
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int drection) {
                if (img == null) {
                    return;
                }
                img.remove(viewHolder.getAdapterPosition());
                madapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        /*
        * 添加ItemTouchHelper.Callback
        * */
        mItemTouchHelper.attachToRecyclerView(recyclerView);
        madapter = new mAdapter(this,img);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(madapter);
        img.add(0, "http://n.sinaimg.cn/games/transform/20160827/cK2n-fxvkkcf6038403.jpg");
        img.add(1, "http://f1.bj.anqu.com/down/YjA3ZQ==/allimg/1210/17-121021112507.jpg");
        img.add(2, "http://imgs.hbsztv.com/2016/0908/20160908012356120.jpg");
        img.add(3, "http://f.hiphotos.baidu.com/zhidao/pic/item/5fdf8db1cb1349547c04f8da504e9258d1094a71.jpg");
        img.add(4, "http://www.bz55.com/uploads/allimg/150407/139-15040GI305.jpg");
        img.add(5, "http://c.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cdd1b9a7620fb30f2442a70fa7.jpg");
        img.add(6, "http://image72.360doc.com/DownloadImg/2014/05/0402/41292510_28.jpg");
        img.add(7, "http://4493bz.1985t.com/uploads/allimg/141204/4-141204095J8.jpg");
        img.add(8, "http://i2.hdslb.com/group1/M00/3E/D2/oYYBAFaGfwGAQ_SQAAHhagultbw697.jpg");
        img.add(9, "http://up.qqjia.com/z/25/tu32695_9.jpg");
        img.add(10, "http://up.qqjia.com/z/25/tu32695_10.jpg");
        img.add(11, "http://v1.qzone.cc/avatar/201503/15/13/08/550513b64bcbf041.jpg%21200x200.jpg");
        img.add(12, "http://www.poluoluo.com/qq/UploadFiles_7828/201611/2016110420035637.jpg");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_llVertiacal:
                startActivity(new Intent(this,LinearLayout_Ver.class));
                break;
            case R.id.bt_llHorizontal:

                break;
        }
    }
}
