package com.example.todo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.logging.Logger;

/**
 * Created by parksuyeong on 2013. 10. 28..
 */
public class LocalDB extends SQLiteOpenHelper {
    private static int DB_VERSION = 20131121;

    public LocalDB(Context context) {
        super(context, "db_user", null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE db_user (_id integer primary key autoincrement, content TEXT, priority int);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE db_user;");
//        db.execSQL("CREATE TABLE db_user (_id integer primary key autoincrement, content TEXT, priority int);");
    }
}

/**
 *
 * http://stackoverflow.com/questions/3505900/sqliteopenhelper-onupgrade-confusion-android
 *
 * http://grepcode.com/file_/repository.grepcode.com/java/ext/com.google.android/android-apps/4.0.1_r1/com/android/providers/calendar/CalendarDatabaseHelper.java/?v=source
 *
 */