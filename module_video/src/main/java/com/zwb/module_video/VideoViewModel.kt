package com.zwb.module_video

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.lib_base.ktx.initiateRequest
import com.zwb.lib_base.mvvm.vm.BaseViewModel
import com.zwb.lib_base.net.State
import com.zwb.lib_base.net.StateType
import com.zwb.lib_base.utils.GsonUtils
import com.zwb.module_video.bean.*
import org.json.JSONObject

class VideoViewModel : BaseViewModel() {

    private val repository by lazy {
        VideoRepo()
    }

    private var recommendNextPageUrl = ""
    private var communityNextPageUrl = ""

    fun getDiscover(): MutableLiveData<List<MultiItemEntity?>> {
        val discoverLiveData: MutableLiveData<List<MultiItemEntity?>> = MutableLiveData()
        initiateRequest({
            val discover = repository.getDiscovery()
            discoverLiveData.value = parseDiscover(discover.toString())
            loadState.value = State(StateType.SUCCESS, VideoApi.DISCOVERY_URL)
        }, loadState, VideoApi.DISCOVERY_URL)
        return discoverLiveData
    }

    fun getRecommend(): MutableLiveData<List<MultiItemEntity?>> {
        val recommendLiveData: MutableLiveData<List<MultiItemEntity?>> = MutableLiveData()
        initiateRequest({
            val discover = repository.getRecommend()
            recommendLiveData.value = parseRecommend(discover.toString())
            loadState.value = State(StateType.SUCCESS, VideoApi.RECOMMEND_URL)
        }, loadState, VideoApi.RECOMMEND_URL)
        return recommendLiveData
    }

    fun getRecommendNextPage(): MutableLiveData<List<MultiItemEntity?>> {
        val recommendLiveData: MutableLiveData<List<MultiItemEntity?>> = MutableLiveData()
        initiateRequest({
            if (!TextUtils.isEmpty(recommendNextPageUrl)) {
                val discover = repository.getRecommendNextPage(recommendNextPageUrl)
                recommendLiveData.value = parseRecommend(discover.toString())
            }else{
                recommendLiveData.value = mutableListOf()
            }
        }, loadState, VideoApi.RECOMMEND_URL)
        return recommendLiveData
    }

    fun getCommunity(): MutableLiveData<List<MultiItemEntity?>> {
        val recommendLiveData: MutableLiveData<List<MultiItemEntity?>> = MutableLiveData()
        initiateRequest({
            val discover = repository.getCommunity()
            recommendLiveData.value = parseCommunity(discover.toString())
            loadState.value = State(StateType.SUCCESS, VideoApi.COMMUNITY_URL)
        }, loadState, VideoApi.COMMUNITY_URL)
        return recommendLiveData
    }

    fun getCommunityNextPage(): MutableLiveData<List<MultiItemEntity?>> {
        val recommendLiveData: MutableLiveData<List<MultiItemEntity?>> = MutableLiveData()
        initiateRequest({
            if (!TextUtils.isEmpty(communityNextPageUrl)) {
                val discover = repository.getCommunityNextPage(communityNextPageUrl)
                recommendLiveData.value = parseCommunity(discover.toString())
            }else{
                recommendLiveData.value = mutableListOf()
            }
        }, loadState, VideoApi.RECOMMEND_URL)
        return recommendLiveData
    }

    fun getVideoRelatedAndReplies(videoId: Long): MutableLiveData<List<MultiItemEntity?>> {
        val liveData: MutableLiveData<List<MultiItemEntity?>> = MutableLiveData()
        initiateRequest({
            val list = mutableListOf<MultiItemEntity?>()
            val json = repository.getVideoRelated(videoId)
            list.addAll(parseVideoRelated(json.toString()))
            val json2 = repository.getVideoReplies(videoId)
            list.addAll(parseVideoReplies(json2.toString()))
            liveData.value = list
            loadState.value = State(StateType.SUCCESS, VideoApi.VIDEO_RELATED_URL)
        }, loadState, VideoApi.VIDEO_RELATED_URL)
        return liveData
    }


