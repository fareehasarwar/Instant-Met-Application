package com.example.hifza.instantmet;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.example.hifza.instantmet.adapter.Random_Profile_Adapter;
import com.example.hifza.instantmet.json.EditDpApi;
import com.example.hifza.instantmet.json.PostUploadImage;
import com.example.hifza.instantmet.json.ProfileJsonParsing;
import com.example.hifza.instantmet.login.Session;
import com.example.hifza.instantmet.model.Data;
import com.example.hifza.instantmet.model.Dataarray;
import com.example.hifza.instantmet.model.Followers;
import com.example.hifza.instantmet.model.Following;
import com.example.hifza.instantmet.model.Pojo;
import com.example.hifza.instantmet.model.RootObject;
import com.example.hifza.instantmet.model.UserPost;
import com.example.hifza.instantmet.model.User_Detail;
import com.example.hifza.instantmet.uploadselfie.CustomGallery_Activity;
import com.example.hifza.instantmet.uploadselfie.Upload_Iimage;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Fareeha on 1/4/2018.
 */

public class ProfileActivity extends AppCompatActivity {
    Session session_obj;
    String id, api_token,detail_url;
    TextView status_tv;
    ImageView post_img,upload_img;
    Random_Profile_Adapter adapter;
    RecyclerView mRecyclerprofile;
    ArrayList<Data> user_detail;
    ArrayList<Data> following_one;
    ArrayList<Data> followers;
    CircleImageView dp_img;
    TextView following,fan,number_of_posts;
    Bitmap bitmap;
    MultipartBody.Part fileToUpload;
    int h,w;

TextView name_tv;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
         session_obj=new Session(ProfileActivity.this);
         id=session_obj.getUserId();
         api_token=session_obj.getApiToken();
         detail_url="api/v1/userprofile/"+id+"?api_token="+api_token;
         getDetail();
        TextView staus=(TextView)findViewById(R.id.text_status);
        following=findViewById(R.id.num_of_followers);
        fan=findViewById(R.id.num_of_fans);
        dp_img=findViewById(R.id.profile_img_1);
        name_tv=findViewById(R.id.user_name);
        number_of_posts=findViewById(R.id.num_of_posts);
        status_tv=findViewById(R.id.text_status);
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(ProfileActivity.this));

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/myriadpro.otf");

        staus.setTypeface(custom_font);
        LinearLayout linearLayout_fans=(LinearLayout)findViewById(R.id.fans);
        LinearLayout linearLayout_following=(LinearLayout)findViewById(R.id.following);
         LinearLayout linearLayout_post=(LinearLayout)findViewById(R.id.post);

//

//        Toast.makeText(ProfileActivity.this,object.getStatus(),Toast.LENGTH_SHORT).show();

        linearLayout_fans.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                Intent leader= new Intent(ProfileActivity.this,FansActivity.class);
                leader.putExtra("hint","myProfile_fans");

               // Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1,R.anim.animation2).toBundle();
               startActivity(leader);
            }
        });
        linearLayout_following.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ProfileActivity.this,FansActivity.class);
               //intent.putExtra("hint","muProfile");
               intent.putExtra("hint","my_followers");

                //Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1,R.anim.animation2).toBundle();
             startActivity(intent);
            }
        });
linearLayout_post.setOnClickListener(new View.OnClickListener() {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        Intent intent= new Intent(ProfileActivity.this,LeaderActivity.class);
        startActivity(intent);

     /*  Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1,R.anim.animation2).toBundle();
        getApplicationContext().startActivity(intent,bndlanimation);*/
    }
});


         mRecyclerprofile = (RecyclerView) findViewById(R.id.recycler_profile);

        mRecyclerprofile.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));

      //  madapter = new profilectivityadapter(mResIds);
       // mRecyclerprofile.setAdapter(madapter);
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public  void UploadUserSelfie(View view)
    {
        Intent intent=new Intent(ProfileActivity.this, CustomGallery_Activity.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1,R.anim.animation2).toBundle();
       intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intent,bndlanimation);
    }
    public  void getDetail()
    {
        String url = "http://selfie.todaylike.club/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProfileJsonParsing service = retrofit.create(ProfileJsonParsing.class);

        Call<Pojo> call = service.getUserDetail(detail_url);

        call.enqueue(new Callback<Pojo>() {
            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                try {

               Pojo object=response.body();
               Dataarray data=object.getDataarray();
             ArrayList<Data> userPosts= (ArrayList<Data>) data.getUser_post();

              user_detail= (ArrayList<Data>) data.getUser_detail();

             following_one= (ArrayList<Data>) data.getFollowing();
             followers= (ArrayList<Data>) data.getFollowers();
                    Picasso.with(ProfileActivity.this).load(user_detail.get(0).getDp()).resize(80,90).into(dp_img);

              //  Toast.makeText(ProfileActivity.this,userPosts.toString(),Toast.LENGTH_SHORT).show();
             adapter=new Random_Profile_Adapter(ProfileActivity.this,R.layout.item_profile,userPosts,user_detail,2);
             mRecyclerprofile.setAdapter(adapter);

                    following.setText(following_one.get(0).getFollowing());
                    fan.setText(followers.get(0).getFollowers());
                    name_tv.setText(user_detail.get(0).getName());
                    number_of_posts.setText(String.valueOf(userPosts.size()));
                    if(user_detail.get(0).getU_status()!=null)
                    {
                        status_tv.setVisibility(View.VISIBLE);
                        status_tv.setText(user_detail.get(0).getU_status());
                    }
                    dp_img.setOnClickListener(new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void onClick(View v) {

                            dpEditDialog(user_detail.get(0).getDp());
                        }
                    });


                }catch (Exception e) {
                    Log.d("onResponse", e.toString());
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {
                Log.d("onFailure", t.toString());
                Toast.makeText(ProfileActivity.this,t.toString(),Toast.LENGTH_SHORT).show();
            }
        });

    }

