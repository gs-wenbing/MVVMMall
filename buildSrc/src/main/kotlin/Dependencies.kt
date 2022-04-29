/**
 * 项目依赖版本统一管理
 *
 * @author zwb
 * @since 2/27/22
 */


/**
 * AndroidX相关依赖
 *
 * @author zwb
 * @since 2/27/22
 */
object AndroidX {
    const val AndroidJUnitRunner = "androidx.test.runner.AndroidJUnitRunner"
    const val AppCompat = "androidx.appcompat:appcompat:1.2.0"
    const val CoreKtx = "androidx.core:core-ktx:1.3.1"
    const val ConstraintLayout = "androidx.constraintlayout:constraintlayout:2.0.1"
    const val ActivityKtx = "androidx.activity:activity-ktx:1.1.0"
    const val FragmentKtx = "androidx.fragment:fragment-ktx:1.2.5"
    const val Room = "androidx.room:room-runtime:2.3.0"
    const val RoomKtx = "androidx.room:room-ktx:2.3.0"
    const val RoomCompiler = "androidx.room:room-compiler:2.3.0"
    const val MultiDex = "androidx.multidex:multidex:2.0.1"
    const val Viewpager2 = "androidx.viewpager2:viewpager2:1.0.0"
    const val TestExtJunit = "androidx.test.ext:junit:1.1.2"
    const val TestEspresso = "androidx.test.espresso:espresso-core:3.3.0"


}

/**
 * Android相关依赖
 *
 * @author zwb
 * @since 2/27/22
 */
object Android {
    const val Junit = "junit:junit:4.13"
    const val Material = "com.google.android.material:material:1.2.0"
}

/**
 * JetPack相关依赖
 *
 * @author zwb
 * @since 2/27/22
 */
object JetPack {
    const val ViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1"
    const val LiveData = "androidx.lifecycle:lifecycle-livedata-ktx:2.3.1"
    const val Lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:2.3.1"
    const val ViewModelSavedState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:2.3.1"
    const val LifecycleCompilerAPT = "androidx.lifecycle:lifecycle-compiler:2.3.1"
    const val NavigationFragment = "androidx.navigation:navigation-fragment-ktx:2.3.0"
    const val NavigationUI = "androidx.navigation:navigation-ui-ktx:2.3.0"
}

/**
 * Kotlin相关依赖
 *
 * @author zwb
 * @since 2/27/22
 */
object Kotlin {
    const val Kotlin = "org.jetbrains.kotlin:kotlin-stdlib:1.4.21"
    const val CoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.6"
    const val CoroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.6"
    const val KotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:1.3.50"
}

/**
 * GitHub及其他相关依赖
 *
 * @author zwb
 * @since 2/27/22
 */
object GitHub {
    const val OkHttp = "com.squareup.okhttp3:okhttp:4.9.0"
    const val OkHttpInterceptorLogging = "com.squareup.okhttp3:logging-interceptor:4.9.1"
    const val Retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
    const val RetrofitConverterGson = "com.squareup.retrofit2:converter-gson:2.9.0"

    // 腾讯 MMKV 替代SP
    const val MMKV = "com.tencent:mmkv-static:1.2.9"

    const val ARoute = "com.alibaba:arouter-api:1.5.1"
    const val ARouteCompiler = "com.alibaba:arouter-compiler:1.5.1"
    const val RecyclerViewAdapter = "com.github.CymChad:BaseRecyclerViewAdapterHelper:2.9.47"
    const val EventBus = "org.greenrobot:eventbus:3.2.0"
    const val EventBusAPT = "org.greenrobot:eventbus-annotation-processor:3.2.0"
    const val PermissionX = "com.permissionx.guolindev:permissionx:1.4.0"
    const val LeakCanary = "com.squareup.leakcanary:leakcanary-android:2.7"

    // 自动生成SPI暴露服务文件
    const val AutoService = "com.google.auto.service:auto-service:1.0"
    const val AutoServiceAnnotations = "com.google.auto.service:auto-service-annotations:1.0"


    // Koin AndroidX Scope feature
    const val KoinAndroidxScope = "org.koin:koin-androidx-scope:2.1.5"
    // Koin AndroidX ViewModel feature
    const val KoinAndroidxViewModel = "org.koin:koin-androidx-viewmodel:2.1.5"
    // Koin AndroidX Fragment Factory (unstable version)
    const val KoinAndroidxFragment = "org.koin:koin-androidx-fragment:2.1.5"

    const val Banner = "com.youth.banner:banner:2.1.0"
    const val SmartRefreshLayout = "com.scwang.smartrefresh:SmartRefreshLayout:1.1.0"
    const val MagicIndicator = "com.github.hackware1993:MagicIndicator:1.5.0"
    const val Glide = "com.github.bumptech.glide:glide:4.9.0"
    const val GlideCompiler = "com.github.bumptech.glide:compiler:4.9.0"
    const val GSYVideoPlayer = "com.shuyu:GSYVideoPlayer:7.1.5"
    const val RoundedImageView = "com.rishabhharit.roundedimageview:RoundedImageView:0.8.4"
    const val Loadsir = "com.kingja.loadsir:loadsir:1.3.8"
    const val SuperTextView = "com.github.lygttpod:SuperTextView:2.4.6"
}

/**
 * SDK相关依赖
 *
 * @author zwb
 * @since 2/27/22
 */
object SDK {
    // 腾讯Bugly 异常上报
    const val TencentBugly = "com.tencent.bugly:crashreport:3.3.9"

    // Bugly native异常上报
    const val TencentBuglyNative = "com.tencent.bugly:nativecrashreport:3.8.0"

    // 腾讯X5WebView
    const val TencentTBSX5 = "com.tencent.tbs.tbssdk:sdk:43939"
}
