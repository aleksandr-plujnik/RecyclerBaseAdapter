package com.github.recyclerbind;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.github.recyclerbind.base.BaseHolder;

/**
 * Created on 13.07.2017
 * Project RecyclerBind
 *
 * @author Aleks Sander
 */

class BurgerHeaderHolder extends BaseHolder<BurgerModel> {

    private static int[] clickedViews = new int[]{R.id.btHeaderClick, R.id.tvTitle1};

    BurgerHeaderHolder(ViewGroup recyclerView) {
        super(recyclerView, R.layout.header_view, clickedViews);
    }

    @Override
    public void bindItem(@NonNull BurgerModel model) {
        //empty
    }
}
