
/**
 * 项目相关参数配置
 *
 * @author Qu Yunshuo
 * @since 4/24/21 5:56 PM
 */
object BuildConfig {
    const val compileSdkVersion = 29
    const val buildToolsVersion = "29.0.2"
    const val applicationId = "com.zwb.mvvm_mall"
    const val minSdkVersion = 21
    const val targetSdkVersion = 29
    const val versionCode = 1
    const val versionName = "1.0"

    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    const val isAppMode = false

    /**
     * 项目当前的版本状态
     * 该状态直接反映当前App是测试版 还是正式版 或者预览版
     * 正式版:RELEASE、预览版(α)-内部测试版:ALPHA、测试版(β)-公开测试版:BETA
     */
//    object Version {

        const val VERSION_RELEASE = "VERSION_STATUS_RELEASE"

        const val VERSION_ALPHA = "VERSION_STATUS_ALPHA"

        const val VERSION_BETA = "VERSION_STATUS_BETA"
//    }
}