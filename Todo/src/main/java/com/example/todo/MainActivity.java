package com.example.todo;

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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {
    SQLiteDatabase db;

    int count = 1;
//
//    class ButtonClickListener implements View.OnClickListener {
//
//        @Override
//        public void onClick(View view) {
//            Toast.makeText(view.getContext(), "clicked", Toast.LENGTH_LONG).show();
//        }
//    }

    class MyTextView extends TextView {

        public MyTextView(Context context, Editable text) {
            super(context);

            setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            setBackgroundColor(Color.parseColor("#00FFFFFF"));
            setPadding(20, 10, 10, 10);
            setTextColor(Color.parseColor("#FF7200"));
            setTextSize(13);

            setText(text);
        }

        public MyTextView(Context context, String text) {
            super(context);

            setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            setBackgroundColor(Color.parseColor("#00FFFFFF"));
            setPadding(20, 10, 10, 10);
            setTextColor(Color.parseColor("#FF7200"));
            setTextSize(13);

            setText(text);
        }

            public boolean onTouchEvent(android.view.MotionEvent event) {
            Toast.makeText(getContext(), "My Text View", Toast.LENGTH_LONG).show();

            return false;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button)findViewById(R.id.button);
//        button.setOnClickListener(new ButtonClickListener());
//        button.setOnClickListener(this);

        View.OnClickListener a = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)view.getContext()).onClick(view);
            }
        };

        button.setOnClickListener( a );

        LocalDB dbHelper = new LocalDB(this);
        db = dbHelper.getWritableDatabase();

        loadToDoList();
    }

    private void loadToDoList() {
        Cursor c = db.rawQuery("SELECT id, content FROM db_user ORDER BY id ASC", null);
        c.moveToFirst();

        for ( int i = 0 ; i < c.getCount(); i++ ) {
            TextView textView = new MyTextView(this, c.getString(1));

            LinearLayout topLL = (LinearLayout)findViewById(R.id.todoListLayout);
            topLL.addView(textView, 0);

            c.moveToNext();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        EditText editText = (EditText)findViewById(R.id.editText);
        TextView textView = new MyTextView(this, editText.getText());

        LinearLayout topLL = (LinearLayout)findViewById(R.id.todoListLayout);
        topLL.addView(textView, 0);

        db.execSQL("INSERT INTO db_user (content) VALUES ('" + editText.getText() + "');");
        editText.setText("");
    }

    public void onDbClick(View view) {
        Cursor c = db.rawQuery("SELECT id, content FROM db_user ORDER BY id DESC", null);
        c.moveToFirst();
        System.out.println("  --- column count : " + c.getColumnCount());
        System.out.println("  --- count : " + c.getCount());

        Toast.makeText(this, c.getString(1), Toast.LENGTH_SHORT).show();
    }

//    public void mOnClick(View v) {
//
//        EditText editText = (EditText)findViewById(R.id.editText);
//
//        TextView textView = new TextView(this);
//
//        //ScrollView scrollView = (ScrollView)findViewById(R.id.scroll);
//
//        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//        textView.setBackgroundColor(Color.parseColor("#00FFFFFF"));
//        textView.setPadding(20, 10, 10, 10);
//        textView.setTextColor(Color.parseColor("#FF7200"));
//        textView.setTextSize(13);
//
//        textView.setText(editText.getText());
//
//        LinearLayout topLL = (LinearLayout)findViewById(R.id.todoListLayout);
//        topLL.addView(textView, 0);
//
//        editText.setText("");
//        //scrollView.setVerticalScrollbarPosition(0);
//    }

}