    private fun parseDiscover(discover: String): MutableList<MultiItemEntity?> {
        val discoverList = mutableListOf<MultiItemEntity?>()
        val jsonObject = JSONObject(discover)
        val itemList = jsonObject.optJSONArray("itemList")
        if (itemList != null) {
            for (i in 0 until itemList.length()) {
                val objItem = itemList.getJSONObject(i)
                when (objItem.optString("type")) {
                    // banner
                    "horizontalScrollCard" -> {
                        val bannerArray = objItem.getJSONObject("data").getJSONArray("itemList")
                        val list = mutableListOf<BannerEntity?>()
                        for (k in 0 until bannerArray.length()) {
                            val bannerData = bannerArray.getJSONObject(k).getJSONObject("data")
                            list.add(
                                GsonUtils().fromLocalJson(
                                    bannerData.toString(),
                                    BannerEntity::class.java
                                )
                            )
                        }
                        discoverList.add(BannerList(list))
                    }
                    "specialSquareCardCollection" -> { // 热门分类
//                    "columnCardList" -> { // 专题策划
                        val headerObject = objItem.getJSONObject("data").getJSONObject("header")
                        discoverList.add(
                            TitleEntity(
                                headerObject.optString("title"),
                                headerObject.optString("actionUrl"),
                                headerObject.optString("rightText")
                            )
                        )
                        val categoryArray = objItem.getJSONObject("data").getJSONArray("itemList")
                        val list = mutableListOf<CategoryEntity?>()
                        for (k in 0 until categoryArray.length()) {
                            val categoryItem = categoryArray.getJSONObject(k).getJSONObject("data")
                            list.add(
                                GsonUtils().fromLocalJson(
                                    categoryItem.toString(),
                                    CategoryEntity::class.java
                                )
                            )
                        }
                        discoverList.add(CategoryList(list))
                    }
                    // 标题
                    "textCard" -> {
                        val titleItem = objItem.getJSONObject("data")
                        discoverList.add(
                            TitleEntity(
                                titleItem.optString("text"),
                                titleItem.optString("actionUrl"),
                                titleItem.optString("rightText")
                            )
                        )
                    }
                    // 本周榜单视频
                    "videoSmallCard" -> {
                        val videoItem = objItem.getJSONObject("data")
                        discoverList.add(parseVideo(videoItem))
                    }
                    // 推荐主题
                    "briefCard" -> {
                        val briefItem = objItem.getJSONObject("data")
                        discoverList.add(
                            GsonUtils().fromLocalJson(
                                briefItem.toString(),
                                ThemeEntity::class.java
                            )
                        )
                    }
                }

            }
        }
        return discoverList
    }

    private fun parseRecommend(discover: String): MutableList<MultiItemEntity?> {
        val recommendList = mutableListOf<MultiItemEntity?>()
        val jsonObject = JSONObject(discover)
        recommendNextPageUrl = jsonObject.optString("nextPageUrl")
        val itemList = jsonObject.optJSONArray("itemList")
        if (itemList != null) {
            for (i in 0 until itemList.length()) {
                val objItem = itemList.getJSONObject(i)
                when (objItem.optString("type")) {
                    // 开眼编辑精选
                    "squareCardCollection" -> {
                        // 标题
                        val headerObject = objItem.getJSONObject("data").getJSONObject("header")
                        recommendList.add(
                            TitleEntity(
                                headerObject.optString("title"),
                                headerObject.optString("actionUrl"),
                                headerObject.optString("rightText")
                            )
                        )
                        // item
                        val followCardList = objItem.getJSONObject("data").getJSONArray("itemList")
                        for (k in 0 until followCardList.length()) {
                            val followCardData =
                                followCardList.getJSONObject(k).getJSONObject("data")
                                    .getJSONObject("content").getJSONObject("data")
                            recommendList.add(parseVideo(followCardData, false))
                        }
                    }
                    "followCard" -> {
                        val videoItem = objItem.getJSONObject("data").getJSONObject("content")
                            .getJSONObject("data")
                        recommendList.add(parseVideo(videoItem, false))
                    }
                    // 标题
                    "textCard" -> {
                        val titleItem = objItem.getJSONObject("data")
                        recommendList.add(
                            TitleEntity(
                                titleItem.optString("text"),
                                titleItem.optString("actionUrl"),
                                titleItem.optString("rightText")
                            )
                        )
                    }
                    // 本周榜单视频
                    "videoSmallCard" -> {
                        recommendList.add(parseVideo(objItem.getJSONObject("data")))
                    }
                }
            }
        }
        return recommendList
    }

