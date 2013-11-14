package com.example.todo.view;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.todo.R;
import com.example.todo.activity.MainActivity;
import com.example.todo.db.PersistantModel;

/**
 * Created by bayja on 2013. 10. 29..
 */

public class TodoItemView implements View.OnClickListener, View.OnLongClickListener {
    private static int[] colors = {Color.parseColor("#FF0000"), Color.parseColor("#000000"), Color.parseColor("#00FF00"), Color.parseColor("#000000"), Color.parseColor("#0000FF")};

    private View _view;
    public int rowId;

    public TodoItemView(Context context, int rowId, String text, int priority) {
        this.rowId = rowId;

        _view = View.inflate(context, R.layout.todo_item, null);
        _view.setBackgroundColor(colors[priority-1]);
//        item.setBackgroundColor(colors[priority-1]);

        TextView textView = (TextView)_view.findViewById(R.id.textView);
        textView.setText(priority + " -- " + text);

        Button deleteBtn = (Button)_view.findViewById(R.id.delBtn);
        deleteBtn.setOnClickListener(this);

//        this.setLayoutParams(new LayoutParams());

        _view.setOnLongClickListener(this);
    }

    public View getView() {
        return _view;
    }

    @Override
    public void onClick(View view) {
        PersistantModel.deleteItem(rowId);
        ((MainActivity)_view.getContext()).deleteTodoItem(rowId, this);
    }

    @Override
    public boolean onLongClick(View view) {
        ClipData data = ClipData.newPlainText("", "");
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
        view.startDrag(data, shadowBuilder, view, 0);
        view.setVisibility(View.INVISIBLE);
        return true;
    }
}
