package com.example.jo_1.androidlabs;

import android.app.Activity;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import static android.R.attr.fragment;
import static android.R.attr.value;

public class MessageDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        String message = getIntent().getExtras().getString("chatMsg");
        int id = getIntent().getExtras().getInt("Id");

        MessageFragment mf = new MessageFragment();

        Bundle bundle = new Bundle();
        bundle.putString("chatMsg", message);
        bundle.putInt("Id",id);

        mf.setArguments(bundle);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, mf);
        ft.commit();


    }
}
