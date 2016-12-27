package com.example.administrator.itemtouchhelper.itemhelper;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/12/26.
 */

public class MItemMenuTouchListener implements RecyclerView.OnItemTouchListener, GestureDetector.OnGestureListener {

    /**
     * 动画执行的时间
     */
    private static int DURATION = 200;

    /**
     * item是否子拖动
     */
    private boolean mIsDragging;
    /**
     * 手势类用来判断手势滑动
     */
    private GestureDetectorCompat mGestureDetector;
    private CallBack mCallback;
    private Context mContext;

    private Animator mAnimator;
    /**
     * 触发scroll滑动距离
     */
    private int mTouchSlop;
    /**
     * 触发scroll滑动的速度
     */

    private int mMaxVelocity;
    private int mMinVelocity;
    /**
     * 点击button后是否button是否会自动关闭
     */
    private boolean isCloseButton = false;

    private float mLastx;
    private float mLasty;

    /*
    * View
    * */
    private ViewGroup viewGroup;
    private View topView;
    private View bottomView;
    private int topViewWith;
    private int bottomViewWith;

    public interface CallBack{
        ViewGroup getSwipLayout(float x,float y);
    }

    /*
    * 获取当前View
    * */
    public void setViews(ViewGroup viewGroup) {
        this.viewGroup = viewGroup;
        this.topView = viewGroup.getChildAt(1);
        this.bottomView = viewGroup.getChildAt(0);
        this.topViewWith = topView.getMeasuredWidth();
        this.bottomViewWith = bottomView.getMeasuredWidth();
    }
    public void clearViews(){
        this.viewGroup = null;
        this.topView = null;
        this.bottomView = null;
        this.topViewWith = 0;
        this.bottomViewWith = 0;
    }
    public MItemMenuTouchListener(Context context, CallBack callback) {
        mContext = context;
        mCallback = callback;
        mGestureDetector = new GestureDetectorCompat(mContext, this);
        //获取当前相关滑轮信息
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop() / 2;
        mMaxVelocity = configuration.getScaledMaximumFlingVelocity();
        mMinVelocity = configuration.getScaledMinimumFlingVelocity();
    }
    /*
    * 判定当前touch事件是否继续执行
    * return false 阻止事件传递
    * return true 反之
    * */
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        //获取touch事件类型
        int acton = MotionEventCompat.getActionMasked(e);
        //记录手指所在位置(变化时)
        int x = (int) e.getX();
        int y = (int) e.getY();
        /**
         * 如果recycler上下滑动就关闭item
         */
        if (rv.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) {
            if (viewGroup != null) {
                smoothView(true);
                viewGroup = null;
            }
            return false;
        }
        /*
        * 如果动画正在执行则执行onTouchEvent
        * */
        if (mAnimator != null && mAnimator.isRunning()) {
            return true;
        }

        boolean needIntercept = false;
        switch (acton) {
            case MotionEvent.ACTION_DOWN:
                mLastx = e.getX();
                mLasty = e.getY();
                if (viewGroup != null) {
                    //若点击事件在bottomView则执行onTouchEvent
                    if (inView(x, y)){
                        Toast.makeText(mContext,"删除",Toast.LENGTH_SHORT).show();
                        return false;
                    }else {
                        if (isExpanded()){
                            smoothView(true);
                            viewGroup=null;
                            return false;
                        }
                    }
                }
                viewGroup = mCallback.getSwipLayout(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                //移动距离
                int deltaX = (x - (int) mLastx);
                int deltaY = (y - (int) mLasty);
                if (Math.abs(deltaY) > Math.abs(deltaX))
                    return false;
                needIntercept = mIsDragging = (viewGroup != null && Math.abs(deltaX) >= mTouchSlop);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                //(取消滑动动作时)判断当前view是否打开
                if (isExpanded()) {
                    //判断当前点击是否在打开的view中
                    if (!inView(x, y)) {
                        return false; //这样不会将界面直接关闭
                    }
                }
                break;
        }
        return needIntercept;
    }


    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        if (mAnimator != null && mAnimator.isRunning() || viewGroup == null)
            return;

//        if (mGestureDetector.onTouchEvent(e)) {
//            mIsDragging = true;
//            return;
//        }

