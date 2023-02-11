package com.apadanah.crypto_news.business.domain.model.main

data class TopicFilter(
    val value: String,
    val name: String
)

val TOPIC_FILTER_LIST = listOf<TopicFilter>(
    TopicFilter("Diem","Diem"),
    TopicFilter("Digital+Dollar","Digital Dollar"),
    TopicFilter("Digital+Euro","Digital Euro"),
    TopicFilter("Digital+Ruble","Digital Ruble"),
    TopicFilter("Digital+Yuan","Digital Yuan"),
    TopicFilter("Futures","Futures"),
    TopicFilter("Libra","Libra"),
    TopicFilter("Mining","Mining"),
    TopicFilter("NFT","NFT"),
    TopicFilter("podcast","podcast"),
    TopicFilter("Stablecoins","Stablecoins"),
    TopicFilter("Tanalysis","Tanalysis"),
    TopicFilter("Taxes","Taxes"),
    TopicFilter("Upgrade","Upgrade"),
    TopicFilter("Whales","Whales"),
)