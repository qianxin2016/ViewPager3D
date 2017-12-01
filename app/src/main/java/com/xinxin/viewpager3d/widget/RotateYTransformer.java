package com.xinxin.viewpager3d.widget;

import android.support.v4.view.ViewPager;
import android.view.View;

public class RotateYTransformer implements ViewPager.PageTransformer {
    private float mMaxRotate = 25.0f;
    private OnTransformListener mOnTransformListener;

    public interface OnTransformListener {
        void onTransform(View page, float position);
    }

    public RotateYTransformer() {
    }

    public RotateYTransformer(float maxRotate) {
        mMaxRotate = maxRotate;
    }

    public void setOnTransformListener(OnTransformListener listener) {
        mOnTransformListener = listener;
    }

    @Override
    public void transformPage(View page, float position) {
        page.setPivotY(page.getHeight() / 2);

        if (position < -1) { // [-Infinity, -1)
            // This page is way off-screen to the left.
            page.setPivotX(page.getWidth());
            page.setRotationY(-1 * mMaxRotate);
        } else if (position <= 1) { // [-1,1]
            if (position < 0) { // [0, -1]
                page.setPivotX(page.getWidth());
                page.setRotationY(position * mMaxRotate);
            } else { // [1, 0]
                page.setPivotX(0);
            }
            page.setRotationY(position * mMaxRotate);
        } else { // (1, +Infinity]
            // This page is way off-screen to the right.
            page.setPivotX(0);
            page.setRotationY(1 * mMaxRotate);
        }

        if (mOnTransformListener != null) {
            mOnTransformListener.onTransform(page, position);
        }
    }
}

