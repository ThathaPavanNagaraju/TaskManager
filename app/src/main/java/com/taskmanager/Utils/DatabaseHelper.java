package com.taskmanager.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.taskmanager.Models.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavan Nagaraju on 16-Jun-17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "TaskManager";
    private static final String TABLE_NAME  = "TaskTable";
    private static final String
            KEY_ID = "id" ,KEY_NAME = "name",KEY_DESCRIPTION = "description",
            KEY_DATE_CREATED = "createdDate" , KEY_DATE_UPDATED = "updatedDate";
    private static final int ID_INDEX = 0, NAME_INDEX = 1, DESCRIPTION_INDEX = 2,CREATED_INDEX = 3,UPDATED_INDEX = 4;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                +KEY_DATE_CREATED+" TEXT,"
                +KEY_DATE_UPDATED+" TEXT"
                + ")";
        db.execSQL(CREATE_TASKS_TABLE);
    }

    public void createTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME,task.getName());
        contentValues.put(KEY_DESCRIPTION,task.getDescription());
        contentValues.put(KEY_DATE_CREATED,task.getCreatedDate());
        contentValues.put(KEY_DATE_UPDATED,task.getModifiedDate());
        db.insert(TABLE_NAME,null,contentValues);
        db.close();
    }

    public void editTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME,task.getName());
        contentValues.put(KEY_DESCRIPTION,task.getDescription());
        contentValues.put(KEY_DATE_CREATED,task.getCreatedDate());
        contentValues.put(KEY_DATE_UPDATED,task.getModifiedDate());
        db.update(TABLE_NAME,contentValues,KEY_ID+" =?",new String[]{task.getId()});
        db.close();
    }

    public void deleteTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,KEY_ID+" =?",new String[]{task.getId()});
        db.close();
    }

    public List<Task> getAllTasks(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Task> list = new ArrayList<Task>();
        String query = "SELECT * FROM "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                String id = cursor.getString(ID_INDEX);
                String name =cursor.getString(NAME_INDEX);
                String description = cursor.getString(DESCRIPTION_INDEX);
                String createdDate = cursor.getString(CREATED_INDEX);
                String updatedDate= cursor.getString(UPDATED_INDEX);
                Task task = new Task(name,description,updatedDate,createdDate,id);
                list.add(task);
            }
            while (cursor.moveToNext());
        }
        return list;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
}
