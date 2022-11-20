package com.example.hifza.instantmet.fragments;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

;
import com.example.hifza.instantmet.R;
import com.example.hifza.instantmet.User_profile;

/**
 * Created by Fareeha on 1/8/2018.
 */

class LeaderAdapter extends RecyclerView.Adapter<LeaderAdapter.ViewHolder> implements View.OnClickListener {

    private int[] mResIds;
    Context context;

    public LeaderAdapter(Context context,int[] resIds) {
        this.mResIds = resIds;
        this.context=context;
    }

    private LeaderAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int resId);
    }

    @Override
    public LeaderAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.leader_layout_item, viewGroup, false);
        LeaderAdapter.ViewHolder vh = new LeaderAdapter.ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(LeaderAdapter.ViewHolder viewHolder, int position) {
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

    public void setOnItemClickListener(LeaderAdapter.OnRecyclerViewItemClickListener listener) {
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



