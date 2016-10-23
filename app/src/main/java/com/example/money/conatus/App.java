package com.example.money.conatus;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by #money on 10/22/2016.
 */

public class App extends Application {
    public static Typeface sQuickSandFont;
    public static Typeface sQuickSandFontBold;

    @Override
    public void onCreate() {
        super.onCreate();
        initTypeFace();
    }

    private  void initTypeFace(){
        sQuickSandFont = Typeface.createFromAsset(getAssets(), "Quicksand-Regular.ttf");
        sQuickSandFontBold = Typeface.createFromAsset(getAssets(), "Quicksand-Bold.ttf");
    }
}