        int x = (int) e.getX();
        int action = MotionEventCompat.getActionMasked(e);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int offset = (int) (mLastx - x);
                if (mIsDragging) {
                    horizontalDrag(offset);
                }
                mLastx = x;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:

                if (mIsDragging) {
                    if (!smoothView(false) && isClosed())
                        viewGroup = null;
                    mIsDragging = false;
                }
                break;

        }
    }


    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    /*
     * 1.用于判断滑动方向与速度
     * 2.e1---->e2..向量距离每秒滑动距离velocityX,velocityY
     * */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//        if (Math.abs(velocityX) > mMinVelocity && Math.abs(velocityX) < mMaxVelocity) {
//            //若不能执行动画(返回false)
//            if (!smoothView(false)) {
//                //当View处于关闭状态
//                if (isClosed()) {
//                    viewGroup = null;
//                    return true;
//                }
//            }
//            return true;
//        }
        return false;
    }

    /**
     * 判断是不是关闭状态
     *
     * @return
     */
    private boolean isClosed() {
        return viewGroup != null && topView.getScrollX() == 0;
    }
    /**
     * 是否是开启状态
     *
     * @return
     */
    public boolean isExpanded() {
        return viewGroup != null && topView.getScrollX() == bottomViewWith;
    }
    /**
     * 点击位置是否是已经打开的view
     *
     * @param x
     * @param y
     * @return
     */
    private boolean inView(int x, int y) {

        if (viewGroup == null)
            return false;

        int scrollX = topView.getScrollX();
        int left = topViewWith - scrollX;
        int top = viewGroup.getTop();
        int right = left +bottomViewWith;
        int bottom = top+topView.getMeasuredHeight();
        Rect rect = new Rect(left, top, right, bottom);
        //X,Y是否在矩阵内
        return rect.contains(x, y);
    }

    /**
     * 执行滑动动画
     * 传true会关闭按钮位置
     *
     * @return
     */
    private boolean smoothView(Boolean isClose) {
        int scrollX = topView.getScrollX();
        int to = 0;
        int width = bottomViewWith / 2;
        //滑动时间
        int duration = DURATION;
        System.out.println(scrollX);
        if (mAnimator != null) {
            return false;
        }

        if (!isClose) {
            //计算滑动时间
            if (scrollX > width) {
                //打开View(大于菜单一半)
                to = bottomViewWith;
//                duration = (int) (DURATION * (float) (scrollX - width) / width);
            } else {
                //关闭View(小于菜单一半)
                to = 0;
//                duration = (int) (duration * (float) (scrollX) / width);
            }
        } else {
            //关闭View
            to = 0;
//            duration = (int) (duration * (float) (scrollX) / width);
        }
        if (to == scrollX) {
            //若已经滑动到头则不执行动画
            return false;
        }
        mAnimator = ObjectAnimator.ofInt(topView, "scrollX", to);
        mAnimator.setDuration(duration);
        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mAnimator = null;
                if (isClosed()) {
                    viewGroup = null;
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mAnimator = null;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mAnimator.start();

        return true;
    }
    /**
     * 根据touch事件来滚动View的scrollX
     *
     * @param delta
     */
    private void horizontalDrag(int delta) {
        int scrollX = topView.getScrollX();
        int scrollY = topView.getScrollY();
        if ((scrollX + delta) <= 0) {
            if (Math.log(1.25)<0){
                scrollX += delta * 0.4;
            }else if (Math.log(1.25)<0.5){
                scrollX += delta * 0.3;
            }else if (Math.log(1.25)<0.7){
                scrollX += delta * 0.2;
            }else if (Math.log(1.25)<0.9){
                scrollX += delta * 0.1;
            }else {
                scrollX += delta * 0.1;
            }
            topView.scrollTo(scrollX, scrollY);
//            topView.scrollTo(0, scrollY);
            return;
        }
        int horRange = bottomViewWith;
        scrollX += delta;
        if (Math.abs(scrollX) < horRange) {
            topView.scrollTo(scrollX, scrollY);
        } else {
            topView.scrollTo(horRange, scrollY);
        }
    }
}
