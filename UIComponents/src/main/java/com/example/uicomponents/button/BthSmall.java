package com.example.uicomponents.button;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.uicomponents.R;

public class BthSmall extends BthCustom {
    public BthSmall(@NonNull Context context) {
        super(context);
        init(R.layout.bth_small_merge);
    }

    public BthSmall(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(R.layout.bth_small_merge);
    }

    public BthSmall(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(R.layout.bth_small_merge);
    }
}