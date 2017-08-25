package com.yjh.espresso;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class RecyclerViewAdapter extends
        RecyclerView.Adapter<RecyclerViewAdapter.EspressoViewHolder> {

    private List<String> mStringList;

    RecyclerViewAdapter(List<String> stringList) {
        this.mStringList = stringList;
    }

    @Override
    public EspressoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EspressoViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_view, parent, false));
    }


    @Override
    public void onBindViewHolder(EspressoViewHolder holder, int position) {
        holder.textView.setText(mStringList.get(position));
    }

    @Override
    public int getItemCount() {
        return mStringList.size();
    }

    class EspressoViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        EspressoViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);
        }
    }
}
