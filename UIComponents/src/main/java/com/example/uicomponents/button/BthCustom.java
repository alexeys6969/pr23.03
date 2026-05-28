package com.example.uicomponents.button;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.uicomponents.R;

public class BthCustom extends ConstraintLayout {
    public Button Bth;
    private int currentLayoutId = -1;

    public enum TypeButton {
        PRIMARY, TETRTIARY, SECONDARY, OFF, ON
    }

    public BthCustom(@NonNull Context context) {
        super(context);
        init(null);
    }

    public BthCustom(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(null);
    }

    public BthCustom(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(null);
    }

    public void init(Integer idLayout) {
        if (idLayout == null) return;
        this.currentLayoutId = idLayout;
        this.removeAllViews();
        LayoutInflater.from(this.getContext()).inflate(idLayout, this, true);
        Bth = findViewById(R.id.bth);
    }

    public void init(String value, TypeButton type) {
        if (Bth == null) {
            Bth = findViewById(R.id.bth);
        }
        if (Bth != null) {
            Bth.setText(value);
            if (type == TypeButton.PRIMARY || type == TypeButton.ON) {
                Bth.setBackgroundResource(R.drawable.bth_primary);
                Bth.setTextColor(Color.parseColor("#FFFFFF"));
            } else if (type == TypeButton.SECONDARY) {
                Bth.setBackgroundResource(R.drawable.bth_secondary);
                Bth.setTextColor(Color.parseColor("#1A6FEE"));
            } else if (type == TypeButton.TETRTIARY) {
                Bth.setBackgroundResource(R.drawable.bth_tetriary);
                Bth.setTextColor(Color.parseColor("#2D2C2C"));
            } else if (type == TypeButton.OFF) {
                Bth.setBackgroundResource(R.drawable.bth_tetriary);
                Bth.setTextColor(Color.parseColor("#7E7E9A"));
            }
        }
    }

    /** Полностью пересоздаёт кнопку с новым текстом и стилем */
    public void reinit(String text, TypeButton type) {
        if (currentLayoutId == -1) return;
        this.removeAllViews();
        LayoutInflater.from(this.getContext()).inflate(currentLayoutId, this, true);
        Bth = findViewById(R.id.bth);
        init(text, type);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (Bth != null) {
            Bth.setEnabled(enabled);
        }
    }
}