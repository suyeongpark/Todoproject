package com.example.todo.view;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by bayja on 2013. 10. 29..
 */
public class TodoItemView extends TextView {
    public TodoItemView(Context context, String text) {
        super(context);

        setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        setBackgroundColor(Color.parseColor("#00FFFFFF"));
        setPadding(20, 10, 10, 10);
        setTextColor(Color.parseColor("#FF7200"));
        setTextSize(13);

        setText(text);
    }
}
