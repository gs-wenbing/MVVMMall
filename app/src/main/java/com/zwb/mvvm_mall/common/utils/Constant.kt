package com.zwb.mvvm_mall.common.utils

/**
 * Created with Android Studio.
 * Description:
 * @date: 2020/02/24
 * Time: 17:36
 */
object Constant {
    const val BASE_URL = "https://mockapi.eolink.com/"
    const val PROJECT = "/JiPqtefd9325e3466dc720a8c0ad3364c4f791e227debcd"
//    const val BASE_URL = "https://wanandroid.com/"



    const val HOME = 0
    const val CLASSIFY = 1
    const val CART = 2
    const val MINE = 3


    const val SEARCH_RECORD = 0
    const val SEARCH_GOODS = 1
    const val SEARCH_SIMILAR = 2

    const val SUCCESS = 0

    const val COMMON_KEY = "url_common_key"
    //接口
    const val URL_BANNER = "$PROJECT/banner/json"
    const val URL_GOODS_CLASS = "$PROJECT/home/getGoodsClass"
    const val URL_SECKILL_GOODS = "$PROJECT/home/getSeckillGoodsList"
    const val URL_BOUTIQUE_GOODS = "$PROJECT/home/getBoutiqueGoodsList"
    const val URL_CARTS = "$PROJECT/home/getCartList"
    const val URL_SEARCH_TAGS = "$PROJECT/home/getSearchTags"
    const val URL_SEARCH_HOT = "$PROJECT/home/getSearchHotTags"
    const val URL_COMMENTS = "$PROJECT/comment/getCommentList"
    const val URL_FILTER_ATTRS = "$PROJECT/goods/getFilterAttrs"


}
