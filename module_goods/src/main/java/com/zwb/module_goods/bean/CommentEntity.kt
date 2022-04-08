package com.zwb.module_goods.bean

data class CommentEntity(var ID:Int,
                         var context:String,
                         var date:String,
                         var starNum:Int,
                         var replyNum:Int,
                         var userName:String,
                         var userIcon:String,
                         var isExpanded:Boolean,
                         var pics:List<CommentPics>)

data class CommentPics(var url:String)