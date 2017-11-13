package com.github.aleksandrp.base;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Should be used as base class for all {@link RecyclerView.ViewHolder}
 *
 * @param <T> Class-model that representing adapter item
 */
@SuppressWarnings("WeakerAccess")
public abstract class BaseHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener {

    @Nullable
    private OnHolderClickListener<T> listener;

    @Nullable
    private T item;

    public BaseHolder(ViewGroup recyclerView, @LayoutRes int layoutId, @Nullable int[] viewIds) {
        super(createView(recyclerView, layoutId));
        initListeners(viewIds);
    }

    private void initListeners(@Nullable int[] viewIds) {
        itemView.setOnClickListener(this);
        if (viewIds == null) return;
        for (int id : viewIds) {
            final View view = itemView.findViewById(id);
            if (view != null) view.setOnClickListener(this);
        }
    }

    void bindModel(@NonNull T model) {
        this.item = model;
        bindItem(model);
    }

    public abstract void bindItem(@NonNull T model);

    @Override
    public void onClick(View v) {
        if (listener != null) listener.onHolderViewClick(v, item);
    }

    void setOnHolderClickListener(@Nullable OnHolderClickListener<T> listener) {
        this.listener = listener;
    }

    private static View createView(ViewGroup recyclerView, @LayoutRes int id) {
        return LayoutInflater.from(recyclerView.getContext()).inflate(id, recyclerView, false);
    }

    public interface OnHolderClickListener<T> {
        void onHolderViewClick(@NonNull View view, @Nullable T t);
    }
}
