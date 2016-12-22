package com.example.administrator.itemtouchhelper.itemhelper;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import com.example.administrator.itemtouchhelper.itemhelper.minterface.OnItemTouchCallbackIm;
import com.example.administrator.itemtouchhelper.adapter.viewholder.HolderImg;

/**
 * Created by Administrator on 2016/12/15.
 */

public class MItemTouchHelperBack extends ItemTouchHelper.Callback {

    private static final int GETMOVEMENTFlAGS_DID_TIME = 2;
    private static final int GETMOVEMENTFlAGS_NOT_DID_TIME = 0;
    /*
    * 回调监听
    * */
    private OnItemTouchCallbackIm onItemTouchCallbackListener;

    public MItemTouchHelperBack(OnItemTouchCallbackIm onItemTouchCallbackListener) {
        this.onItemTouchCallbackListener = onItemTouchCallbackListener;
    }

    /*
    * 当前拖动中的viewHolder(Item)
    * */
    private RecyclerView.ViewHolder selectedViewHolder;
    /*
    * 是否可拖动，滑动
    * */
    private boolean canDrag = true;
    private boolean canSwipe = true;

    /*
    * 是否处于拖动状态
    * */
    private boolean isDraging = false;
    /*
    * 用于判断当前长按、布局处理事件是否执行完毕(执行完毕即可进行判断)
    * */
    private int isGetMovementFlagsDown = 0;

    /*
    * Item根据布局设置允许拖动与滑动的方向(此方法会执行两次)
    * */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //不同布局的不同处理
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int dragFlags = 0;
        int swipeFlags = 0;
        if (layoutManager instanceof GridLayoutManager) {
            //GridLayoutManager允许左右上下拖动，不允许滑动
            dragFlags =
                    ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            swipeFlags = 0;
        } else if (layoutManager instanceof LinearLayoutManager) {
            if (((LinearLayoutManager) layoutManager).getOrientation() == LinearLayoutManager.VERTICAL) {
                //纵向LinearLayoutManager允许上下拖动，允许左右滑动
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            } else {
                //横向LinearLayoutManager允许左右拖动，允许上下滑动
                swipeFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                dragFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            }
        }
        //处理长按、布局处理事件判断
        isGetMovementFlagsDown++;
        if (isGetMovementFlagsDown == GETMOVEMENTFlAGS_DID_TIME) {
            //执行放大操作
            applifacation(viewHolder);
            //记录当前拖动中的viewHolder
            selectedViewHolder=viewHolder;
        }
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    /*
    * item长按选中状态的变化
    * */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (isDraging&&selectedViewHolder!=null) {
            //放手后重置所有相关记录
            narrow(selectedViewHolder);
            isDraging=false;
            isGetMovementFlagsDown=GETMOVEMENTFlAGS_NOT_DID_TIME;
            selectedViewHolder=null;
        }
        if (isGetMovementFlagsDown == GETMOVEMENTFlAGS_DID_TIME) {
            isDraging = true;
        }
        super.onSelectedChanged(viewHolder, actionState);
    }



    /*
     * item拖动中
     * */
    @Override
    public float getMoveThreshold(RecyclerView.ViewHolder viewHolder) {
        return super.getMoveThreshold(viewHolder);
    }

    /*
    * 位置发生变化后处理
    * */
    @Override
    public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
        super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
    }

    /*
     * Item位置变化
     * */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return onItemTouchCallbackListener.onMove(recyclerView, viewHolder, target);
    }

    /*
     * Item滑动后事件
     * */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        onItemTouchCallbackListener.onSwiped(viewHolder, direction);
    }

    @Override
    public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
        Log.e("SwipeThreshold","----->");
        return super.getSwipeThreshold(viewHolder);
    }

    /**
     * item是否能够长按拖动
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return canDrag;
    }

    /**
     * item是否能够被滑动
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return canSwipe;
    }

    public void setCanDrag(boolean canDrag) {
        this.canDrag = canDrag;
    }

    public void setCanSwipe(boolean canSwipe) {
        this.canSwipe = canSwipe;
    }

    /*
    * 获取相应ViewHolder的View后的操作(放大缩小)
    * */
    private void applifacation(RecyclerView.ViewHolder viewHolderv) {
        if (viewHolderv instanceof HolderImg) {
            View view = ((HolderImg) viewHolderv).mimg;
            startAnimation(view);
        }
    }

    private void narrow(RecyclerView.ViewHolder viewHolderv) {
        if (viewHolderv instanceof HolderImg) {
            View view = ((HolderImg) viewHolderv).mimg;
            cancelAinimation(view);
        }
    }

    /*
    * Animation
    * */
    public void startAnimation(View view) {
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(view, "scaleX",
                1.0f, 1.08f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(view, "scaleY",
                1.0f, 1.08f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(400);
        animSet.setInterpolator(new LinearInterpolator());
        animSet.playTogether(anim1, anim2);
        animSet.start();
    }

    public void cancelAinimation(View view) {
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(view, "scaleX",
                1.08f, 1.0f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(view, "scaleY",
                1.08f, 1.0f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(400);
        animSet.setInterpolator(new LinearInterpolator());
        animSet.playTogether(anim1, anim2);
        animSet.start();
    }

}
