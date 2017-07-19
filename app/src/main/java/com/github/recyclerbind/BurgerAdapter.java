package com.github.recyclerbind;

import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.github.recyclerbind.base.BaseAdapter;
import com.github.recyclerbind.base.BaseHolder;

import java.util.List;

class BurgerAdapter extends BaseAdapter<BurgerModel> {


    public BurgerAdapter(@Nullable List<BurgerModel> list,
                         @Nullable OnNeedMoreItemsListener needMoreItemsListener) {
        super(list, null, needMoreItemsListener);
    }

    @Override
    public BaseHolder<BurgerModel> onCreateContentViewHolder(ViewGroup parent) {
        return new BurgerHolder(parent);
    }

    @Override
    public BaseHolder<BurgerModel> onCreateHeaderViewHolder(ViewGroup parent) {
        return  new BurgerHeaderHolder(parent);
    }

    @Override
    public BaseHolder<BurgerModel> onCreateFooterViewHolder(ViewGroup parent) {
        return  new BurgerFooterHolder(parent);
    }
}
