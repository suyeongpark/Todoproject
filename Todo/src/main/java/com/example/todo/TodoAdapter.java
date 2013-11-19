package com.example.todo;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class TodoAdapter extends CursorAdapter {
    private static int[] colors = {Color.parseColor("#FF0000"), Color.parseColor("#000000"), Color.parseColor("#00FF00"), Color.parseColor("#000000"), Color.parseColor("#0000FF")};

    private LayoutInflater _inflater;

    public TodoAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);

        _inflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return _inflater.inflate(R.layout.todo_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        int priority = cursor.getInt(2);
        String content = cursor.getString(1);

        view.setBackgroundColor(colors[priority-1]);

        TextView tv1 = (TextView)view.findViewById(R.id.textView);

        tv1.setText(priority + " -- " + content);
    }
}
