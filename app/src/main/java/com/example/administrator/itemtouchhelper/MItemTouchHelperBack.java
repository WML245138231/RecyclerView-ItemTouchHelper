package com.example.administrator.itemtouchhelper;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by Administrator on 2016/12/15.
 */

public class MItemTouchHelperBack extends ItemTouchHelper.Callback {
    /*
    * 回调监听
    * */
    private OnItemTouchCallbackIm onItemTouchCallbackListener;

    private boolean canDrag = true;
    private boolean canSwipe = true;

    public MItemTouchHelperBack(OnItemTouchCallbackIm onItemTouchCallbackListener) {
        this.onItemTouchCallbackListener = onItemTouchCallbackListener;
    }

    /*
    * Item根据布局设置允许拖动与滑动的方向
    * */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        applifacation(viewHolder);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int dragFlags = 0;
        int swipeFlags = 0;
        if (layoutManager instanceof GridLayoutManager) {
            dragFlags =
                    ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            swipeFlags = 0;
        } else if (layoutManager instanceof LinearLayoutManager) {
            if (((LinearLayoutManager) layoutManager).getOrientation() == LinearLayoutManager.VERTICAL) {
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            } else {
                swipeFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                dragFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            }
        }
        narrow(viewHolder);
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    /*
    * item拖动中
    * */
    @Override
    public float getMoveThreshold(RecyclerView.ViewHolder viewHolder) {
        narrow(viewHolder);
        return super.getMoveThreshold(viewHolder);
    }

    /*
     * Item拖动事件
     * */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return onItemTouchCallbackListener.onMove(recyclerView, viewHolder, target);
    }

    /*
     * Item滑动事件
     * */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        onItemTouchCallbackListener.onSwiped(viewHolder, direction);
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
    * 获取相应ViewHolder的View后的操作
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
                1.0f, 1.1f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(view, "scaleY",
                1.0f, 1.1f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(500);
        animSet.setInterpolator(new LinearInterpolator());
        animSet.playTogether(anim1, anim2);
        animSet.start();
    }

    public void cancelAinimation(View view) {
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(view, "scaleX",
                1.1f, 1.0f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(view, "scaleY",
                1.1f, 1.0f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(500);
        animSet.setInterpolator(new LinearInterpolator());
        animSet.playTogether(anim1, anim2);
        animSet.start();
    }

}
