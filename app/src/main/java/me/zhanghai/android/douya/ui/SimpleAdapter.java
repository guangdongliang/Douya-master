/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.android.douya.ui;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class SimpleAdapter<T, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    private List<T> mList;

    public SimpleAdapter() {
        this(null);
    }

    public SimpleAdapter(List<T> list) {
        mList = list != null ? list : new ArrayList<T>();
    }

    public List<T> getList() {
        return mList;
    }

    public void addAll(Collection<? extends T> collection) {
        int oldSize = mList.size();
        mList.addAll(collection);
        notifyItemRangeInserted(oldSize, collection.size());
    }

    public void replace(Collection<? extends T> collection) {
        mList.clear();
        mList.addAll(collection);
        notifyDataSetChanged();
    }

    public void add(T item) {
        mList.add(item);
        notifyItemInserted(mList.size() - 1);
    }

    public void set(int position, T item) {
        mList.set(position, item);
        notifyItemChanged(position);
    }

    public T remove(int position) {
        T item = mList.remove(position);
        notifyItemRemoved(position);
        return item;
    }

    public void clear() {
        int oldSize = mList.size();
        mList.clear();
        notifyItemRangeRemoved(0, oldSize);
    }

    public int findPositionById(long id) {
        int count = getItemCount();
        for (int i = 0; i < count; ++i) {
            if (getItemId(i) == id) {
                return i;
            }
        }
        return RecyclerView.NO_POSITION;
    }

    public void notifyItemChangedById(long id) {
        int position = findPositionById(id);
        if (position != RecyclerView.NO_POSITION) {
            notifyItemChanged(position);
        }
    }

    public T removeById(long id) {
        int position = findPositionById(id);
        if (position != RecyclerView.NO_POSITION) {
            return remove(position);
        } else {
            return null;
        }
    }

    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
