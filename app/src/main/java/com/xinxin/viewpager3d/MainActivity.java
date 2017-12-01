package com.xinxin.viewpager3d;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xinxin.viewpager3d.widget.InfinitePagerAdapter;
import com.xinxin.viewpager3d.widget.RotateYTransformer;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private InfinitePagerAdapter mPagerAdapter;
    private ViewPager mDetailsViewPager;
    private InfinitePagerAdapter mDetailsPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mPagerAdapter = new InfinitePagerAdapter(new Pager3DAdapter());
        mViewPager = (ViewPager) findViewById(R.id.home_viewpager);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setPageMargin(40);
        mViewPager.setAdapter(mPagerAdapter);
        RotateYTransformer transformer = new RotateYTransformer();
        transformer.setOnTransformListener(new RotateYTransformer.OnTransformListener() {
            @Override
            public void onTransform(View page, float position) {
                if (position < 0.5f && position >-0.5f) {
                    page.setBackground(getResources().getDrawable(R.drawable.viewpager_item_bg_highlight));
                } else {
                    page.setBackground(getResources().getDrawable(R.drawable.viewpager_item_bg));
                }
            }
        });
        mViewPager.setPageTransformer(false, transformer);

        mDetailsViewPager = (ViewPager) findViewById(R.id.home_details_viewpager);
        mDetailsViewPager.setOffscreenPageLimit(2);
        mDetailsViewPager.setPageMargin(150);
        mDetailsPagerAdapter = new InfinitePagerAdapter(new Pager3DDetailsAdapter());
        mDetailsViewPager.setAdapter(mDetailsPagerAdapter);
    }

    @Override
    public void onBackPressed() {
        if (mDetailsViewPager.getVisibility() == View.VISIBLE) {
            hideDetailsViewPager();
            showViewPager(mDetailsViewPager.getCurrentItem());
        } else {
            super.onBackPressed();
        }
    }

    private void showDetailsViewPager(int position) {
        mDetailsViewPager.setCurrentItem(position, true);
        mDetailsViewPager.setVisibility(View.VISIBLE);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(mDetailsViewPager, "scaleX", 0.0f, 1.0f),
                ObjectAnimator.ofFloat(mDetailsViewPager, "scaleY", 0.0f, 1.0f)
        );
        animatorSet.setDuration(500);
        animatorSet.start();
    }

    private void hideDetailsViewPager() {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(mDetailsViewPager, "scaleX", 1.0f, 0.0f),
                ObjectAnimator.ofFloat(mDetailsViewPager, "scaleY", 1.0f, 0.0f)
        );
        animatorSet.setDuration(500);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mDetailsViewPager.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }

    private void showViewPager(int position) {
        mViewPager.setCurrentItem(position, true);
        mViewPager.setVisibility(View.VISIBLE);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(mViewPager, "scaleX", 0.0f, 1.0f),
                ObjectAnimator.ofFloat(mViewPager, "scaleY", 0.0f, 1.0f)
        );
        animatorSet.setDuration(500);
        animatorSet.start();
    }

    private void hideViewPager() {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(mViewPager, "scaleX", 1.0f, 0.0f),
                ObjectAnimator.ofFloat(mViewPager, "scaleY", 1.0f, 0.0f)
        );
        animatorSet.setDuration(500);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mViewPager.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }

    private class Pager3DAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = getLayoutInflater().inflate(R.layout.viewpager_item, null);
            TextView textView = (TextView) view.findViewById(R.id.viewpager_item_text);
            view.setBackground(getResources().getDrawable(R.drawable.viewpager_item_bg));
            textView.setText(String.valueOf(position + 1));
            container.addView(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDetailsViewPager(position);
                    hideViewPager();
                }
            });

            return view;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private class Pager3DDetailsAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = getLayoutInflater().inflate(R.layout.details_viewpager_item, null);
            TextView textView = (TextView) view.findViewById(R.id.details_viewpager_item_text);
            textView.setText(String.valueOf(position + 1));
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
