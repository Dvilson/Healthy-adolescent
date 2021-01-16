package com.dvilson.healthyadolescent.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dvilson.healthyadolescent.MainActivity;
import com.dvilson.healthyadolescent.R;
import com.dvilson.healthyadolescent.adapters.PostAdapter;
import com.dvilson.healthyadolescent.models.Post;
import com.dvilson.healthyadolescent.models.User;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    RecyclerView mRecyclerView;
    PostAdapter mPostAdapter;
    Context mContext;
    ArrayList<Post> mPosts;

    SwipeRefreshLayout mRefreshLayout;




    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result =  inflater.inflate(R.layout.fragment_home, container, false);
        return  result;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getContext();
        mRecyclerView = view.findViewById(R.id.recycleview_home);
       //mToolbar = view.findViewById(R.id.home_toolbar);
        mRefreshLayout = view.findViewById(R.id.swipe_home);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setData();
            }
        });

        //((MainActivity) getContext()).setSupportActionBar(mToolbar);
        setData();
        initRecyclerView();


    }
    public void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mPostAdapter = new PostAdapter(mContext,mPosts);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mPostAdapter);

    }

    public  void setData(){
        mPosts = new ArrayList<>();
        User user  = new User("Kissi","Dvilson");
        mPosts.add(new Post(122,13,"il ya 12 min","Les infections sexuelles","https://firebasestorage.googleapis.com/v0/b/healthy-adolescent.appspot.com/o/post_images%2F108142.jpg?alt=media&token=87c14335-bc33-4b17-abf7-6b217f4851d3",user,true));
        mRefreshLayout.setRefreshing(false);
    }
}