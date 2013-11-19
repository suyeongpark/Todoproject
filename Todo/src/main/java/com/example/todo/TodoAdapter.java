package com.example.todo;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.todo.db.PersistantModel;

public class TodoAdapter extends CursorAdapter implements View.OnClickListener {
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
        view.setTag(new Integer(cursor.getInt(0)));

        TextView tv1 = (TextView)view.findViewById(R.id.textView);
        Button btn_delete = (Button)view.findViewById(R.id.delBtn);

        final int todoId = cursor.getInt(0);
//        Log.d("Adapter", "todoId = " + todoId);

        btn_delete.setOnClickListener(this);

        tv1.setText(priority + " -- " + content);
    }

    @Override
    public void onClick(View view) {
        int todoId = (Integer)((View)view.getParent()).getTag();
//        Log.d("AdapteronClick", "todoId = " + todoId);
        PersistantModel.deleteItem(todoId);
        Cursor newCursor = PersistantModel.getDb().rawQuery("SELECT * FROM db_user ORDER BY _id", null);
        changeCursor(newCursor);
        notifyDataSetChanged();
    }
}
