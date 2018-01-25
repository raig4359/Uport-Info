package com.raig.uportinfo.genericRecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.raig.uportinfo.genericRecyclerview.Interface.RecyclerItemClickListener;
import com.raig.uportinfo.genericRecyclerview.Interface.RecyclerViewHolderCallBack;
import com.raig.uportinfo.genericRecyclerview.Interface.RecyclerViewHolderViewTypeCallBack;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    RecyclerItemClickListener recyclerItemClickListener;
    RecyclerViewHolderCallBack recyclerViewHolderCallBack;
    RecyclerViewHolderViewTypeCallBack recyclerViewHolderViewTypeCallBack;
    List dataSource = new ArrayList<>();
    List<?> filteredUserList=new ArrayList<>();
    Context context;
    boolean displaySingleRow = true;

    public RecyclerViewAdapter(Context context, List<?> dataSource, RecyclerViewHolderCallBack recyclerViewHolderCallBack, RecyclerItemClickListener recyclerItemClickListener) {
        this.context = context;
        this.dataSource = dataSource;
        filteredUserList=dataSource;
        this.recyclerViewHolderCallBack = recyclerViewHolderCallBack;
        this.recyclerItemClickListener = recyclerItemClickListener;
        displaySingleRow = true;
    }

    public RecyclerViewAdapter(Context context, List<?> dataSource, RecyclerViewHolderViewTypeCallBack recyclerViewHolderViewTypeCallBack, RecyclerItemClickListener recyclerItemClickListener) {
        this.context = context;
        this.dataSource = dataSource;
        this.recyclerViewHolderViewTypeCallBack = recyclerViewHolderViewTypeCallBack;
        this.recyclerItemClickListener = recyclerItemClickListener;
        displaySingleRow = false;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (displaySingleRow) {
            return recyclerViewHolderCallBack.onCreateViewHolder(parent, viewType);
        } else {
            return recyclerViewHolderViewTypeCallBack.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        holder.setData(context, dataSource.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerItemClickListener.onRecyclerItemClicked(holder, dataSource.get(position), position);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (displaySingleRow) {
            return 0;
        }
        return recyclerViewHolderViewTypeCallBack.recyclerItemViewType(position, dataSource.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSource.size() > 0 ? dataSource.size() : 0;
    }

}