    private fun parseCommunity(discover: String): MutableList<MultiItemEntity?> {
        val discoverList = mutableListOf<MultiItemEntity?>()
        val jsonObject = JSONObject(discover)
        communityNextPageUrl = jsonObject.optString("nextPageUrl")
        val itemList = jsonObject.optJSONArray("itemList")
        if (itemList != null) {
            for (i in 0 until itemList.length()) {
                val objItem = itemList.getJSONObject(i)
                when (objItem.optString("type")) {
                    // banner
                    "horizontalScrollCard" -> {
                        when (objItem.getJSONObject("data").optString("dataType")){
                            "ItemCollection" -> {
                                val itemCollectionList = objItem.getJSONObject("data").getJSONArray("itemList")
                                val list = mutableListOf<CommunitySquareEntity?>()
                                for (k in 0 until itemCollectionList.length()) {
                                    val communitySquare = itemCollectionList.getJSONObject(k).getJSONObject("data")
                                    list.add(
                                        GsonUtils().fromLocalJson(
                                            communitySquare.toString(),
                                            CommunitySquareEntity::class.java
                                        )
                                    )
                                }
                                discoverList.add(CommunitySquareList(list))
                            }
                            "HorizontalScrollCard" -> {
                                val bannerArray = objItem.getJSONObject("data").getJSONArray("itemList")
                                val list = mutableListOf<BannerEntity?>()
                                for (k in 0 until bannerArray.length()) {
                                    val bannerData = bannerArray.getJSONObject(k).getJSONObject("data")
                                    list.add(
                                        GsonUtils().fromLocalJson(
                                            bannerData.toString(),
                                            BannerEntity::class.java
                                        )
                                    )
                                }
                                discoverList.add(BannerList(list))
                            }
                        }
                    }
                    "communityColumnsCard" -> {
                        val communityColumns =
                            objItem.getJSONObject("data").getJSONObject("content")
                                .getJSONObject("data")

                        discoverList.add(
                            CommunityCardEntity(
                                coverUrl = communityColumns.getJSONObject("cover").optString("feed"),
                                description = communityColumns.optString("description"),
                                avatarUrl = communityColumns.getJSONObject("owner").optString("avatar"),
                                nickName = communityColumns.getJSONObject("owner").optString("nickname"),
                                collectionCount = communityColumns.getJSONObject("consumption").optInt("collectionCount"),
                                imgWidth = communityColumns.optInt("width"),
                                imgHeight = communityColumns.optInt("height")
                            )
                        )
                    }
                }

            }
        }
        return discoverList
    }

    private fun parseVideoRelated(json: String): MutableList<MultiItemEntity?>{
        val dataList = mutableListOf<MultiItemEntity?>()
        val jsonObject = JSONObject(json)
        val itemList = jsonObject.optJSONArray("itemList")
        if (itemList != null) {
            for (i in 0 until itemList.length()) {
                val objItem = itemList.getJSONObject(i)
                when (objItem.optString("type")) {
                    // 标题
                    "textCard" -> {
                        val titleItem = objItem.getJSONObject("data")
                        dataList.add(TitleEntity(
                            text = titleItem.optString("text"),
                            actionUrl = titleItem.optString("actionUrl"),
                        ))
                    }
                    // 视频
                    "videoSmallCard" -> {
                        dataList.add(parseVideo(objItem.getJSONObject("data")))
                    }
                }
            }
        }
        return dataList
    }

    private fun parseVideoReplies(json: String): MutableList<MultiItemEntity?>{
        val dataList = mutableListOf<MultiItemEntity?>()
        val jsonObject = JSONObject(json)
        val itemList = jsonObject.optJSONArray("itemList")
        if (itemList != null) {
            for (i in 0 until itemList.length()) {
                val objItem = itemList.getJSONObject(i)
                when (objItem.optString("type")) {
                    // 最新评论 标题
                    "leftAlignTextHeader" -> {
                        val headerObject = objItem.getJSONObject("data")
                        dataList.add(TitleEntity(text = headerObject.optString("text")))
                    }
                    "reply" -> {
                        val replyItem = objItem.getJSONObject("data")
                        val replyEntity = ReplyEntity()
                        if(replyItem.optJSONObject("user")!=null){
                            replyEntity.avatar =  replyItem.getJSONObject("user").optString("avatar")
                            replyEntity.nickName =  replyItem.getJSONObject("user").optString("nickname")
                        }
                        replyEntity.replyMessage = replyItem.optString("message")
                        replyEntity.releaseTime = replyItem.optLong("createTime")
                        replyEntity.likeCount = replyItem.optInt("likeCount")
                        dataList.add(replyEntity)
                    }
                }
            }
        }
        return dataList
    }

    private fun parseVideo(videoItem: JSONObject, isSmall: Boolean = true): VideoEntity {
        val video: VideoEntity = if (isSmall) VideoSmallEntity() else VideoEntity()
        if(videoItem.optJSONObject("cover")!=null){
            video.coverUrl = videoItem.getJSONObject("cover").optString("detail")
            video.blurredUrl = videoItem.getJSONObject("cover").optString("blurred")
        }
        if(videoItem.optJSONObject("author")!=null){
            video.description = "${
                videoItem.getJSONObject("author").optString("name")
            } / # ${videoItem.optString("category")}"
            video.authorIcon = videoItem.getJSONObject("author").optString("icon")
            video.authorDescription = videoItem.getJSONObject("author")
                .optString("description")
            video.nickName = videoItem.getJSONObject("author").optString("name")
        }
        video.videoTime = videoItem.optInt("duration")
        video.title = videoItem.optString("title")
        video.videoDescription = videoItem.optString("description")
        video.category = videoItem.optString("category")
        video.playerUrl = videoItem.optString("playUrl")
        video.videoId = videoItem.optLong("id")
        return video
    }
}