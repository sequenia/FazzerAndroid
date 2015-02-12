package com.sequenia.fazzer.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

import com.sequenia.fazzer.R;

/**
 * Created by chybakut2004 on 11.02.15.
 */
public class RobotoRegularEditText extends EditText {
    public RobotoRegularEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        //Typeface.createFromAsset doesn't work in the layout editor. Skipping...
        if (isInEditMode()) {
            return;
        }

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "Roboto-Regular.ttf");
        setTypeface(typeface);
    }
}

