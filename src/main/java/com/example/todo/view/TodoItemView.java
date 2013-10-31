package com.example.todo.view;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todo.R;
import com.example.todo.activity.MainActivity;
import com.example.todo.db.LocalDB;
import com.example.todo.db.PersistantModel;

/**
 * Created by bayja on 2013. 10. 29..
 */

public class TodoItemView extends LinearLayout implements View.OnClickListener {
    public int id;

    public TodoItemView(Context context, int id, String text) {
        super(context);

        this.id = id;

        TextView textView = new TextView(context);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setBackgroundColor(Color.parseColor("#00FFFFFF"));
        textView.setPadding(20, 10, 10, 10);
        textView.setTextColor(Color.parseColor("#FF7200"));
        textView.setTextSize(13);
        textView.setText(id + " -- " + text);

        addView(textView);

        Button deleteBtn = new Button(context);
        deleteBtn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        deleteBtn.setBackgroundColor(Color.parseColor("#00FF00FF"));
        deleteBtn.setPadding(20, 10, 10, 10);
        deleteBtn.setTextColor(Color.parseColor("#FF7200"));
        deleteBtn.setTextSize(13);
        deleteBtn.setText("X");
        deleteBtn.setOnClickListener(this);

        addView(deleteBtn);
    }

    @Override
    public void onClick(View view) {
        PersistantModel.deleteItem(id);
        ((MainActivity)getContext()).deleteTodoItem(this.id, this);
    }
}
