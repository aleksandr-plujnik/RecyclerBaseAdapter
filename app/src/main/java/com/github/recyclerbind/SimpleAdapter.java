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

public class SimpleAdapter extends BaseAdapter<BurgerModel> {


    public SimpleAdapter(@Nullable List<BurgerModel> list, @Nullable BaseHolder.OnHolderClickListener<BurgerModel> listener) {
        super(list, listener);
    }

    @Override
    public BaseHolder<BurgerModel> onCreateContentViewHolder(ViewGroup recyclerView) {
        return new SimpleHolder(recyclerView);
    }
}
