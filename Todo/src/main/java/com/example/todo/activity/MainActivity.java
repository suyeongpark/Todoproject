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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.todo.R;
import com.example.todo.db.PersistantModel;
import com.example.todo.view.TodoItemView;

public class MainActivity extends Activity {
    private static final String LOG_TAG = "MainActivity";

    private ViewGroup _layout_todolist;
    private Spinner _spinner_priority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _spinner_priority = (Spinner)findViewById(R.id.spinner_priority);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.priority_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _spinner_priority.setAdapter(adapter);

        Button newBtn = (Button)findViewById(R.id.newBtn);

        newBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)view.getContext()).onNewClick(view);
            }
        });

        _layout_todolist = (ViewGroup)findViewById(R.id.todoListLayout);
        _layout_todolist.setOnDragListener(new MyDragListener());

        PersistantModel.initialize(this);
        loadToDoList();
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

        _layout_todolist.addView(todoItem, 0);
    }

    public void deleteTodoItem(int id, TodoItemView item) {
        _layout_todolist.removeView(item.getView());
    }

    public void onNewClick(View view) {
        EditText editText = (EditText)findViewById(R.id.editText);
        //RadioGroup rg = (RadioGroup)findViewById(R.id.radio);
        RadioButton rb1 = (RadioButton)findViewById(R.id.radioButton);
        RadioButton rb2 = (RadioButton)findViewById(R.id.radioButton2);
        RadioButton rb3 = (RadioButton)findViewById(R.id.radioButton3);

        String text = editText.getText().toString();

        // priority 확인
        int priority = 3;

        Log.i(getLocalClassName(), "Selected Item:" + _spinner_priority.getSelectedItem());
        Log.i(getLocalClassName(), "Selected ItemId: " + _spinner_priority.getSelectedItemId());

        if ( rb1.isChecked() )
            priority = 1;
        else if ( rb2.isChecked() )
            priority = 3;
        else if ( rb3.isChecked() )
            priority = 5;

        int id = PersistantModel.createItem(text, priority);
        addTodoItem(id, text, priority);

        editText.setText("");
    }

    public void onDbClick(View view) {
        Cursor c = PersistantModel.getItems();
        c.moveToLast();
        System.out.println("  --- column count : " + c.getColumnCount());
        System.out.println("  --- count : " + c.getCount());

        Toast.makeText(this, c.getString(1), Toast.LENGTH_SHORT).show();
    }

    class MyDragListener implements View.OnDragListener {
        Drawable enterShape = getResources().getDrawable(R.drawable.shape_droptarget);
        Drawable normalShape = getResources().getDrawable(R.drawable.shape);

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            View src = (View)event.getLocalState();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    Log.i("DragAction", "ACTION_DRAG_STARTED : ");
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
//                    v.setBackgroundDrawable(enterShape);
                    Log.i("DragAction", "ACTION_DRAG_ENTERED : ");
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.i("DragAction", "ACTION_DRAG_EXITED : ");
//                    v.setBackgroundDrawable(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                    Log.i("DragAction", "ACTION_DROP : ");
                    // Dropped, reassign View to ViewGroup
                    ViewGroup owner = (ViewGroup) src.getParent();
                    owner.removeView(src);
//                    LinearLayout container = (LinearLayout) v;
//                    container.addView(view);
                    owner.addView(src, 0);
                    src.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    Log.i("DragAction", "ACTION_DRAG_ENDED : ");
                    src.setVisibility(View.VISIBLE);
//                    v.setBackgroundDrawable(normalShape);
                default:
                    break;
            }
            return true;
        }
    }
}
