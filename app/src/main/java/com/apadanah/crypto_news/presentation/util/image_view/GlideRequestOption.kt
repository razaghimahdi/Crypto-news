package com.apadanah.crypto_news.presentation.util.image_view

 import com.apadanah.crypto_news.R
 import com.bumptech.glide.request.RequestOptions

object GlideRequestOption {

      val imageRequestOptions = RequestOptions
        .placeholderOf(R.drawable.cryptoccurency_logo)
        .error(R.drawable.cryptoccurency_logo)

}