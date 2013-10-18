package com.example.todo;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void mOnClick(View v) {

        EditText editText = (EditText)findViewById(R.id.editText);

        TextView textView;

        int resID = getResources().getIdentifier(String.format("textView0%d", count), "id", getPackageName());
        textView = (TextView) findViewById(resID);
        count++;
        if ( count > 3 )
            count = 1;

        textView.setText(editText.getText());
        editText.setText("");
    }

}
