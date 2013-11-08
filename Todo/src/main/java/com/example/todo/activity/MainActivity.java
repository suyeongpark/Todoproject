package com.example.todo.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.todo.R;
import com.example.todo.db.PersistantModel;
import com.example.todo.view.TodoItemView;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button newBtn = (Button)findViewById(R.id.newBtn);

        newBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)view.getContext()).onNewClick(view);
            }
        });

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
        View todoItem = new TodoItemView(this, id, text, priority);

        LinearLayout topLL = (LinearLayout)findViewById(R.id.todoListLayout);
        topLL.addView(todoItem, 0);
    }

    public void deleteTodoItem(int id, TodoItemView view) {
        LinearLayout topLL = (LinearLayout)findViewById(R.id.todoListLayout);
        topLL.removeView(view);
    }

    public void onNewClick(View view) {
        EditText editText = (EditText)findViewById(R.id.editText);
        //RadioGroup rg = (RadioGroup)findViewById(R.id.radio);
        RadioButton rb1 = (RadioButton)findViewById(R.id.radioButton);
        RadioButton rb2 = (RadioButton)findViewById(R.id.radioButton2);
        RadioButton rb3 = (RadioButton)findViewById(R.id.radioButton3);

        String text = editText.getText().toString();
        int priority = 3;

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

}
