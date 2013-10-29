package com.example.todo.activity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todo.db.LocalDB;
import com.example.todo.R;
import com.example.todo.view.TodoItemView;

public class MainActivity extends Activity {
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button newBtn = (Button)findViewById(R.id.newBtn);

        newBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)view.getContext()).onClick(view);
            }
        });

        LocalDB dbHelper = new LocalDB(this);
        db = dbHelper.getWritableDatabase();

        loadToDoList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    private void loadToDoList() {
        Cursor c = db.rawQuery("SELECT id, content FROM db_user ORDER BY id ASC", null);
        c.moveToFirst();

        for ( int i = 0 ; i < c.getCount(); i++ ) {
            addTodoItem(c.getString(1));
            c.moveToNext();
        }
    }

    private void addTodoItem(String text) {
        TextView textView = new TodoItemView(this, text);

        LinearLayout topLL = (LinearLayout)findViewById(R.id.todoListLayout);
        topLL.addView(textView, 0);
    }


    public void onClick(View view) {
        EditText editText = (EditText)findViewById(R.id.editText);
        String text = editText.getText().toString();

        db.execSQL("INSERT INTO db_user (content) VALUES ('" + text + "');");
        addTodoItem(text);
        editText.setText("");
    }

    public void onDbClick(View view) {
        Cursor c = db.rawQuery("SELECT id, content FROM db_user ORDER BY id DESC", null);
        c.moveToFirst();
        System.out.println("  --- column count : " + c.getColumnCount());
        System.out.println("  --- count : " + c.getCount());

        Toast.makeText(this, c.getString(1), Toast.LENGTH_SHORT).show();
    }

}
