package com.zwb.module_video

import com.google.gson.JsonElement
import com.zwb.lib_base.mvvm.m.BaseRepository
import com.zwb.lib_base.net.RetrofitFactory

class VideoRepo : BaseRepository() {
    private val apiService by lazy {
        RetrofitFactory.instance.getService(VideoApi::class.java, VideoApi.BASE_URL)
    }

    suspend fun getDiscovery(): JsonElement {
        return apiService.getDiscovery()
    }

    suspend fun getRecommend():JsonElement{
        return apiService.getRecommend()
    }

    suspend fun getRecommendNextPage(url: String):JsonElement{
        return apiService.getRecommendNextPage(url)
    }


    suspend fun getCommunity():JsonElement{
        return apiService.getCommunity()
    }

    suspend fun getCommunityNextPage(url: String):JsonElement{
        return apiService.getCommunityNextPage(url)
    }


    suspend fun getVideoRelated(videoId: Long):JsonElement{
        return apiService.getVideoRelated(videoId)
    }


    suspend fun getVideoReplies(videoId: Long):JsonElement{
        return apiService.getVideoReplies(videoId)
    }
}