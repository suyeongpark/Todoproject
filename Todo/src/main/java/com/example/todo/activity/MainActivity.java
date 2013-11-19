package com.example.todo.activity;


import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.todo.R;
import com.example.todo.TodoAdapter;
import com.example.todo.db.PersistantModel;
import com.example.todo.view.TodoItemView;

public class MainActivity extends Activity {
    private static final String LOG_TAG = "MainActivity";

    private LinearLayout mainLayout;
    private ListView _list_todo;
    private Spinner _spinner_priority;

    private SimpleCursorAdapter _cursorAdapter;
    private TodoAdapter _todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = (LinearLayout)findViewById(R.id.mainLayout);

        _spinner_priority = (Spinner)findViewById(R.id.spinner_priority);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.priority_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _spinner_priority.setAdapter(adapter);
        _spinner_priority.setSelection(1);

        Button newBtn = (Button)findViewById(R.id.btnNew);

        newBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)view.getContext()).onNewClick(view);
            }
        });

        PersistantModel.initialize(this);

        String[] fromColumns = {"content"};
        int[] toViews = {R.id.textView};

        SQLiteDatabase db = PersistantModel.getDb();
        Cursor cursor = db.rawQuery("SELECT * FROM db_user ORDER BY _id", null);
//        _cursorAdapter = new SimpleCursorAdapter(this, R.layout.todo_item, cursor, fromColumns, toViews, 0);
        _todoAdapter = new TodoAdapter(this, cursor, 0);

        _list_todo = (ListView)findViewById(R.id.list_todo);
        _list_todo.setAdapter(_todoAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void deleteTodoItem(int id, TodoItemView item) {
        Cursor newCursor = PersistantModel.getDb().rawQuery("SELECT * FROM db_user ORDER BY _id", null);
//        _cursorAdapter.changeCursor(newCursor);
//        _cursorAdapter.notifyDataSetChanged();
        _todoAdapter.changeCursor(newCursor);
        _todoAdapter.notifyDataSetChanged();

//        _layout_todolist.removeView(item.getView());
    }

    public void onNewClick(View view) {
        EditText editText = (EditText)findViewById(R.id.editText);
        String text = editText.getText().toString();

        // priority 확인
        int priority = 3;
        int selected = (int)_spinner_priority.getSelectedItemId();

        switch ( selected ) {
            case 0:
                priority = 1;
                break;
            case 1:
                priority = 3;
                break;
            case 2:
                priority = 5;
                break;
        }

        PersistantModel.createItem(text, priority);

        editText.setText("");

        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

        Cursor newCursor = PersistantModel.getDb().rawQuery("SELECT * FROM db_user ORDER BY _id", null);
//        _cursorAdapter.changeCursor(newCursor);
//        _cursorAdapter.notifyDataSetChanged();
        _todoAdapter.changeCursor(newCursor);
        _todoAdapter.notifyDataSetChanged();
    }
}