public  void backButton(View view)
{
  Intent intent=new Intent(ProfileActivity.this,MainActivity.class);
  startActivity(intent);

}




    // ddp Image Edit Dialog Box
    private void dpEditDialog( String dp) {
//        MyLog.i(TAG, "***************number " + brandLocation);
        final Dialog pDialog = new Dialog(ProfileActivity.this, android.R.style.Theme_Translucent_NoTitleBar);

        final View layout = ((LayoutInflater)ProfileActivity.this. getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.dp_edit_popup, null);
        // pDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        pDialog.addContentView(layout,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                        ViewGroup.LayoutParams.FILL_PARENT));
        pDialog.getWindow().setGravity(Gravity.CENTER);
        pDialog.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        pDialog.setCancelable(false);
        pDialog.show();
        post_img=layout.findViewById(R.id.dp_img);
        upload_img=layout.findViewById(R.id.upload);
        final RelativeLayout edt_img=layout.findViewById(R.id.edit_dp);
        final RelativeLayout back_img=layout.findViewById(R.id.back_img);

        Glide.with(this).load(dp).into(post_img);
        edt_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImageFromGallery();
            }
        });
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog.dismiss();
            }
        });
        upload_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadEditImage();
            }
        });




        pDialog.setCanceledOnTouchOutside(true);
    }
    public void captureImageFromGallery()
    {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);
    }
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK && reqCode == 1) {
            try {
                final Uri imageUri = data.getData();

                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
               post_img.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(ProfileActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }

    }
    public void UploadEditImage()
    {

        Bitmap bitmap = ((BitmapDrawable)dp_img.getDrawable()).getBitmap();

        Uri uri_img=getImageUri(this,bitmap);
        String filePath = getRealPathFromURIPath(uri_img,ProfileActivity.this);
        File file = new File(filePath);

        //RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        fileToUpload = MultipartBody.Part.createFormData("postimage", file.getName(), mFile);






        RequestBody id_req = RequestBody.create(MediaType.parse("text/plain"), id);


        RequestBody token_req = RequestBody.create(MediaType.parse("text/plain"),api_token);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://selfie.todayworlds.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

       EditDpApi uploadImage = retrofit.create( EditDpApi.class);
        Call<RootObject> fileUpload = uploadImage.uploadEditedDetail( id_req,token_req,fileToUpload);
        fileUpload.enqueue(new Callback<RootObject>() {


            @Override
            public void onResponse(Call<RootObject> call, Response<RootObject> response) {

                RootObject studentdata=response.body();
                Data data=  studentdata.getData();
                if(studentdata.getStatus().toString().equals("success"))
                {
                    Toast.makeText(ProfileActivity.this, studentdata.getStatus() , Toast.LENGTH_LONG).show();
                    Session.getInstance(getApplicationContext()).userLoginDetail(data.getApi_token(),data.getId(), data.getName(),data.getEmail(),data.getPassword(),data.getAge(), data.getDp(),data.getGender(), data.getCountry());


                    finish();
                    Intent intent=new Intent(ProfileActivity.this,MainActivity.class);
                    startActivity(intent);
                }

                //  startActivity(new Intent(getApplicationContext(), MainActivity.class));

           /* Session.getInstance(getApplicationContext()).userLoginDetail(studentdata.getData().getApi_token(),studentdata.getData().getId(), studentdata.getData().getName(),studentdata.getData().getEmail(), studentdata.getData().getPassword(), studentdata.getData().getAge(), studentdata.getData().getDp(), studentdata.getData().getGender(), studentdata.getData().getCountry());


            Toast.makeText(Upload_Iimage.this, "Success " + response.body(), Toast.LENGTH_LONG).show();

            startActivity(new Intent(getApplicationContext(), MainActivity.class));
*/

            }
            @Override
            public void onFailure(Call<RootObject> call, Throwable t) {
                Toast.makeText(ProfileActivity.this,t.toString(),Toast.LENGTH_LONG).show();
                Log.d("log", "Error " + t.getMessage());

            }
        });
    }
    private String getRealPathFromURIPath(Uri contentURI, ProfileActivity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {

        w = inImage.getWidth();
        h = inImage.getHeight();


        if(w>=800 && h>=1200 ) {
            h = h / 2;
            w = w / 2;
        }


        bitmap= Bitmap.createScaledBitmap(inImage, w, h, true);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        //Bitmap bm= getResizedBitmap(inImage,250,300);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(),inImage, "Title", null);
        return Uri.parse(path);
    }
    public void gotoBack(View view)
    {

        finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }


}