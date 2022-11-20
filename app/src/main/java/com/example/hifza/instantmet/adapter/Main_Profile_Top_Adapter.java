package com.example.hifza.instantmet.adapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hifza.instantmet.FansActivity;
import com.example.hifza.instantmet.ProfileActivity;
import com.example.hifza.instantmet.R;
import com.example.hifza.instantmet.User_profile;
import com.example.hifza.instantmet.login.Registration_Activity;
import com.example.hifza.instantmet.login.Session;
import com.example.hifza.instantmet.model.Data;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Fareeha on 1/14/2018.
 */

public class Main_Profile_Top_Adapter  extends RecyclerView.Adapter<Main_Profile_Top_Adapter.MyViewHolder> {
    Context context;
    ArrayList<Data> configlist;
    int res;
    String out;
    Date dateObj,timeObj;
    Session session;boolean login;
    int val;

    public Main_Profile_Top_Adapter(Context context, int res, ArrayList<Data> configlist, int val) {
        this.context = context;
        this.configlist = configlist;
        this.val=val;
        session = new Session(context);

        this.res = res;

    }


    @Override
    public Main_Profile_Top_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout


        View v = LayoutInflater.from(parent.getContext()).inflate(res, parent, false);
        // set the view's size, margins, paddings and layout parameters

        Main_Profile_Top_Adapter.MyViewHolder vh = new Main_Profile_Top_Adapter.MyViewHolder(v); // pass the view to View Holder
        return vh;
    }



    @Override
    public void onBindViewHolder(final Main_Profile_Top_Adapter.MyViewHolder holder, final int position) {
if(val==1) {

    Picasso.with(context).load(configlist.get(position).getDp()).resize(120, 140).into(holder.dp_img);

    holder.name_tv.setText(configlist.get(position).getName());


    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View v) {

            login = session.isLoggedIn();
            if (login == true) {
                String id = configlist.get(position).getId();
                Session session = new Session(context);
                final String login_id = session.getUserId();
                if (login_id.equals(id)) {
                    Intent intent = new Intent(context, ProfileActivity.class);

                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(context, R.anim.animation1, R.anim.animation2).toBundle();
                    context.startActivity(intent, bndlanimation);

                } else {
                    Intent intent = new Intent(context, User_profile.class);
                    intent.putExtra("id", configlist.get(position).getId());
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(context, R.anim.animation1, R.anim.animation2).toBundle();
                    context.startActivity(intent, bndlanimation);
                }
            } else {
                Intent intent = new Intent(context, Registration_Activity.class);

                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(context, R.anim.animation1, R.anim.animation2).toBundle();
                context.startActivity(intent, bndlanimation);
            }
        }
    });
}
        if(val==2) {

            Picasso.with(context).load(configlist.get(position).getDp()).resize(120, 140).into(holder.dp_img);

            holder.name_tv.setText(configlist.get(position).getName());
            holder.desc_tv.setText(configlist.get(position).getU_status());


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {

                    login = session.isLoggedIn();
                    if (login == true) {
                        String id = configlist.get(position).getId();
                        Session session = new Session(context);
                        final String login_id = session.getUserId();
                        if (login_id.equals(id)) {
                            Intent intent = new Intent(context, ProfileActivity.class);

                            Bundle bndlanimation = ActivityOptions.makeCustomAnimation(context, R.anim.animation1, R.anim.animation2).toBundle();
                            context.startActivity(intent, bndlanimation);

                        } else {
                            Intent intent = new Intent(context, User_profile.class);
                            intent.putExtra("id", configlist.get(position).getId());
                            Bundle bndlanimation = ActivityOptions.makeCustomAnimation(context, R.anim.animation1, R.anim.animation2).toBundle();
                            context.startActivity(intent, bndlanimation);
                        }
                    } else {
                        Intent intent = new Intent(context, Registration_Activity.class);

                        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(context, R.anim.animation1, R.anim.animation2).toBundle();
                        context.startActivity(intent, bndlanimation);
                    }
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return configlist.size();
    }


    public static  class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        ProgressBar progressBar;
        ImageView post_image;
        CircleImageView dp_img;
        TextView name_tv,desc_tv;


        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            progressBar=itemView.findViewById(R.id.progress_bar);
            dp_img=itemView.findViewById(R.id.item_sticker);


            name_tv=itemView.findViewById(R.id.list_name);
            desc_tv=itemView.findViewById(R.id.desc);

        }
    }
    public  void setFilter(ArrayList<Data> list)
    {
        configlist=new ArrayList<>();
        configlist.addAll(list);
        notifyDataSetChanged();
    }

}
