package com.zwb.mvvm_mall.bean

data class GoodsBannerEntity(var imageUrl:String,
                             var viewType:Int){

    companion object{
        fun getTestData(): List<GoodsBannerEntity> {
            val list = ArrayList<GoodsBannerEntity>()
            list.add(GoodsBannerEntity("https://vod.300hu.com/4c1f7a6atransbjngwcloud1oss/444b0379221736599300878337/v.f30.mp4?dockingId=8f3e428b-c281-4f33-9395-85c40b1fd12b1",2))
            list.add(GoodsBannerEntity("http://img13.360buyimg.com/n1/s800x1026_jfs/t1/45710/39/4373/326364/5d219e37Eb4d39bac/32f0f9a45c3fa5b1.jpg!cc_800x1026.jpg", 1))
            list.add(GoodsBannerEntity("http://img13.360buyimg.com/n1/s800x1026_jfs/t1/99640/13/1695/326377/5dc44663Ebdb194d9/f0998050e03ed577.jpg!cc_800x1026.jpg", 1))
            list.add(GoodsBannerEntity("http://img10.360buyimg.com/n1/s800x1026_jfs/t1/136133/35/5473/451693/5f1c27d1E23499ddd/51c81606fb5e1af1.jpg!cc_800x1026.jpg", 1))
            list.add(GoodsBannerEntity("http://img10.360buyimg.com/n1/s800x1026_jfs/t21781/61/1794677431/712711/16eddd01/5b379400Nf0c29d8a.jpg!cc_800x1026.jpg", 1))
            list.add(GoodsBannerEntity( "http://img10.360buyimg.com/n1/s350x449_jfs/t21838/341/1810460908/653035/d422f418/5b379401N2f5477a2.jpg!cc_800x1026.jpg", 1))
            return list
        }
    }
}
