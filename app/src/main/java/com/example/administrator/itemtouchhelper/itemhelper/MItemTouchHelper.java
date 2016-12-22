package com.example.administrator.itemtouchhelper.itemhelper;

import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.administrator.itemtouchhelper.itemhelper.minterface.OnItemTouchCallbackIm;

/**
 * Created by Administrator on 2016/12/15.
 */

public class MItemTouchHelper extends ItemTouchHelper {

    public MItemTouchHelper(OnItemTouchCallbackIm onItemTouchCallbackListener) {
        super(new MItemTouchHelperBack(onItemTouchCallbackListener));
    }

}