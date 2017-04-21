package com.zqx.mytouchdragdemo;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemStateChangedListener;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements SimpleRecyclerAdapter.SimpleAdapterListener<Integer> {

    @BindView(R.id.rv_drag)
    SwipeMenuRecyclerView mRvDrag;
    @BindView(R.id.rv_data)
    RecyclerView          mRvData;
    private ArrayList<Integer>             mDatas;
    private DragTouchAdapter               mDragAdapter;
    private SimpleRecyclerAdapter<Integer> mDataAdapter;

    //条目移动监听
    private OnItemMoveListener         mOnItemMove  = new OnItemMoveListener() {
        @Override
        public boolean onItemMove(int fromPosition, int toPosition) {
            Collections.swap(mDatas, fromPosition, toPosition);
            mDataAdapter.notifyDataSetChanged();
            mDragAdapter.notifyItemMoved(fromPosition, toPosition);
            mRvData.smoothScrollToPosition(toPosition);
            ToastUtil.show("将第" + fromPosition + "条拖向第" + toPosition + "条");
            return true;
        }

        @Override
        public void onItemDismiss(int position) {//侧滑删除,本demo不开启
            mDatas.remove(position);
            mDragAdapter.notifyItemRemoved(position);
            ToastUtil.show("删除了第" + position + "条");
        }
    };

    //条目拖拽释放状态改变监听
    private OnItemStateChangedListener mOnItemDrag  = new OnItemStateChangedListener() {
        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                if (actionState == OnItemStateChangedListener.ACTION_STATE_DRAG) {

                    viewHolder.itemView.setTranslationZ(15f);
                } else {

                    viewHolder.itemView.setTranslationZ(0f);
                }
            }
        }
    };

    //条目点击监听
    private OnItemClickListener        mOnItemClick = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            ToastUtil.show("点击了第" + position + "条");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initDatas();

        //左边recyclerview
        mRvDrag.setLayoutManager(new LinearLayoutManager(this));
        mRvDrag.setOnItemMoveListener(mOnItemMove);
        mRvDrag.setOnItemStateChangedListener(mOnItemDrag);
        mRvDrag.setHasFixedSize(true);
        mRvDrag.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mDragAdapter = new DragTouchAdapter(mRvDrag, mDatas);
        mDragAdapter.setOnItemClickListener(mOnItemClick);
        mRvDrag.setAdapter(mDragAdapter);

        //右边recyclerview
        mRvData.setLayoutManager(new LinearLayoutManager(this));
        mRvData.setHasFixedSize(true);
        mRvData.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mDataAdapter = new SimpleRecyclerAdapter<>(mDatas, R.layout.item_list_real_data, this);
        mRvData.setAdapter(mDataAdapter);
    }

    private void initDatas() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mDatas.add(i);
        }
    }

    public void notify(View view) {
        mDragAdapter.notifyDataSetChanged();
        mDataAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(SimpleRecyclerAdapter.ViewHolder holder, Integer item) {
        holder.getTextView(R.id.tv_data).setText(((int) item) + "");
    }
}
