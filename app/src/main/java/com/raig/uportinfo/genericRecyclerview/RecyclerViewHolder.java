package com.raig.uportinfo.genericRecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    public RecyclerViewHolder(View itemView) {
        super(itemView);
    }
    public RecyclerViewHolder(View itemView, RecyclerViewAdapter adapter) {
        super(itemView);
    }

    protected void setData(Context context, Object itemObject) {}

}
