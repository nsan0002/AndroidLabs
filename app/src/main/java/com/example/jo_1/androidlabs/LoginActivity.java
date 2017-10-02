package com.example.jo_1.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {
    protected static final String ACTIVITY_NAME = "LoginActivity";
    Button b1;
    protected String defaultEmail = "email@domain.com";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final SharedPreferences email = getSharedPreferences("AnyFileName", MODE_PRIVATE);
        final EditText editT = (EditText) findViewById(R.id.editText);

        String address = email.getString("DefaultEmail",defaultEmail);
        editT.setText(address);

        b1 = (Button) findViewById(R.id.button1);
        email.getString("DefaultEmail",defaultEmail);
        b1.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v){
                String inputEmail = editT.getEditableText().toString();
                SharedPreferences.Editor writer = email.edit();
                writer.putString("DefaultEmail",inputEmail);
                writer.commit();

                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intent);

            }
        });

            email.getString("DefaultEmail", "nsan0002@algonquinlive.com");
        }

    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }


    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    protected void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }
}
