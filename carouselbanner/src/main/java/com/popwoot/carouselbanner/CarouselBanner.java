package com.popwoot.carouselbanner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;


import androidx.recyclerview.widget.RecyclerView;

import com.popwoot.carouselbanner.adapter.CarouselAdapter;
import com.popwoot.carouselbanner.base.CarouselBannerBase;
import com.popwoot.carouselbanner.interfaces.OnCarouselItemClickListener;
import com.popwoot.carouselbanner.layoutmanager.BannerLayoutManager;

import java.util.List;


public class CarouselBanner extends CarouselBannerBase<BannerLayoutManager, CarouselAdapter> {

    public CarouselBanner(Context context) {
        this(context, null);
    }

    public CarouselBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CarouselBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onBannerScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (bannerSize < 2) return;
        int firstReal = mLayoutManager.findFirstVisibleItemPosition();
        View viewFirst = mLayoutManager.findViewByPosition(firstReal);
        float width = getWidth();
        if (width != 0 && viewFirst != null) {
            float right = viewFirst.getRight();
            float ratio = right / width;
            if (ratio > 0.8) {
                if (currentIndex != firstReal) {
                    currentIndex = firstReal;
                    refreshIndicator();
                }
            } else if (ratio < 0.2) {
                if (currentIndex != firstReal + 1) {
                    currentIndex = firstReal + 1;
                    refreshIndicator();
                }
            }
        }
    }

    @Override
    protected void onBannerScrollStateChanged(RecyclerView recyclerView, int newState) {
        int first = mLayoutManager.findFirstVisibleItemPosition();
        int last = mLayoutManager.findLastVisibleItemPosition();
        if (currentIndex != first && first == last) {
            currentIndex = first;
            refreshIndicator();
        }
    }

    @Override
    protected BannerLayoutManager getLayoutManager(Context context, int orientation) {
        return new BannerLayoutManager(context, orientation, false, speedPerPixelMillisecond);
    }

    @Override
    protected CarouselAdapter getAdapter(List<String> list, OnCarouselItemClickListener onBannerItemClickListener) {
        return new CarouselAdapter(list, onBannerItemClickListener);
    }


}