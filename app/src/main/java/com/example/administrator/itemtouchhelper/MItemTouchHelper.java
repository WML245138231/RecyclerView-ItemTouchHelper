package com.example.administrator.itemtouchhelper;

import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by Administrator on 2016/12/15.
 */

public class MItemTouchHelper extends ItemTouchHelper {

    public MItemTouchHelper(OnItemTouchCallbackIm onItemTouchCallbackListener) {
        super(new MItemTouchHelperBack(onItemTouchCallbackListener));
    }

}