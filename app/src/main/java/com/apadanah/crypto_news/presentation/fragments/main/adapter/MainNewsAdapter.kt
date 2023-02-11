package com.apadanah.crypto_news.presentation.fragments.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.apadanah.crypto_news.R
import com.apadanah.crypto_news.business.domain.constans.Constants
import com.apadanah.crypto_news.business.domain.model.main.News
import com.apadanah.crypto_news.databinding.CustomNewsItemMainBinding
import com.apadanah.crypto_news.presentation.util.image_view.GlideRequestOption
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions



class MainNewsAdapter(
    private val context: Context,
    private val ClickListener: (News) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG: String = "AppDebug NewsAdapter2"

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<News>() {

        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.news_url == newItem.news_url
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem == newItem
        }

    }


    private val differ =
        AsyncListDiffer(
            NewsRecyclerChangeCallback(this),
            AsyncDifferConfig.Builder(DIFF_CALLBACK).build()
        )


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NewsViewHolder(
            CustomNewsItemMainBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            ClickListener = ClickListener,
        )
    }

    internal inner class NewsRecyclerChangeCallback(
        private val adapter: MainNewsAdapter
    ) : ListUpdateCallback {

        override fun onChanged(position: Int, count: Int, payload: Any?) {
            adapter.notifyItemRangeChanged(position, count, payload)
        }

        override fun onInserted(position: Int, count: Int) {
            adapter.notifyItemRangeChanged(position, count)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            adapter.notifyDataSetChanged()
        }

        override fun onRemoved(position: Int, count: Int) {
            adapter.notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NewsViewHolder -> {
                holder.bind(differ.currentList[position], context)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(NewsList: List<News>?) {
        val newList = NewsList?.toMutableList()
        differ.submitList(newList)

    }

    class NewsViewHolder
    constructor(
        private val binding: CustomNewsItemMainBinding,
        private val ClickListener: (News) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: News, context: Context) {
            binding.root.setOnClickListener {
                ClickListener(item)
            }

            binding.txtTitle.text = item.title
            binding.txtDesc.text = item.text
            binding.txtDate.text = item.date


            Glide.with(context)
                .setDefaultRequestOptions(GlideRequestOption.imageRequestOptions)
                .load(item.image_url)
                //.diskCacheStrategy(DiskCacheStrategy.ALL)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.imageView)


            when (item.sentiment) {
                Constants.SENTIMENT_NEGATIVE -> {
                    binding.imageViewDot.setImageResource(R.drawable.shape_circle_negative)
                }
                Constants.SENTIMENT_NEUTRAL -> {
                    binding.imageViewDot.setImageResource(R.drawable.shape_circle_natural)
                }
                Constants.SENTIMENT_POSITIVE -> {
                    binding.imageViewDot.setImageResource(R.drawable.shape_circle_positive)
                }
            }


        }

    }


}
