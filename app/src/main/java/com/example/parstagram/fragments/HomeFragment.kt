package com.example.parstagram.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.parstagram.Post
import com.example.parstagram.PostAdapter
import com.example.parstagram.R
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery

open class HomeFragment : Fragment() {

    lateinit var rvPosts: RecyclerView
    lateinit var adapter: PostAdapter
    lateinit var swipeContainer: SwipeRefreshLayout
    lateinit var hlike: ImageView
    lateinit var save: ImageView

    var allPosts: MutableList<Post> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeContainer = view.findViewById(R.id.swipeContainer)
        swipeContainer.setOnRefreshListener {
            Log.i(TAG, "Refreshing posts")
            queryPosts()
            var count = 0
            hlike = view.findViewById(R.id.like)
            hlike.setOnClickListener {
                if (count == 0) {
                    Glide.with(view.context).load(R.drawable.heart1).into(hlike)
                    count = 1
                } else {
                    Glide.with(view.context).load(R.drawable.ufi_heart).into(hlike)
                    count = 0
                }
            }
            var req = 0
            save = view.findViewById(R.id.saved)
            save.setOnClickListener {
                if (req == 0) {
                    Glide.with(view.context).load(R.drawable.saved).into(save)
                    req = 1
                } else {
                    Glide.with(view.context).load(R.drawable.unsaved).into(save)
                    req = 0
                }
            }
        }

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light);

        rvPosts = view.findViewById(R.id.rvPosts)

        adapter = PostAdapter(requireContext(), allPosts as ArrayList<Post>)
        rvPosts.adapter = adapter
        rvPosts.layoutManager = LinearLayoutManager(requireContext())
        queryPosts()
    }


    open fun queryPosts() {
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        query.include(Post.KEY_USER)
        query.addDescendingOrder("createdAt")
        query.setLimit(20)
        query.findInBackground(object: FindCallback<Post> {
            override fun done(posts: MutableList<Post>?, e: ParseException?) {
                if (e != null) {
                    Log.e(TAG, "Error fetching post")
                } else {
                    if (posts != null) {
                        adapter.clear()
                        for (post in posts) {
                            Log.i(TAG, "Post: " + post.getDescription() + ", Username: " + post.getUser()?.username + ", profileImage: " + post.getProfileImage()?.url +" Post: " + post.getImage()?.url)
                        }
                        allPosts.addAll(posts)
                        adapter.notifyDataSetChanged()
                        swipeContainer.setRefreshing(false)
                    }
                }
            }

        })
    }
    companion object {
        const val TAG = "HomeFeed"
    }

}