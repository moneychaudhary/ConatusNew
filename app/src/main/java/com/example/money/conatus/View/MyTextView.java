package com.example.money.conatus.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.example.money.conatus.App;

/**
 * Created by #money on 10/22/2016.
 */

public class MyTextView extends TextView {
    public MyTextView(Context context) {
        this(context,null);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(App.sQuickSandFontBold);

    }
}
