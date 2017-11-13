package com.github.aleksandrp;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.github.aleksandrp.base.BaseHolder;
import com.github.recyclerbind.R;

/**
 * Created on 13.07.2017
 * Project RecyclerBind
 *
 * @author Aleks Sander
 */

class BurgerFooterHolder extends BaseHolder<BurgerModel> {


    public BurgerFooterHolder(ViewGroup recyclerView) {
        super(recyclerView, R.layout.footer_view, null);
    }

    @Override
    public void bindItem(@NonNull BurgerModel model) {
        //empty
    }
}
