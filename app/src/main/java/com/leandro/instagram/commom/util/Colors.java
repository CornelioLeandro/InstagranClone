package com.leandro.instagram.commom.util;

import android.content.Context;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

public final class Colors {
    public static int getColor(Context context, @ColorRes int colorId){
        return ContextCompat.getColor(context, colorId);
    }
}
