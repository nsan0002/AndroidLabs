package com.example.jo_1.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper {
        public final static String name = "MyTable";
        public final static String DATABASE_NAME = "Messages.db";
        public final static int VERSION_NUM = 5;
        public final static String KEY_ID = "id";
        public final static String KEY_MESSAGE = "message";

//        ChatDatabaseHelper db = new ChatDatabaseHelper(this);
//        SQLiteOpenHelper dB = db.getWritableDatabase();


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
    }