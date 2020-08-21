package com.zwb.mvvm_mall.common.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import com.shuyu.gsyvideoplayer.utils.CommonUtil;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.video.NormalGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer;
import com.shuyu.gsyvideoplayer.view.SmallVideoTouch;

import java.lang.reflect.Constructor;

import static com.shuyu.gsyvideoplayer.utils.CommonUtil.getActionBarHeight;
import static com.shuyu.gsyvideoplayer.utils.CommonUtil.getStatusBarHeight;

public class GoodsDetailVideoPlay extends NormalGSYVideoPlayer {
    public GoodsDetailVideoPlay(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public GoodsDetailVideoPlay(Context context) {
        super(context);
    }

    public GoodsDetailVideoPlay(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    /**
     * 移除没用的
     */
    private void removeVideo(ViewGroup vp, int id) {
        View old = vp.findViewById(id);
        if (old != null) {
            if (old.getParent() != null) {
                ViewGroup viewGroup = (ViewGroup) old.getParent();
                vp.removeView(viewGroup);
            }
        }
    }
    @Override
    public GSYBaseVideoPlayer showSmallVideo(Point size, boolean actionBar, boolean statusBar) {
//        return super.showSmallVideo(size, actionBar, statusBar);
        final ViewGroup vp = (CommonUtil.scanForActivity(getContext())).findViewById(Window.ID_ANDROID_CONTENT);

        removeVideo(vp, getSmallId());

        if (mTextureViewContainer.getChildCount() > 0) {
            mTextureViewContainer.removeAllViews();
        }

        try {
            Class basePlayerClass = GSYBaseVideoPlayer.class;
            Constructor<GSYBaseVideoPlayer> constructor = basePlayerClass.getConstructor(Context.class);
            GSYBaseVideoPlayer baseVideoPlayer = constructor.newInstance(getActivityContext());
            baseVideoPlayer.setId(getSmallId());

            LayoutParams lpParent = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            FrameLayout frameLayout = new FrameLayout(mContext);

            LayoutParams lp = new LayoutParams(size.x, size.y);
            int marginLeft = CommonUtil.getScreenWidth(mContext) - size.x;
            int marginTop = CommonUtil.getScreenHeight(mContext) - size.y;

            if (actionBar) {
                marginTop = marginTop - getActionBarHeight((Activity) mContext);
            }

            if (statusBar) {
                marginTop = marginTop - getStatusBarHeight(mContext);
            }

            lp.setMargins(marginLeft, marginTop, 0, 0);
            frameLayout.addView(baseVideoPlayer, lp);

            vp.addView(frameLayout, lpParent);

            cloneParams(this, baseVideoPlayer);

            baseVideoPlayer.setIsTouchWiget(false);//小窗口不能点击

            basePlayerClass.getDeclaredMethod("addTextureView").invoke(baseVideoPlayer);

//            baseVideoPlayer.addTextureView();
            //隐藏掉所有的弹出状态哟
            basePlayerClass.getDeclaredMethod("onClickUiToggle").invoke(baseVideoPlayer);
//            baseVideoPlayer.onClickUiToggle();
            baseVideoPlayer.setVideoAllCallBack(mVideoAllCallBack);
            basePlayerClass.getDeclaredMethod("setSmallVideoTextureView",SmallVideoTouch.class).invoke(baseVideoPlayer,new SmallVideoTouch(baseVideoPlayer, marginLeft, marginTop));
//            baseVideoPlayer.setSmallVideoTextureView(new SmallVideoTouch(baseVideoPlayer, marginLeft, marginTop));

            getGSYVideoManager().setLastListener(this);
            getGSYVideoManager().setListener(baseVideoPlayer);
            if (mVideoAllCallBack != null) {
                Debuger.printfError("onEnterSmallWidget");
                mVideoAllCallBack.onEnterSmallWidget(mOriginUrl, mTitle, baseVideoPlayer);
            }

            return baseVideoPlayer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
