package com.raig.uportinfo.genericRecyclerview.Interface;


import com.raig.uportinfo.genericRecyclerview.RecyclerViewHolder;

public interface RecyclerItemClickListener {
    void onRecyclerItemClicked(RecyclerViewHolder holder, Object itemObject, int itemPosition);
}
