package com.example.todo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by parksuyeong on 2013. 10. 28..
 */
public class LocalDB extends SQLiteOpenHelper {
    public LocalDB(Context context) {
        super(context, "db_user", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE db_user (id integer primary key autoincrement, content TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
