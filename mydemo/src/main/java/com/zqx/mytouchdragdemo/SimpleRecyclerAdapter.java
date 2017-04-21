package com.zqx.mytouchdragdemo;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ZhangQixiang on 2017/3/4.
 * 单类条目展示的最终RecyclerView.Adapter
 */

public class SimpleRecyclerAdapter<T> extends RecyclerView.Adapter<SimpleRecyclerAdapter.ViewHolder> {

    private List<T>               mDatas;
    private int                   mItemLayout;
    private SimpleAdapterListener<T> mListener;

    public SimpleRecyclerAdapter(List<T> datas, int itemLayout, SimpleAdapterListener listener) {
        mDatas = datas;
        mItemLayout = itemLayout;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(mItemLayout, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        //加载条目时的动画
        //holder.itemView.setScaleX(.9f);
        //ViewCompat.animate(holder.itemView).setDuration(300).scaleX(1).start();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mListener != null) {
            mListener.onBindViewHolder(holder, mDatas.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public interface SimpleAdapterListener<T> {
        void onBindViewHolder(ViewHolder holder, T item);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        SparseArray<View> mViews = new SparseArray<>();

        ViewHolder(View view) {
            super(view);
        }

        public View getViewById(int id) {
            View view = mViews.get(id);
            if (view == null) {
                view = itemView.findViewById(id);
                mViews.put(id, view);
            }
            return view;
        }

        public TextView getTextView(int id) {
            View view = getViewById(id);
            if (view instanceof TextView) {
                return ((TextView) view);
            } else {
                throw new RuntimeException("SimpleRecyclerView: id类型不匹配");
            }
        }

        public ImageView getImageView(int id) {
            View view = getViewById(id);
            if (view instanceof ImageView) {
                return ((ImageView) view);
            } else {
                throw new RuntimeException("SimpleRecyclerView: id类型不匹配");
            }
        }


    }
}
