package com.example.hifza.instantmet;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Fareeha on 1/4/2018.
 */

public class FanAdapter extends RecyclerView.Adapter<FanAdapter.ViewHolder> implements View.OnClickListener {

    private int[] mResIds;
    Context context;

    public FanAdapter(Context context,int[] resIds) {
        this.mResIds = resIds;
        this.context=context;
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int resId);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fans_layout_item, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.imageView.setImageResource(mResIds[position]);
        viewHolder.itemView.setTag(mResIds[position]);
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            public Context context;

            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context,User_profile.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (Integer) v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return mResIds.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            imageView= (ImageView) view.findViewById(R.id.fan_img);

        }
    }
}


