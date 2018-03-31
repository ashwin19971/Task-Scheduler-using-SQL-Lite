package com.example.ashwingiri.task_scheduler_db.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ashwingiri.task_scheduler_db.Holder.Todo;

import java.util.ArrayList;

/**
 * Created by Ashwin Giri on 3/30/2018.
 */

public class MyDbHelper  extends SQLiteOpenHelper{

    public MyDbHelper(Context context) {
        super(context, "todos.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CMD_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public static final String TABLE_NAME = "todos";
    public static final String ID = "id";
    public static final String TASK = "task";
    public static final String DONE = "done";


    public static final String CMD_CREATE_TABLE =
            " CREATE TABLE IF NOT EXISTS "+TABLE_NAME
                    + " ( "
                    + ID + " INTEGER " + " PRIMARY KEY AUTOINCREMENT " + " , "
                    + TASK + " TEXT " + " , "
                    + DONE + " BOOLEAN "
                    + " ) "+ " ; " ;


    public void insertTodo (Todo todo) {
        ContentValues newTodo = new ContentValues();
        newTodo.put(TASK, todo.getTask());
        newTodo.put(DONE, todo.isChecked());

        SQLiteDatabase db=getWritableDatabase();
        db.insert(TABLE_NAME, null, newTodo);
        db.close();
    }

    public void updateTask(String task,boolean status) {
        Log.d("TAG", "updateTask: "+status);
        SQLiteDatabase db = getWritableDatabase();

        int flag;
        if (status)
            flag = 1;
        else
            flag = 0;
        try {
          //  db.execSQL("UPDATE " + TABLE_NAME + " SET " + DONE + " = " + flag + " WHERE " + TASK + " = " + 'abc' + " ; ");
            db.execSQL("Update " + TABLE_NAME + " set " + DONE + " ='" + flag + "' where " + TASK + " ='" + task + "' ;");
        } catch (Exception e) {

        }
    }
    public void removeTodo (String task) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE "+TASK+"='"+task+"'");
        //  db.execSQL(" Delete from " + TABLE_NAME + " where " + TASK + " = " + task + " ;");
        db.close();
    }

    public ArrayList<Todo> getAllTodos() {
        SQLiteDatabase db=getReadableDatabase();
        Cursor c = db.query(
                TABLE_NAME,
                new String[]{TASK, DONE},
                null,
                null,
                null,
                null,
                null
        );
        ArrayList<Todo> todos = new ArrayList<>();
        c.moveToFirst();
        int taskIndex = c.getColumnIndex(TASK);
        int doneIndex = c.getColumnIndex(DONE);

        while (!c.isAfterLast()) {
            todos.add(new Todo(
                            c.getString(taskIndex),
                            c.getInt(doneIndex)==1
                    )
            );
            c.moveToNext();
        }
        c.close();
        return todos;
    }
}
