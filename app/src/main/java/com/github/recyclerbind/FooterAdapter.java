package com.github.recyclerbind;

import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.github.recyclerbind.base.BaseAdapter;
import com.github.recyclerbind.base.BaseHolder;

import java.util.List;

/**
 * Created on 18.07.2017
 * Project RecyclerBind
 *
 * @author Aleks Sander
 */

public class FooterAdapter extends BaseAdapter<BurgerModel> {

    public FooterAdapter(@Nullable List<BurgerModel> list) {
        super(list, null);
    }

    @Override
    public BaseHolder<BurgerModel> onCreateContentViewHolder(ViewGroup recyclerView) {
        return new BurgerHolder(recyclerView);
    }

    @Override
    public BaseHolder<BurgerModel> onCreateFooterViewHolder(ViewGroup recyclerView) {
        return new BurgerFooterHolder(recyclerView);
    }
}
