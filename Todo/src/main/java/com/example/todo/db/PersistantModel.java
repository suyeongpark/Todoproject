package com.example.todo.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by bayja on 2013. 10. 29..
 */
public class PersistantModel {
    private static SQLiteDatabase _db;
//    private static Context context;

    static {
    }

    public static void initialize(Context context) {
        _db = new LocalDB(context).getWritableDatabase();
    }

    public static Cursor getItems() {
        return _db.rawQuery("SELECT _id, content, priority FROM db_user ORDER BY priority DESC", null);
    }

    public static int createItem(String text, int priority) {
        _db.execSQL("INSERT INTO db_user (content, priority) VALUES ('" + text + "', " + priority + ");");
        Cursor c = _db.rawQuery("SELECT _id FROM db_user ORDER BY _id DESC LIMIT 1", null);
        c.moveToFirst();
        return c.getInt(0);
    }

    public static void deleteItem(int id) {
        _db.execSQL("DELETE FROM db_user WHERE _id = " + id);
    }

    public static SQLiteDatabase getDb() {
        return _db;
    }
}
