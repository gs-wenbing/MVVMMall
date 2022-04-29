package com.zwb.module_video

import com.google.gson.JsonElement
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface VideoApi {

    @GET(DISCOVERY_URL)
    suspend fun getDiscovery(): JsonElement

    @GET(RECOMMEND_URL)
    suspend fun getRecommend(): JsonElement

    @GET
    suspend fun getRecommendNextPage(@Url url: String): JsonElement

    @GET(COMMUNITY_URL)
    suspend fun getCommunity(): JsonElement

    @GET
    suspend fun getCommunityNextPage(@Url url: String): JsonElement

    @GET(VIDEO_RELATED_URL)
    suspend fun getVideoRelated(@Query("id") id: Long): JsonElement

    @GET(VIDEO_REPLIES_URL)
    suspend fun getVideoReplies(@Query("videoId") videoId: Long): JsonElement


    companion object {
        const val BASE_URL = "http://baobab.kaiyanapp.com/api/"

        //发现
        const val DISCOVERY_URL =
            "http://baobab.kaiyanapp.com/api/v7/index/tab/discovery?udid=fa53872206ed42e3857755c2756ab683fc22d64a&vc=591&vn=6.2.1&size=720X1280&deviceModel=Che1-CL20&first_channel=eyepetizer_zhihuiyun_market&last_channel=eyepetizer_zhihuiyun_market&system_version_code=19"

        //推荐
        const val RECOMMEND_URL = "http://baobab.kaiyanapp.com/api/v5/index/tab/allRec"

        //社区
        const val COMMUNITY_URL = "http://baobab.kaiyanapp.com/api/v7/community/tab/rec"

        //视频详情 相关推荐
        const val VIDEO_RELATED_URL = "http://baobab.kaiyanapp.com/api/v4/video/related"

        // 视频详情 热门评论
        const val VIDEO_REPLIES_URL = "http://baobab.kaiyanapp.com/api/v2/replies/video"
    }
}