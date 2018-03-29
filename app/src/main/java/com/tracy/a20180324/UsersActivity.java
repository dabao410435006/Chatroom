package com.tracy.a20180324;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class UsersActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private RecyclerView mUserList;
    private DatabaseReference mUsersDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        mToolbar = (Toolbar) findViewById(R.id.users_appBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("所有使用者");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("User");


        mUserList = (RecyclerView) findViewById(R.id.users_list);
        mUserList.setHasFixedSize(true);
        //layoutManager呈現View
        mUserList.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void onStart() {
        super.onStart();
        //使用FirebaseUI提供的FirebaseRecyclerAdapter類別快速生出adapter供RecyclerView顯示，不須實作太多方法即可同步Firebase資料。
       FirebaseRecyclerAdapter<Users,UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, UsersViewHolder>(
                Users.class,
                R.layout.users_single_layout,
                UsersViewHolder.class,
                mUsersDatabase
        ) {
           @Override
           protected void populateViewHolder(UsersViewHolder usersviewHolder, Users users, int position) {
               //將users.getName()物件傳送到ViewHolder內顯示
               //UsersViewHolder class的的setName
               usersviewHolder.setName(users.getName());
               usersviewHolder.setUserStatus(users.getStatus());
               usersviewHolder.setUserImage(users.getThumb_image(),getApplicationContext());

               //
               final String user_id = getRef(position).getKey();

               //View 點下要取得別人的profile
               usersviewHolder.mView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {

                       Intent profileIntent = new Intent(UsersActivity.this,ProfileActivity.class);
                       profileIntent.putExtra("user_id",user_id);
                       startActivity(profileIntent);

                   }
               });
           }
       };
       //將Adapter設定給RecyclerView
       mUserList.setAdapter(firebaseRecyclerAdapter);

    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public UsersViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setName(String name) {

            TextView userNameView = (TextView) mView.findViewById(R.id.user_single_name);
            userNameView.setText(name);
        }
        public void setUserStatus(String status) {

            TextView userStatusView = (TextView)mView.findViewById(R.id.user_single_status);
            userStatusView.setText(status);
        }

        public void setUserImage(String thumb_images,Context ctx) {

            CircleImageView userImageView = (CircleImageView) mView.findViewById(R.id.user_single_image);
            //使用Picasso將圖片顯示在ImageView
            Picasso.with(ctx).load(thumb_images).placeholder(R.drawable.user_person_before).into(userImageView);

        }
    }
}
