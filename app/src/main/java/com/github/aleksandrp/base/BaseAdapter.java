package com.github.aleksandrp.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import static android.support.v7.widget.RecyclerView.INVALID_TYPE;

/**
 * Base class representing {@link RecyclerView.Adapter}
 *
 * @param <T> Class-model that representing adapter item
 */

@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseHolder<T>> {

    private static final int TYPE_HEADER = 1;
    private static final int TYPE_FOOTER = 2;
    private static final int TYPE_CONTENT = 3;

    @Nullable
    private List<T> list;

    @Nullable
    private BaseHolder.OnHolderClickListener<T> listener;

    @Nullable
    private OnNeedMoreItemsListener needMoreItemsListener;

    @Nullable
    private BaseHolder<T> headerHolder;

    @Nullable
    private BaseHolder<T> footerHolder;

    private boolean hasHeader;
    private boolean hasFooter;
    private boolean isDownloading;
    private boolean hasMoreItems = true;

    public BaseAdapter(@Nullable List<T> list, @Nullable BaseHolder.OnHolderClickListener<T> listener) {
        this.list = list;
        this.listener = listener;
    }

    public BaseAdapter(@Nullable List<T> list, @Nullable BaseHolder.OnHolderClickListener<T> listener, @Nullable OnNeedMoreItemsListener needMoreItemsListener) {
        this.list = list;
        this.listener = listener;
        this.needMoreItemsListener = needMoreItemsListener;
    }

    public void showHeader() {
        if (!hasHeader) {
            hasHeader = true;
            notifyItemInserted(0);
        }
    }

    public void hideHeader() {
        if (hasHeader) {
            notifyItemRemoved(0);
            hasHeader = false;
        }
    }

    public void showFooter() {
        if (!hasFooter) {
            hasFooter = true;
            notifyItemInserted(getItemCount() - 1);
        }
    }

    public void hideFooter() {
        if (hasFooter) {
            notifyItemRemoved(getItemCount() - 1);
            hasFooter = false;
        }
    }


    @Override
    public BaseHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseHolder<T> holder;
        switch (viewType) {
            case TYPE_HEADER:
                holder = onCreateHeaderViewHolder(parent);
                if (holder == null) {
                    throw new IllegalArgumentException("Need to override the onCreateHeaderViewHolder(ViewGroup) method in your adapter");
                }
                holder.setOnHolderClickListener(listener);
                headerHolder = holder;
                break;
            case TYPE_FOOTER:
                holder = onCreateFooterViewHolder(parent);
                if (holder == null) {
                    throw new IllegalArgumentException("Need to override the onCreateFooterViewHolder(ViewGroup) method in your adapter");
                }
                holder.setOnHolderClickListener(listener);
                footerHolder = holder;
                break;
            default:
                holder = onCreateContentViewHolder(parent);
                holder.setOnHolderClickListener(listener);
                break;
        }
        return holder;
    }

    /**
     * Called when RecyclerView needs a new {@link BaseHolder} of the given type
     * (exclude {@link #TYPE_HEADER} and {@link #TYPE_FOOTER}) to represent
     * an item.
     * <p>
     * Inherit from {@link #onCreateViewHolder(ViewGroup, int)}
     *
     * @param recyclerView The ViewGroup into which the new View will be added after it is bound to
     *                     an adapter position.
     */
    public abstract BaseHolder<T> onCreateContentViewHolder(ViewGroup recyclerView);

    public BaseHolder<T> onCreateHeaderViewHolder(ViewGroup recyclerView) {
        return null;
    }

    public BaseHolder<T> onCreateFooterViewHolder(ViewGroup recyclerView) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder<T> holder, int position) {
        if (isNeedMoreItem(position)) holder.itemView.post(actionLoadMore);
        if (list != null && isContentViewType(position)) {
            T item = list.get(hasHeader ? position - 1 : position);
            if (item != null) holder.bindModel(item);
        }
    }

    @Nullable
    public BaseHolder<T> getHeaderHolder() {
        return headerHolder;
    }

    @Nullable
    public BaseHolder<T> getFooterHolder() {
        return footerHolder;
    }

    private boolean isNeedMoreItem(int position) {
        return needMoreItemsListener != null && !isDownloading && hasMoreItems && position == getItemCount() - 1;
    }

    /**
     * Content view type validation
     *
     * @param position position to query
     * @return <code>true</code>  if view type not header and not footer and not invalid, <code>false</code>
     * otherwise
     */
    private boolean isContentViewType(int position) {
        int viewType = getItemViewType(position);
        return viewType != TYPE_HEADER && viewType != TYPE_FOOTER && viewType != INVALID_TYPE;
    }

    @Override
    public int getItemViewType(int position) {
        if (list == null) return INVALID_TYPE;
        return position == 0 && hasHeader ? TYPE_HEADER : position == getItemCount() - 1 && hasFooter ? TYPE_FOOTER : TYPE_CONTENT;
    }

    @Override
    public int getItemCount() {
        if (list == null) return 0;
        int size = list.size();
        if (hasHeader) size++;
        if (hasFooter) size++;
        return size;
    }

    public void noMoreItems() {
        hasMoreItems = false;
    }

    public synchronized void addNewItems(@Nullable List<T> additionalItems) {
        isDownloading = false;
        if (list == null) {
            list = additionalItems;
            notifyDataSetChanged();
            return;
        }
        if (additionalItems != null) {
            boolean isAdded = list.addAll(additionalItems);
            if (isAdded) notifyDataSetChanged();
        }
    }

    @Nullable
    public synchronized List<T> getItems() {
        return list;
    }

    /**
     * Removes the specified object from the array.
     *
     * @param item The object to remove.
     */
    public synchronized void removeItem(@Nullable T item) {
        if (item == null || list == null) return;
        int removedIndex = list.indexOf(item);
        if (removedIndex >= 0) {
            list.remove(removedIndex);
            if (hasHeader) removedIndex++;
            notifyItemRemoved(removedIndex);
        }
    }

    /**
     * Remove all elements from the list.
     */
    public synchronized void clearItems() {
        if (list != null) {
            list.clear();
            notifyDataSetChanged();
        }
    }

    private Runnable actionLoadMore = new Runnable() {
        @Override
        public void run() {
            if (needMoreItemsListener != null) {
                isDownloading = true;
                needMoreItemsListener.needMoreItems();
            }
        }
    };

    public interface OnNeedMoreItemsListener {
        void needMoreItems();
    }
}
