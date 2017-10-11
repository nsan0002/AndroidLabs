package com.example.jo_1.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends Activity {

    protected ListView listV;
    protected EditText editT;
    protected Button btn;
    protected ArrayList<String> aList = new ArrayList<>();
    protected  ChatDatabaseHelper mydbase;
    protected SQLiteDatabase dbase;
    protected Cursor csr;


    @Override
        protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);


        listV = (ListView) findViewById(R.id.commentsList);
        editT = (EditText) findViewById(R.id.comment);
        btn = (Button) findViewById(R.id.SendB);


        final ChatAdapter messageAdapter = new ChatAdapter(this);
        listV.setAdapter(messageAdapter);

        mydbase = new ChatDatabaseHelper(getApplicationContext());
        dbase = mydbase.getReadableDatabase();


        csr = dbase.rawQuery("select * from MyTable", null);
        csr.moveToFirst();
        while (!csr.isAfterLast()) {
            aList.add(csr.getString(csr.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            Log.i("ChatWindow", "SQL MESSAGE:" + csr.getString(csr.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            csr.moveToNext();
        }

        Log.i("ChatWindow", "Cursor’s  column count =" + csr.getColumnCount());
        for (int i = 0; i < csr.getColumnCount(); i++) {
            Log.i("ChatWindow", csr.getColumnName(i));
        }
        csr.close();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = editT.getText().toString();
                if (msg!=null){
                    aList.add(msg);
                    ContentValues values = new ContentValues();
                    values.put(ChatDatabaseHelper.KEY_MESSAGE, msg);

                    dbase.insert(ChatDatabaseHelper.name, null, values);
                    editT.setText("");

                }
                messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/getView()
            }


        });
    }



    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }


        public int getCount() {
            return aList.size();
        }

        public String getItem(int position) {
            return aList.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();

            View result = null;

            if (position % 2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);

            TextView message = (TextView) result.findViewById(R.id.message_text);
            message.setText(getItem(position));
            return result;

        }
    }


        protected void onDestroy(){
            super.onDestroy();
            dbase.close();
            Log.i("ChatWindow", "In onDestroy()");



    }


}
