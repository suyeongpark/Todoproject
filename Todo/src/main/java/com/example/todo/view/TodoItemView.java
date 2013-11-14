package com.example.todo.view;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.view.MotionEvent;
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

public class TodoItemView extends LinearLayout implements View.OnClickListener, View.OnLongClickListener {
    private static int[] colors = {Color.parseColor("#FF0000"), Color.parseColor("#000000"), Color.parseColor("#00FF00"), Color.parseColor("#000000"), Color.parseColor("#0000FF")};
    public int rowId;

    public TodoItemView(Context context, int rowId, String text, int priority) {
        super(context);

        this.rowId = rowId;

        View item = View.inflate(getContext(), R.layout.todo_item, null);
        item.setBackgroundColor(colors[priority-1]);
//        item.setBackgroundColor(colors[priority-1]);

        TextView textView = (TextView)item.findViewById(R.id.textView);
        textView.setText(priority + " -- " + text);

        Button deleteBtn = (Button)item.findViewById(R.id.delBtn);
        deleteBtn.setOnClickListener(this);

//        this.setLayoutParams(new LayoutParams());

        setOnLongClickListener(this);

        addView(item);
    }

    @Override
    public void onClick(View view) {
        PersistantModel.deleteItem(rowId);
        ((MainActivity)getContext()).deleteTodoItem(rowId, this);
    }

    @Override
    public boolean onLongClick(View view) {
        ClipData data = ClipData.newPlainText("", "");
        DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
        view.startDrag(data, shadowBuilder, view, 0);
        view.setVisibility(View.INVISIBLE);
        return true;
    }
}
