package com.example.administrator.itemtouchhelper.tools;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2016/12/23.
 */

public class MRelativeLayout extends RelativeLayout {

    public View topView;
    public View bottomView;
    public int topViewWidth;
    public int bottomViewWidth;

    public MRelativeLayout(Context context) {
        super(context);
    }

    public MRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (bottomView!=null){
            bottomViewWidth=bottomView.getMeasuredWidth();
            int heigh=bottomView.getMeasuredHeight();
            bottomView.layout(l-bottomViewWidth,0,r,heigh);
        }
        if(topView != null){
            topViewWidth = topView.getMeasuredWidth();
            int height = topView.getMeasuredHeight();
            topView.layout(l,0,r,height);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        bottomView = getChildAt(0);
        topView = getChildAt(1);
    }

}
