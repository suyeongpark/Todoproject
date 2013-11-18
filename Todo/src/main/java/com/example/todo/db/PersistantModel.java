package com.example.todo.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by bayja on 2013. 10. 29..
 */
public class PersistantModel {
    private static SQLiteDatabase db;
//    private static Context context;

    static {
    }

    public static void initialize(Context context) {
//        PersistantModel.context = context;
        db = new LocalDB(context).getWritableDatabase();
    }

    public static Cursor getItems() {
        return db.rawQuery("SELECT _id, content, priority FROM db_user ORDER BY priority DESC", null);
    }

    public static int createItem(String text, int priority) {
        db.execSQL("INSERT INTO db_user (content, priority) VALUES ('" + text + "', " + priority + ");");
        Cursor c = db.rawQuery("SELECT _id FROM db_user ORDER BY _id DESC LIMIT 1", null);
        c.moveToFirst();
        return c.getInt(0);
    }

    public static void deleteItem(int id) {
        db.execSQL("DELETE FROM db_user WHERE _id = " + id);
    }
}
