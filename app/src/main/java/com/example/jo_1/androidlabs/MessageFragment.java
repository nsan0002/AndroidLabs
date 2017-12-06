package com.example.jo_1.androidlabs;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link MessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragment extends Fragment {

   private TextView tMessage;
   private TextView messageId;
   private Button deleteB;
   private String myMsg;
   private int myId;
   private long dbID;
   private ChatWindow chatWindow;


    public MessageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.*/

     public void setChatWindow(ChatWindow chatWindow){
     this.chatWindow = chatWindow;
     }


     public static MessageFragment newInstance(){
     MessageFragment newFragment = new MessageFragment();
     return newFragment;
     }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            myMsg = getArguments().getString("chatMsg");
            myId = getArguments().getInt("Id");
            dbID = getArguments().getLong("dbId");
            Log.i("MessageFragment", myMsg);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        tMessage = (TextView) view.findViewById(R.id.tMessage);
        tMessage.setText(myMsg);

        messageId = (TextView) view.findViewById(R.id.messageId);
        messageId.setText(Integer.toString(myId));

        deleteB = (Button) view.findViewById(R.id.deleteB);
        deleteB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chatWindow != null){
                    //tablet
                    chatWindow.deleteMessage(myId);
                    // once deleted the fragment is disappeared
                    getActivity().getFragmentManager().popBackStack();

                }
                else{
                    Log.i("tag","hello");
                    Intent intent = new Intent();
                    intent.putExtra("deleteMsgId", myId);
                    //intent.putExtra("deleteDBMsgId", dbID);
                    getActivity().setResult(10, intent);
                    getActivity().finish();
                }
            }
        });
        return view;
    }
}
