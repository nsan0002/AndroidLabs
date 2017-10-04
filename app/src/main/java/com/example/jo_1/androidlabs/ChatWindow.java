package com.example.jo_1.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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

    private ListView listV;
    private EditText editT;
    private Button btn;
    ArrayList<String> aList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        listV = (ListView) findViewById(R.id.commentsList);
        editT = (EditText) findViewById(R.id.comment);
        btn = (Button) findViewById(R.id.SendB);

       final ChatAdapter messageAdapter = new ChatAdapter(this);
        listV.setAdapter (messageAdapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String msg = editT.getText().toString();
            if(!msg.isEmpty()){
                aList.add(msg);
                editT.setText("");

}
                messageAdapter.notifyDataSetChanged();

                }
        });

    }

    private class ChatAdapter extends ArrayAdapter<String>{

            public ChatAdapter(Context ctx) {
                super(ctx, 0);
            }



        public int getCount(){
        return aList.size();
    }

        public String getItem(int position){
            return aList.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();

            View result = null ;

            if(position%2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);

            TextView message = (TextView)result.findViewById(R.id.message_text);
            message.setText(getItem(position));
            return result;

        }



    }


}
