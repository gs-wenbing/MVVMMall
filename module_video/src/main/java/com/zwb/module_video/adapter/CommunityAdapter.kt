package com.zwb.module_video.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.youth.banner.Banner
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.util.BannerUtils
import com.zwb.lib_base.ktx.height
import com.zwb.lib_base.ktx.width
import com.zwb.lib_base.utils.UIUtils
import com.zwb.module_video.R
import com.zwb.module_video.bean.*

class CommunityAdapter(var fragment: Fragment, data: MutableList<MultiItemEntity>) :
    BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(data) {

    init {
        addItemType(AdapterItemType.BANNER_VIEW, R.layout.item_banner_layout)
        addItemType(AdapterItemType.COMMUNITY_SQUARE_VIEW, R.layout.item_community_square_layout)
        addItemType(AdapterItemType.COMMUNITY_CARD_VIEW, R.layout.item_community_card_layout)
    }

    override fun convert(helper: BaseViewHolder, item: MultiItemEntity?) {
        when(helper.itemViewType){
            AdapterItemType.BANNER_VIEW -> {
                item?.let {
                    val data = it as BannerList

                    val layoutParams = StaggeredGridLayoutManager.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        UIUtils.dp2px(170f)
                    )
                    layoutParams.isFullSpan = true
                    helper.itemView.layoutParams = layoutParams

                    val banner: Banner<BannerEntity, BannerVideoAdapter> =
                        helper.getView(R.id.banner)
                    if (banner.adapter == null) banner.adapter = BannerVideoAdapter(mutableListOf())
                    banner.addBannerLifecycleObserver(fragment)
                    banner.indicator = CircleIndicator(fragment.context)
                    banner.setBannerRound2(20f)
                    banner.adapter.setDatas(data.bannerList)
                }
            }
            AdapterItemType.COMMUNITY_SQUARE_VIEW -> {
                item?.let {
                    val data = it as CommunitySquareList
                    val rvSquare = helper.getView<RecyclerView>(R.id.rv_square_view)
                    if (rvSquare.layoutManager == null) {
                        val layoutParams = StaggeredGridLayoutManager.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            UIUtils.dp2px(80f)
                        )
                        layoutParams.isFullSpan = true
                        helper.itemView.layoutParams = layoutParams

                        val layoutManager = GridLayoutManager(mContext, 1)
                        val squareAdapter = SquareAdapter(mutableListOf())
                        layoutManager.orientation = RecyclerView.HORIZONTAL
                        rvSquare.layoutManager = layoutManager
                        rvSquare.adapter = squareAdapter
                        squareAdapter.setNewData(data.list)
                    }
                }
            }
            AdapterItemType.COMMUNITY_CARD_VIEW -> {
                item?.let {
                    val data = it as CommunityCardEntity
                    helper.setText(R.id.tv_description, data.description)
                    helper.setText(R.id.tv_nickname, data.nickName)
                    helper.setText(R.id.tv_collection_num, data.collectionCount.toString())

                    val ivCover = helper.getView<ImageView>(R.id.iv_cover_bg)
                    BannerUtils.setBannerRound(ivCover, 20f)

                    val itemWidth = UIUtils.getScreenWidth() / 2
                    val scale: Float = (itemWidth + 0f) / data.imgWidth
                    ivCover.height((data.imgHeight * scale).toInt())

                    Glide.with(ivCover.context)
                        .load(data.coverUrl)
                        .into(ivCover)

                    val ivAvatar = helper.getView<ImageView>(R.id.iv_avatar)
                    BannerUtils.setBannerRound(ivAvatar, UIUtils.dp2px(30f).toFloat())
                    Glide.with(ivAvatar.context)
                        .load(data.avatarUrl)
                        .into(ivAvatar)
                }
            }
        }
    }
}