package com.example.hifza.instantmet.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hifza.instantmet.Encounter;
import com.example.hifza.instantmet.MainActivity;
import com.example.hifza.instantmet.Popular_Activity;
import com.example.hifza.instantmet.ProfileActivity;
import com.example.hifza.instantmet.R;
import com.example.hifza.instantmet.User_profile;
import com.example.hifza.instantmet.json.ProfileJsonParsing;
import com.example.hifza.instantmet.login.Registration_Activity;
import com.example.hifza.instantmet.login.Session;
import com.example.hifza.instantmet.login.Signup_Activity;
import com.example.hifza.instantmet.model.Data;
import com.example.hifza.instantmet.model.Dataarray;
import com.example.hifza.instantmet.model.Pojo;
import com.example.hifza.instantmet.transformImage.RoundedCornersTransform;
import com.example.hifza.instantmet.uploadselfie.Upload_Iimage;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Fareeha on 1/14/2018.
 */

public class Random_Profile_Adapter  extends RecyclerView.Adapter<Random_Profile_Adapter.MyViewHolder> {

    Context context;
    ArrayList<Data> configlist, detail;
    int res;
    String out;
    Date dateObj, timeObj;
    Session session;
    boolean login;
    int val;

    public Random_Profile_Adapter(Context context, int res, ArrayList<Data> configlist, int val) {
        this.context = context;
        this.configlist = configlist;
        this.val = val;
        session = new Session(context);
        this.res = res;

    }

    public Random_Profile_Adapter(Context context, int res, ArrayList<Data> configlist, ArrayList<Data> detail, int val) {
        this.context = context;
        this.configlist = configlist;
        this.val = val;
        session = new Session(context);
        this.res = res;
        this.detail = detail;
//Toast.makeText(context,detail.get(0).getName(),Toast.LENGTH_SHORT).show();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout


        View v = LayoutInflater.from(parent.getContext()).inflate(res, parent, false);
        // set the view's size, margins, paddings and layout parameters

        MyViewHolder vh = new MyViewHolder(v, context); // pass the view to View Holder
        return vh;
    }


