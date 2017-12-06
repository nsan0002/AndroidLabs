package com.example.jo_1.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper {
        public final static String name = "MyTable";
        public final static String DATABASE_NAME = "Messages.db";
        public final static int VERSION_NUM =9;
        public final static String KEY_ID = "id";
        public final static String KEY_MESSAGE = "message";
        public SQLiteDatabase mdb;
        public static final String[] KEYS = new String[]{
            KEY_ID, KEY_MESSAGE};



        public ChatDatabaseHelper(Context ctx) {
            super(ctx, DATABASE_NAME, null, VERSION_NUM);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + name + " ("
                    + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                    + KEY_MESSAGE + " TEXT" + ");");
            Log.i("ChatDatabaseHelper", "Calling onCreate");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
            db.execSQL("DROP TABLE IF EXISTS " + name);
            onCreate(db);
            Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVer + "newVersion=" + newVer);
        }


    // open database
    public ChatDatabaseHelper open() {
        if(mdb == null){
            mdb = getWritableDatabase();
        }
        return this;
    }

    public void close(){
        if(mdb != null){
            mdb.close();
        }
    }

    // retrieving data from database
    public Cursor getChatMessages(){
        return mdb.query(name, KEYS, null, null, null, null, null);
    }

    public String getMessageFromCursor(Cursor cursor){
        String msg = cursor.getString(cursor.getColumnIndex(KEY_MESSAGE));
        return msg;
    }

    public int getIdFromCursor(Cursor cursor){
        int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
        return id;
    }

    public void insert(ContentValues content){
        mdb.insert(name, null, content);
    }

    public void remove(long id){
        int deletedRecord =  mdb.delete(name, "_id" + "=" + id, null);
        Log.i("Deleted ",Integer.toString(deletedRecord));
    }

    }