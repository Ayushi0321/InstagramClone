package com.example.parstagram

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PostAdapter(val context: Context, val posts: ArrayList<Post>)
    : RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostAdapter.ViewHolder, position: Int) {
        val post = posts.get(position)
        holder.bind(post)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    // Clean all elements of the recycler
    fun clear() {
        posts.clear()
        notifyDataSetChanged()
    }

    // Add a list of items -- change to type used
    fun addAll(post: List<Post>) {
        posts.addAll(post)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvUserName: TextView
        val ivImage: ImageView
        val tvdescription: TextView
        val profileImage: ImageView

        init {
            tvUserName = itemView.findViewById(R.id.tvUserName)
            ivImage = itemView.findViewById(R.id.ivImage)
            tvdescription = itemView.findViewById(R.id.tvdescription)
            profileImage = itemView.findViewById(R.id.profileImage)
        }

        fun bind(post: Post) {
            tvdescription.text = post.getDescription()
            tvUserName.text = post.getUser()?.username
            Glide.with(itemView.context).load(post.getProfileImage()?.url).placeholder(R.drawable.not1).circleCrop().into(profileImage)
            Glide.with(itemView.context).load(post.getImage()?.url).into(ivImage)
        }
    }
}