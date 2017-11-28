package com.example.jo_1.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import static android.widget.Toast.LENGTH_SHORT;
import static com.example.jo_1.androidlabs.ListItemsActivity.REQUEST_IMAGE_CAPTURE;

public class StartActivity extends Activity {
    protected static final String ACTIVITY_NAME = "StartActivity";
    protected final int requestCode = 10;

   private String TAG = "activity_start.xml";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        setContentView(R.layout.activity_start);

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StartActivity.this, ListItemsActivity.class);
                startActivityForResult(i, 10);
            }

        });
        
    Button btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(StartActivity.this, ChatWindow.class);
            startActivity(intent);
            Log.i(ACTIVITY_NAME, "User clicked Start Chat");
        }

    });

       Button weatherB = (Button)findViewById(R.id.weatherB);
        weatherB.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick (View v){
        Intent intent = new Intent(StartActivity.this, WeatherForecast.class);
        startActivity(intent);
    }
    });
    }

            protected void onActivityResult(int requestCode, int resultCode, Intent data) {

                if ((requestCode == 10) && (resultCode == Activity.RESULT_OK)) {
                    String message = data.getStringExtra("Response");
                    String response = "ListItemsActivity passed: My information to share " + message;
                    Toast toast = Toast.makeText(StartActivity.this, response, Toast.LENGTH_LONG); //this is the ListActivity
                    Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
                    toast.show();


                }
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

            protected void onDestroy() {
                super.onDestroy();
                Log.i(ACTIVITY_NAME, "In onDestroy()");

            }
}
