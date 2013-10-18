package com.example.todo;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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

        TextView textView = new TextView(this);

        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setBackgroundColor(Color.parseColor("#00FFFFFF"));
        textView.setPadding(20, 10, 10, 10);
        textView.setTextColor(Color.parseColor("#FF7200"));
        textView.setTextSize(13);

        textView.setText(editText.getText());

        LinearLayout topLL = (LinearLayout)findViewById(R.id.mainLayout);
        topLL.addView(textView, 2);

        editText.setText("");
    }

}
