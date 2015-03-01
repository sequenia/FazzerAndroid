package com.sequenia.fazzer.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sequenia.fazzer.R;
import com.sequenia.fazzer.adapters.HomeActivityPagerAdapter;

/**
 * Created by chybakut2004 on 26.02.15.
 */
public class SlidingTabLayoutWithButton extends SlidingTabLayout {
    public SlidingTabLayoutWithButton(Context context) {
        super(context);
    }

    public SlidingTabLayoutWithButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlidingTabLayoutWithButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void populateTabStrip() {
        super.populateTabStrip();

        final SlidingTabStrip tabStrip = getTabStrip();
        final HomeActivityPagerAdapter adapter = (HomeActivityPagerAdapter) getViewPager().getAdapter();

        Button button = adapter.getButton();
        int buttonSize = (int) (getResources().getDimensionPixelSize(R.dimen.tab_view_text_size) + getResources().getDimensionPixelSize(R.dimen.tab_view_padding_dips) * getResources().getDisplayMetrics().density);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(buttonSize, buttonSize);
        params.gravity = Gravity.CENTER_VERTICAL;
        params.leftMargin = (int) (TAB_VIEW_PADDING_DIPS * getResources().getDisplayMetrics().density);
        button.setLayoutParams(params);
        tabStrip.addView(button);
    }
}
