package cn.com.egova.progress;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

/**
 * Created by yuanchao on 2017/8/1.
 */

public class ProgressManager {
    public static final int STYLE_COMMON = 0;       //普通仿苹果的进度条
    public static final int STYLE_RATIO_LINE = 1;   //带比例的水平进度条
    public static final int STYLE_RATIO_ROUND = 2;  //带比例的圆形进度条
    private int proressStyle = 0;
    private Context mContext;
    private ViewGroup mProgressView;
    private ViewGroup mParentVG;  //根容器,FrameLayout.
    private ProgressBar progressBar; //进度条

    public ProgressManager(Activity activity) {
        this(activity, STYLE_COMMON);
    }

    public ProgressManager(Activity activity, int proressStyle) {
        this(activity, proressStyle,null);
    }

    public ProgressManager(Activity activity, int proressStyle, ViewGroup viewGroup) {
        this.mContext = activity;
        this.proressStyle = proressStyle;
        if (viewGroup == null) {
            if(proressStyle==STYLE_COMMON){
                viewGroup = (ViewGroup) LayoutInflater.from(activity).inflate(R.layout.progressbar_common, null, false);
            }else if(proressStyle==STYLE_RATIO_LINE){
                viewGroup = (ViewGroup) LayoutInflater.from(activity).inflate(R.layout.progressbar_ratio_line, null, false);
            }else{
                viewGroup = (ViewGroup) LayoutInflater.from(activity).inflate(R.layout.progressbar_ratio_round, null, false);
            }
        }
        this.progressBar = (ProgressBar) viewGroup.findViewById(R.id.proBar);
        this.mProgressView = viewGroup;
        mParentVG = findContentParent(activity);

    }

    public static ProgressManager makeProgress(Activity activity) {
        return makeProgress(activity, STYLE_COMMON);
    }

    public static ProgressManager makeProgress(Activity activity, int proressStyle) {
        return makeProgress(activity,proressStyle,null);
    }

    public static ProgressManager makeProgress(Activity activity, int proressStyle, ViewGroup viewGroup) {
        ProgressManager pm = new ProgressManager(activity, proressStyle, viewGroup);
        return pm;
    }

    public ProgressManager show() {
        if (mProgressView != null && mProgressView.getParent() != null) {
            mParentVG.removeView(mProgressView);
        }
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mParentVG.addView(mProgressView, lp);
        return this;
    }


    public ProgressManager dismiss() {
        if (mProgressView != null && mProgressView.getParent() != null) {
            mParentVG.removeView(mProgressView);
			//这一行可以注释掉,这样就可以多次show() 最后再来一把dismiss,不用担心dismiss不掉的问题.
            //mProgressView = null;
        }
        return this;
    }


    /**
     * android.R.id.content 实际上是一个FrameLayout.
     *
     * @param activity
     * @return
     */
    private ViewGroup findContentParent(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        ViewGroup viewGroup = (ViewGroup) decorView.findViewById(android.R.id.content);
        return viewGroup;
    }

    /**
     * 获取进度条以便设置刻度.
     * @return
     */
    public ProgressBar getProgressBar(){
        return this.progressBar;
    }
}
