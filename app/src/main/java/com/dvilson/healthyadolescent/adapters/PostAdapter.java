package com.dvilson.healthyadolescent.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dvilson.healthyadolescent.R;
import com.dvilson.healthyadolescent.models.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {
    ArrayList<Post> mPosts= new ArrayList<>();
    Context mContext;
    public PostAdapter(Context context,ArrayList<Post> posts) {
        mContext = context;
        mPosts = posts;

    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_item,parent,false);
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        Post post = mPosts.get(position);
        holder.bind(post);

    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public class PostHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvPostTitle, tvDate, tvLikes, tvComments;
        ImageButton likeBtn,commentBtn;
        CircleImageView imgProfile;
        ImageView imgPost;
        public PostHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.home_tv_name);
            tvPostTitle = itemView.findViewById(R.id.home_tv_post_title);
            tvDate = itemView.findViewById(R.id.home_tv_post_date);
            tvLikes = itemView.findViewById(R.id.home_numbers_like);
            tvComments = itemView.findViewById(R.id.home_number_comments);
            likeBtn = itemView.findViewById(R.id.home_like__button);
            commentBtn = itemView.findViewById(R.id.home_comment_button);
            imgProfile = itemView.findViewById(R.id.home_img_profile);
            imgPost = itemView.findViewById(R.id.home_image_post);
        }
        private void bind(Post post){
            Glide.with(mContext)
                    .load(post.getPhoto())
                    .centerCrop()
                    .into(imgProfile);
            Glide.with(mContext)
                    .load(post.getPhoto())
                    .centerCrop()
                    .into(imgPost);
            tvName.setText(post.getUser().getPseudo());
            tvDate.setText(post.getDate());
            tvPostTitle.setText(post.getDescription());
            tvComments.setText("See All"+ post.getComments()+"comments");
            tvLikes.setText(post.getLikes()+ " likes");

        }
    }
}
