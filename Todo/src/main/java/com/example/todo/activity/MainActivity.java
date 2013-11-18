package com.example.todo.activity;


import android.app.Activity;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.todo.R;
import com.example.todo.db.PersistantModel;
import com.example.todo.view.TodoItemView;

public class MainActivity extends Activity implements View.OnDragListener {
    private static final String LOG_TAG = "MainActivity";

    private LinearLayout mainLayout;
//    private ViewGroup _layout_todolist;
    private ListView _list_todo;
    private Spinner _spinner_priority;

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

        String[] myStringArray = {"ABC", "DEF"};

        ArrayAdapter listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myStringArray);

        _list_todo = (ListView)findViewById(R.id.list_todo);
        _list_todo.setAdapter(listAdapter);

//        _layout_todolist = (ViewGroup)findViewById(R.id.todoListLayout);
//        _layout_todolist.setOnDragListener(this);

        PersistantModel.initialize(this);
//        loadToDoList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void loadToDoList() {
        Cursor c = PersistantModel.getItems();
        c.moveToFirst();

        for ( int i = 0 ; i < c.getCount(); i++ ) {
            addTodoItem(c.getInt(0), c.getString(1), c.getInt(2));
            c.moveToNext();
        }
    }

    private void addTodoItem(int id, String text, int priority) {
        View todoItem = new TodoItemView(this, id, text, priority).getView();
//        todoItem.setOnDragListener(this);

//        _layout_todolist.addView(todoItem, 0);
    }

    public void deleteTodoItem(int id, TodoItemView item) {
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

        int id = PersistantModel.createItem(text, priority);
        addTodoItem(id, text, priority);

        editText.setText("");
    }
/*
    private int calcDropIndex(DragEvent dragEvent) {
//        Log.i("DragAction", "  x = " + dragEvent.getX());
//        Log.i("DragAction", "  y = " + dragEvent.getY());

//        Log.i("DragAction", "  container.y = " + _layout_todolist.getY());
//        Log.i("DragAction", "  container.scrollY = " + _layout_todolist.getScrollY());

        float eventY = dragEvent.getY();
        int sumHeight = 0;
        for ( int i = 0 ; i < _layout_todolist.getChildCount() ; i++ )
        {
            int childHeight = _layout_todolist.getChildAt(i).getHeight();
            if ( eventY >= sumHeight && eventY < sumHeight + childHeight )
                return i;

            sumHeight += childHeight;
        }

        return 0;
    }
*/
    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
//        int action = dragEvent.getAction();
//        View src = (View)dragEvent.getLocalState();
//        Drawable enterShape = getResources().getDrawable(R.drawable.shape_droptarget);
//        Drawable normalShape = getResources().getDrawable(R.drawable.shape);
//        int index;
//
//        switch ( action ) {
//            case DragEvent.ACTION_DRAG_STARTED:
//                break;
//            case DragEvent.ACTION_DRAG_LOCATION:
//                index = calcDropIndex(dragEvent);
//                Log.i("DragAction", "  index = " + index);
//                Log.i("DragAction", _layout_todolist.getChildAt(index).toString());
//                break;
//            case DragEvent.ACTION_DROP:
//                index = calcDropIndex(dragEvent);
//                _layout_todolist.removeView(src);
//                _layout_todolist.addView(src, index);
//                src.setVisibility(View.VISIBLE);
//                break;
//            case DragEvent.ACTION_DRAG_ENDED:
//                if ( !dragEvent.getResult() )
//                    src.setVisibility(View.VISIBLE);
//                break;
//            case DragEvent.ACTION_DRAG_ENTERED:
//                break;
//            case DragEvent.ACTION_DRAG_EXITED:
//                break;
//            default:
//                return true;
//        }
//
//        return true;
        return false;
    }
}