    @Override
    public void onBindViewHolder(final Random_Profile_Adapter.MyViewHolder holder, final int position) {
        if (val == 1) {
            viewMainPostLikes(holder, configlist.get(position).getId());
            checkMainPostLikes(holder, configlist.get(position).getId());
            String time = configlist.get(position).getCreated_at();
            Date date = null;
            try {
                date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String newString = new SimpleDateFormat("HH:mm").format(date);

            holder.image_share.setImageResource(R.drawable.share_image_icon);
            holder.like_img.setImageResource(R.drawable.icon_like);
            holder.name_tv.setText(configlist.get(position).getName());
            holder.likes_tv.setText(configlist.get(position).getTotallike());
            holder.time_tv.setText(newString.toString());
            Picasso.with(context).load(configlist.get(position).getDp()).resize(50, 50).into(holder.dp_img);
            Picasso.with(context).load(configlist.get(position).getPostimage()).transform(new RoundedCornersTransform(21, 1)).resize(480, 250).centerCrop().into(holder.post_image);

            if (configlist.get(position).getDescription() != null) {
                holder.status_tv.setVisibility(View.VISIBLE);
                holder.status_tv.setText(configlist.get(position).getDescription());
            }

            //  holder.name_tv.setText(configlist.get(position).getName());

            if (configlist.get(position).getImagefrom().equals("Gallery")) {
                holder.img_gallery.setImageResource(R.drawable.gallery);
                holder.desc_tv.setText("Gallery");
            } else {
                holder.img_gallery.setImageResource(R.drawable.cam_icon);
                holder.desc_tv.setText("Camera");
            }


            holder.share_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareImage(holder);
                }
            });
            holder.image_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareImage(holder);
                }
            });

            holder.dp_img.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {

                    login = session.isLoggedIn();
                    if (login == true) {
                        String id = configlist.get(position).getUid();
                        Session session = new Session(context);
                        final String login_id = session.getUserId();
                        if (login_id.equals(id)) {
                            Intent intent = new Intent(context, ProfileActivity.class);
                            Bundle bndlanimation = ActivityOptions.makeCustomAnimation(context, R.anim.animation1, R.anim.animation2).toBundle();
                            context.startActivity(intent, bndlanimation);
                        } else {
                            Intent intent = new Intent(context, User_profile.class);
                            intent.putExtra("id", configlist.get(position).getUid());
                            //  Bundle bndlanimation = ActivityOptions.makeCustomAnimation(context, R.anim.animation1, R.anim.animation2).toBundle();
                            context.startActivity(intent);
                        }
                    } else {
                        Intent intent = new Intent(context, Registration_Activity.class);

                        //Bundle bndlanimation = ActivityOptions.makeCustomAnimation(context, R.anim.animation1, R.anim.animation2).toBundle();
                        context.startActivity(intent);
                    }
                }
            });
            holder.name_tv.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {

                    login = session.isLoggedIn();
                    if (login == true) {
                        String id = configlist.get(position).getUid();
                        Session session = new Session(context);
                        final String login_id = session.getUserId();
                        if (login_id.equals(id)) {
                            Intent intent = new Intent(context, ProfileActivity.class);

                            // Bundle bndlanimation = ActivityOptions.makeCustomAnimation(context, R.anim.animation1,R.anim.animation2).toBundle();
                            context.startActivity(intent);
                        } else {
                            Intent intent = new Intent(context, User_profile.class);
                            intent.putExtra("id", configlist.get(position).getUid());
                            // Bundle bndlanimation = ActivityOptions.makeCustomAnimation(context, R.anim.animation1,R.anim.animation2).toBundle();
                            context.startActivity(intent);
                        }
                    } else {
                        Intent intent = new Intent(context, Registration_Activity.class);

                        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(context, R.anim.animation1, R.anim.animation2).toBundle();
                        context.startActivity(intent, bndlanimation);
                    }
                }
            });
            holder.post_image.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {
                    login = session.isLoggedIn();
                    if (login == true) {
                        showDirectionsDialog(configlist.get(position).getId(), configlist.get(0).getDp(), configlist.get(position).getPostimage(), configlist.get(0).getName(), configlist.get(position).getDescription(), configlist.get(position).getTotallike());
                    } else {
                        Intent intent = new Intent(context, Registration_Activity.class);

                        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(context, R.anim.animation1, R.anim.animation2).toBundle();
                        context.startActivity(intent, bndlanimation);
                    }
                }
            });
        } else if (val == 2) {
            Picasso.with(context).load(configlist.get(position).getPostimage()).resize(280, 150).into(holder.post_image);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    profileDialog(configlist.get(position).getId(), detail.get(0).getDp(), configlist.get(position).getPostimage(), detail.get(0).getName(), configlist.get(position).getDescription(), configlist.get(position).getTotallike(), configlist.get(position).getImagefrom());
                }
            });
        } else if (val == 6) {
            Picasso.with(context).load(configlist.get(position).getPostimage()).resize(280, 150).into(holder.post_image);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDirectionsDialog(configlist.get(position).getId(), detail.get(0).getDp(), configlist.get(position).getPostimage(), detail.get(0).getName(), configlist.get(position).getDescription(), configlist.get(position).getTotallike());
                }
            });
        } else if (val == 3) {
            Picasso.with(context).load(configlist.get(position).getDp()).into(holder.dp_img);
            holder.name_tv.setText(configlist.get(position).getName());
            holder.desc_tv.setText(configlist.get(position).getU_status());
            checkFollower(holder.itemView, configlist.get(position).getUid());
            holder.dp_img.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {

                    login = session.isLoggedIn();
                    if (login == true) {
                        String id = configlist.get(position).getUid();
                        Session session = new Session(context);
                        final String login_id = session.getUserId();
                        if (login_id.equals(id)) {
                            Intent intent = new Intent(context, ProfileActivity.class);
                            //Bundle bndlanimation = ActivityOptions.makeCustomAnimation(context, R.anim.animation1, R.anim.animation2).toBundle();
                            context.startActivity(intent);
                        } else {
                            Intent intent = new Intent(context, User_profile.class);
                            intent.putExtra("id", configlist.get(position).getUid());
                            //Bundle bndlanimation = ActivityOptions.makeCustomAnimation(context, R.anim.animation1, R.anim.animation2).toBundle();
                            context.startActivity(intent);
                        }
                    } else {
                        Intent intent = new Intent(context, Registration_Activity.class);

                        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(context, R.anim.animation1, R.anim.animation2).toBundle();
                        context.startActivity(intent, bndlanimation);
                    }
                }
            });
            holder.name_tv.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {

                    login = session.isLoggedIn();
                    if (login == true) {
                        String id = configlist.get(position).getUid();
                        Session session = new Session(context);
                        final String login_id = session.getUserId();
                        if (login_id.equals(id)) {
                            Intent intent = new Intent(context, ProfileActivity.class);
                            //  Bundle bndlanimation = ActivityOptions.makeCustomAnimation(context, R.anim.animation1, R.anim.animation2).toBundle();
                            context.startActivity(intent);
                        } else {
                            Intent intent = new Intent(context, User_profile.class);
                            intent.putExtra("id", configlist.get(position).getUid());
                            //  Bundle bndlanimation = ActivityOptions.makeCustomAnimation(context, R.anim.animation1, R.anim.animation2).toBundle();
                            context.startActivity(intent);
                        }
                    } else {
                        Intent intent = new Intent(context, Registration_Activity.class);

                        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(context, R.anim.animation1, R.anim.animation2).toBundle();
                        context.startActivity(intent, bndlanimation);
                    }
                }
            });

        } else if (val == 4) {

            Picasso.with(context).load(configlist.get(position).getDp()).into(holder.dp_img);

            holder.name_tv.setText(configlist.get(position).getName());
            switch (position) {
                case 0:
                    holder.numbering_txt.setVisibility(View.INVISIBLE);
                    holder.img_rank.setVisibility(View.VISIBLE);
                    holder.img_rank.setImageResource(R.drawable.rank_1);
                    break;
                case 1:
                    holder.numbering_txt.setVisibility(View.INVISIBLE);
                    holder.img_rank.setVisibility(View.VISIBLE);
                    holder.img_rank.setImageResource(R.drawable.rank_2);
                    break;
                case 2:
                    holder.numbering_txt.setVisibility(View.INVISIBLE);
                    holder.img_rank.setVisibility(View.VISIBLE);
                    holder.img_rank.setImageResource(R.drawable.rank_3);
                    break;
                default:
                    holder.numbering_txt.setVisibility(View.VISIBLE);
                    holder.img_rank.setVisibility(View.INVISIBLE);
                    holder.numbering_txt.setText(String.valueOf(position + 1));
                    break;


            }

            holder.dp_img.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {

                    login = session.isLoggedIn();
                    if (login == true) {
                        String id = configlist.get(position).getUid();
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

            holder.name_tv.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {

                    login = session.isLoggedIn();
                    if (login == true) {
                        String id = configlist.get(position).getUid();
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

            checkFollower(holder.itemView, configlist.get(position).getId());

        } else if (val == 5) {
            Picasso.with(context).load(configlist.get(position).getDp()).into(holder.dp_img);
            holder.name_tv.setText(configlist.get(position).getName());
            holder.desc_tv.setText(configlist.get(position).getU_status());
            checkFollower(holder.itemView, configlist.get(position).getId());
            holder.dp_img.setOnClickListener(new View.OnClickListener() {
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
                            //  Bundle bndlanimation = ActivityOptions.makeCustomAnimation(context, R.anim.animation1, R.anim.animation2).toBundle();
                            context.startActivity(intent);
                        } else {
                            Intent intent = new Intent(context, User_profile.class);
                            intent.putExtra("id", configlist.get(position).getId());
                            // Bundle bndlanimation = ActivityOptions.makeCustomAnimation(context, R.anim.animation1, R.anim.animation2).toBundle();
                            context.startActivity(intent);
                        }
                    } else {
                        Intent intent = new Intent(context, Registration_Activity.class);

                        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(context, R.anim.animation1, R.anim.animation2).toBundle();
                        context.startActivity(intent, bndlanimation);
                    }
                }
            });
            holder.name_tv.setOnClickListener(new View.OnClickListener() {
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
        //  return configlist.size();
        return (configlist == null) ? 0 : configlist.size();

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        LinearLayout follow_linear;
        ProgressBar progressBar;
        ImageView post_image, img_rank, img_gallery, image_share, like_img;
        CircleImageView dp_img;
        TextView name_tv, desc_tv, status_tv, time_tv, likes_tv, share_tv, numbering_txt;
        Context context;


        @SuppressLint("WrongViewCast")
        public MyViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            // get the reference of item view's

            image_share = itemView.findViewById(R.id.imageshare);
            follow_linear = itemView.findViewById(R.id.follow_btn);
            like_img = itemView.findViewById(R.id.imageView2);
            progressBar = itemView.findViewById(R.id.progress_bar);
            dp_img = itemView.findViewById(R.id.item_sticker);
            numbering_txt = itemView.findViewById(R.id.numbering);
            img_rank = (ImageView) itemView.findViewById(R.id.imageView7);
            post_image = (ImageView) itemView.findViewById(R.id.imageee);
            img_gallery = (ImageView) itemView.findViewById(R.id.img_gal);
            name_tv = itemView.findViewById(R.id.list_name);
            //Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/myriadpro.otf");
            desc_tv = itemView.findViewById(R.id.desc);
            // desc_tv.setTypeface(custom_font);
            share_tv = itemView.findViewById(R.id.comnt);
            // share_tv.setTypeface(custom_font);
            status_tv = itemView.findViewById(R.id.textdesc);
            // status_tv.setTypeface(custom_font);
            time_tv = itemView.findViewById(R.id.time);
            // time_tv.setTypeface(custom_font);
            likes_tv = itemView.findViewById(R.id.likes);
            //  likes_tv.setTypeface(custom_font);
        }
    }

    private void showDirectionsDialog(String id, String dp, String img, String name, String post, String like) {
//        MyLog.i(TAG, "***************number " + brandLocation);
        final Dialog pDialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);

        final View layout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.common_popup_screen, null);
        // pDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        pDialog.addContentView(layout,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                        ViewGroup.LayoutParams.FILL_PARENT));
        pDialog.getWindow().setGravity(Gravity.CENTER);
        pDialog.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        pDialog.setCancelable(false);
        pDialog.show();
        ImageView post_img = layout.findViewById(R.id.popup_img);
        final RelativeLayout edt_img = layout.findViewById(R.id.menu_click);

        RelativeLayout relativeLayout_share = layout.findViewById(R.id.share_btn);

        Glide.with(context).load(img).into(post_img);
        final String check_id = id;
        final TextView post_tv = (TextView) layout.findViewById(R.id.desc_txt);
        post_tv.setText(post);
        //final TextView share_tv = (TextView) layout.findViewById(R.id.list_name);
        final TextView likes_tv = (TextView) layout.findViewById(R.id.likes);
        likes_tv.setText(like);
        viewLikes(layout, id);
        checkLikes(layout, check_id, like);


        final boolean state2;


        relativeLayout_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharePopupPost(layout);
            }
        });


        pDialog.setCanceledOnTouchOutside(true);
    }

    // Profile Activity Popup
    private void profileDialog(final String id, String dp, final String img, String name, final String post, String like, final String image_from) {
//        MyLog.i(TAG, "***************number " + brandLocation);
        final Dialog pDialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);

        final View layout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.profile_popup_screen, null);
        // pDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        pDialog.addContentView(layout,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                        ViewGroup.LayoutParams.FILL_PARENT));
        pDialog.getWindow().setGravity(Gravity.CENTER);
        pDialog.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        pDialog.setCancelable(false);
        pDialog.show();
        ImageView post_img = layout.findViewById(R.id.popup_img);
        final RelativeLayout edt_img = layout.findViewById(R.id.menu_click);

        RelativeLayout relativeLayout_share = layout.findViewById(R.id.share_btn);

        Glide.with(context).load(img).into(post_img);
        final String check_id = id;
        final TextView post_tv = (TextView) layout.findViewById(R.id.desc_txt);
        post_tv.setText(post);
        //final TextView share_tv = (TextView) layout.findViewById(R.id.list_name);
        final TextView likes_tv = (TextView) layout.findViewById(R.id.likes);
        likes_tv.setText(like);
        viewLikes(layout, id);
        checkLikes(layout, check_id, like);


        final boolean state2;


        relativeLayout_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharePopupPost(layout);
            }
        });
        edt_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //Creating the instance of PopupMenu
                final PopupMenu popup = new PopupMenu(context, edt_img);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.image_drop_down, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete:

                                deletePost(v, id);


                                return true;
                            case R.id.edit:
                                popup.dismiss();
                                Intent intent = new Intent(context, Upload_Iimage.class);
                                intent.putExtra("image", img);
                                intent.putExtra("id", id);
                                intent.putExtra("from", image_from);
                                intent.putExtra("status", post);
                                context.startActivity(intent);
                                return true;


                        }

                        return true;
                    }
                });

                popup.show();
            }
        });

        pDialog.setCanceledOnTouchOutside(true);
    }

    public void checkFollower(final View view, String user_id) {
        Session session = new Session(context);
        final String login_id = session.getUserId();
        final String id = user_id;
        final String api_token = session.getApiToken();
        String check_follow_url = "api/v1/checkfollow/" + login_id + "/" + id + "?api_token=" + api_token;
        String url = "http://selfie.todaylike.club/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProfileJsonParsing service = retrofit.create(ProfileJsonParsing.class);

        Call<Pojo> call = service.getUserDetail(check_follow_url);

        call.enqueue(new retrofit2.Callback<Pojo>() {
            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                LinearLayout follow_linear;
                follow_linear = view.findViewById(R.id.follow_btn);
                try {


                    Pojo object = response.body();
                    if (object.getData().toString().equals("Yes")) {

                        TextView follow_tv = view.findViewById(R.id.follow_txt);
                        ImageView ticmark_img = view.findViewById(R.id.rep_img);
                        // follow_tv.setText("Following");
                        follow_linear.setVisibility(View.INVISIBLE);
                        ticmark_img.setVisibility(View.VISIBLE);
                        ticmark_img.setImageResource(R.drawable.ticmark);


                    } else {
                        follow_linear.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String user_follow_url = "api/v1/follow/" + login_id + "/" + id + "?api_token=" + api_token;
                                getFollower(user_follow_url, view);
                            }
                        });
                    }

                    // Toast.makeText(context,object.getStatus().toString(),Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.d("onResponse", e.toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {
                Log.d("onFailure", t.toString());
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getFollower(String follow_url, final View view) {
        String url = "http://selfie.todaylike.club/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProfileJsonParsing service = retrofit.create(ProfileJsonParsing.class);

        Call<Pojo> call = service.getUserDetail(follow_url);

        call.enqueue(new retrofit2.Callback<Pojo>() {
            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                LinearLayout follow_linear;
                follow_linear = view.findViewById(R.id.follow_btn);
                try {

                    Pojo object = response.body();
                    if (object.getResponse().toString().equals("true")) {
                        TextView follow_tv = view.findViewById(R.id.follow_txt);
                        ImageView ticmark_img = view.findViewById(R.id.rep_img);
                        // follow_tv.setText("Following");
                        follow_linear.setVisibility(View.INVISIBLE);
                        ticmark_img.setVisibility(View.VISIBLE);
                        ticmark_img.setImageResource(R.drawable.ticmark);


                    }


                    Toast.makeText(context, object.getStatus().toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.d("onResponse", e.toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {
                Log.d("onFailure", t.toString());
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void checkLikes(final View view, String user_id, final String total_likes) {
        Session session = new Session(context);
        final String login_id = session.getUserId();
        final String id = user_id;
        final String api_token = session.getApiToken();
        String check_follow_url = "api/v1/checklike/" + login_id + "/" + id + "?api_token=" + api_token;
        String url = "http://selfie.todaylike.club/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProfileJsonParsing service = retrofit.create(ProfileJsonParsing.class);

        Call<Pojo> call = service.getUserDetail(check_follow_url);

        call.enqueue(new retrofit2.Callback<Pojo>() {
            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                try {
                    RelativeLayout like_btn = view.findViewById(R.id.like_btn);
                    Pojo object = response.body();
                    // Toast.makeText(context,object.getData().toString(),Toast.LENGTH_SHORT).show();
                    if (object.getData().toString().equals("Yes")) {
                        TextView like_tv = view.findViewById(R.id.likes);
                        ImageView ticmark_img = view.findViewById(R.id.imageView_like);
                        // int count= Integer.valueOf(like_tv.getText().toString());
                        //  like_tv.setText(String.valueOf(count++));

                        ticmark_img.setImageResource(R.drawable.fb_change);
                        // like_tv.setText(total_likes);


                    } else {
                        like_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String user_like_url = "api/v1/like/" + login_id + "/" + id + "?api_token=" + api_token;
                                //  Toast.makeText(context,"button clicked",Toast.LENGTH_SHORT).show();
                                getLikes(user_like_url, view);
                            }
                        });
                    }


                } catch (Exception e) {
                    Log.d("onResponse", e.toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {
                Log.d("onFailure", t.toString());
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getLikes(String follow_url, final View view) {
        String url = "http://selfie.todaylike.club/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProfileJsonParsing service = retrofit.create(ProfileJsonParsing.class);

        Call<Pojo> call = service.getUserDetail(follow_url);

        call.enqueue(new retrofit2.Callback<Pojo>() {
            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                try {

                    Pojo object = response.body();
                    if (object.getResponse().toString().equals("true")) {
                        TextView like_tv = view.findViewById(R.id.likes);
                        ImageView ticmark_img = view.findViewById(R.id.imageView_like);
                        String count = like_tv.getText().toString();
                        int add = Integer.valueOf(count);
                        add++;
                        like_tv.setText(String.valueOf(add));
                        ticmark_img.setImageResource(R.drawable.fb_change);


                    }


                } catch (Exception e) {
                    Log.d("onResponse", e.toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {
                Log.d("onFailure", t.toString());
                //   Toast.makeText(context,t.toString(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void viewLikes(final View view, final String user_id) {
        Session session = new Session(context);
        final String api_token = session.getApiToken();
        String user_like_url = "api/v1/likeview/" + user_id + "?api_token=" + api_token;
        String url = "http://selfie.todaylike.club/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProfileJsonParsing service = retrofit.create(ProfileJsonParsing.class);

        Call<Pojo> call = service.getUserDetail(user_like_url);

        call.enqueue(new retrofit2.Callback<Pojo>() {
            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                try {

                    Pojo object = response.body();
                    Dataarray data = object.getDataarray();
                    ArrayList<Data> post_detail = (ArrayList<Data>) data.getPost_detail();
                    if (object.getStatus().toString().equals("success")) {
                        TextView like_tv = view.findViewById(R.id.likes);


                        like_tv.setText(String.valueOf(post_detail.get(0).getTotal_like()));


                        //Toast.makeText(context,post_detail.get(0).getTotal_Like,Toast.LENGTH_SHORT).show();
                        // Toast.makeText(context,user_id,Toast.LENGTH_SHORT).show();

                    }


                } catch (Exception e) {
                    Log.d("onResponse", e.toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {
                Log.d("onFailure", t.toString());
                //   Toast.makeText(context,t.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void sharePopupPost(final View view) {
        ImageView post_image = view.findViewById(R.id.popup_img);
        TextView share_tv = view.findViewById(R.id.desc_txt);
        String status = share_tv.getText().toString();
        post_image.setDrawingCacheEnabled(true);
        Bitmap icon = post_image.getDrawingCache();

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        icon.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg");
        try {
            file.createNewFile();
            FileOutputStream fo = new FileOutputStream(file);
            fo.write(bytes.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("*/*");
        Uri uri = Uri.parse("file:///sdcard/temporary_file.jpg");
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.putExtra(Intent.EXTRA_TEXT, status);
        context.startActivity(Intent.createChooser(shareIntent, "Share Image"));

    }

    public void deletePost(final View view, final String post_id) {

        Session session = new Session(context);
        final String login_id = session.getUserId();
        final String api_token = session.getApiToken();
        String user_like_url = "api/v1/delpost/" + post_id + "?api_token=" + api_token;
        String url = "http://selfie.todaylike.club/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProfileJsonParsing service = retrofit.create(ProfileJsonParsing.class);

        Call<Pojo> call = service.getUserDetail(user_like_url);

        call.enqueue(new retrofit2.Callback<Pojo>() {
            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                try {

                    Pojo object = response.body();

                    if (object.getStatus().toString().equals("success")) {


                        Toast.makeText(context, "Delete Successfully", Toast.LENGTH_SHORT).show();

                        ((Activity) context).finish();
                        view.getContext().startActivity(new Intent(view.getContext(), ProfileActivity.class));

                        notifyDataSetChanged();
                        // Toast.makeText(context,user_id,Toast.LENGTH_SHORT).show();

                    }


                } catch (Exception e) {
                    Log.d("onResponse", e.toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {
                Log.d("onFailure", t.toString());
                //   Toast.makeText(context,t.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }


    //Main Activity methods

    public void shareImage(final Random_Profile_Adapter.MyViewHolder holder) {

        String status = holder.status_tv.getText().toString();
        holder.post_image.setDrawingCacheEnabled(true);
        Bitmap icon = holder.post_image.getDrawingCache();

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        icon.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg");
        try {
            file.createNewFile();
            FileOutputStream fo = new FileOutputStream(file);
            fo.write(bytes.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("*/*");
        Uri uri = Uri.parse("file:///sdcard/temporary_file.jpg");
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.putExtra(Intent.EXTRA_TEXT, status);
        context.startActivity(Intent.createChooser(shareIntent, "Share Image"));

    }

    public void viewMainPostLikes(final Random_Profile_Adapter.MyViewHolder holder, final String user_id) {

        Session session = new Session(context);
        final String api_token = session.getApiToken();
        String user_like_url = "api/v1/likeview/" + user_id + "?api_token=" + api_token;
        String url = "http://selfie.todaylike.club/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())

                .build();

        ProfileJsonParsing service = retrofit.create(ProfileJsonParsing.class);

        Call<Pojo> call = service.getUserDetail(user_like_url);

        call.enqueue(new retrofit2.Callback<Pojo>() {
            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                try {

                    Pojo object = response.body();
                    Dataarray data = object.getDataarray();
                    ArrayList<Data> post_detail = (ArrayList<Data>) data.getPost_detail();
                    if (object.getStatus().toString().equals("success")) {
                        holder.likes_tv.setText(String.valueOf(post_detail.get(0).getTotal_like()));


                        //Toast.makeText(context,post_detail.get(0).getTotal_Like,Toast.LENGTH_SHORT).show();
                        // Toast.makeText(context,user_id,Toast.LENGTH_SHORT).show();

                    }


                } catch (Exception e) {
                    Log.d("onResponse", e.toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void checkMainPostLikes(final Random_Profile_Adapter.MyViewHolder holder, String user_id) {

        Session session = new Session(context);
        final String login_id = session.getUserId();
        final String id = user_id;
        final String api_token = session.getApiToken();
        String check_follow_url = "api/v1/checklike/" + login_id + "/" + id + "?api_token=" + api_token;
        String url = "http://selfie.todaylike.club/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())

                .build();

        ProfileJsonParsing service = retrofit.create(ProfileJsonParsing.class);

        Call<Pojo> call = service.getUserDetail(check_follow_url);

        call.enqueue(new retrofit2.Callback<Pojo>() {
            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                try {
                    // RelativeLayout like_btn=view.findViewById(R.id.like_btn);
                    Pojo object = response.body();
                    // Toast.makeText(context,object.getData().toString(),Toast.LENGTH_SHORT).show();
                    if (object.getData().toString().equals("Yes")) {

                        // int count= Integer.valueOf(like_tv.getText().toString());
                        //  like_tv.setText(String.valueOf(count++));

                        holder.like_img.setImageResource(R.drawable.like_icon_change);
                        // like_tv.setText(total_likes);


                    } else {
                        holder.likes_tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String user_like_url = "api/v1/like/" + login_id + "/" + id + "?api_token=" + api_token;
                                //  Toast.makeText(context,"button clicked",Toast.LENGTH_SHORT).show();
                                getMainPostLikes(user_like_url, holder);
                            }
                        });
                        holder.like_img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String user_like_url = "api/v1/like/" + login_id + "/" + id + "?api_token=" + api_token;
                                //  Toast.makeText(context,"button clicked",Toast.LENGTH_SHORT).show();
                                getMainPostLikes(user_like_url, holder);
                            }
                        });
                    }


                } catch (Exception e) {
                    Log.d("onResponse", e.toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void getMainPostLikes(String follow_url, final Random_Profile_Adapter.MyViewHolder holder) {

        String url = "http://selfie.todaylike.club/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())

                .build();

        ProfileJsonParsing service = retrofit.create(ProfileJsonParsing.class);

        Call<Pojo> call = service.getUserDetail(follow_url);

        call.enqueue(new retrofit2.Callback<Pojo>() {
            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                try {

                    Pojo object = response.body();
                    if (object.getResponse().toString().equals("true")) {

                        String count = holder.likes_tv.getText().toString();
                        int add = Integer.valueOf(count);
                        add++;
                        holder.likes_tv.setText(String.valueOf(add));
                        holder.like_img.setImageResource(R.drawable.like_icon_change);


                    }


                } catch (Exception e) {
                    Log.d("onResponse", e.toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}






