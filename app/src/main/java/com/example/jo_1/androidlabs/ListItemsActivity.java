package com.example.jo_1.androidlabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemsActivity extends Activity {
    protected static final String ACTIVITY_NAME = "ListItemsActivity";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageButton imageBt;
    Switch swt;
    boolean isChecked = false;
    CheckBox cBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        setContentView(R.layout.activity_list_items);

        imageBt = (ImageButton) findViewById(R.id.imageB);
        imageBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });


        swt = (Switch) findViewById(R.id.switchB);

        swt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (!isChecked) {
                    CharSequence text = "Switch is off";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(ListItemsActivity.this, text, duration); //this is the ListActivity
                    toast.show();
                }
            }
        });


        final AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
        cBox = (CheckBox) findViewById(R.id.checkB);
        cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    builder.setMessage("Do you want to finish the activity?");
                    builder.setTitle("CheckBox");
                    builder.setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent2 = new Intent();
                            intent2.putExtra("Response", "Here is my response");
                            setResult(Activity.RESULT_OK, intent2);
                            finish();
                        }
                    });

                    builder.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    })
                            .show();

                }
            }
        });
    }

    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
           Bundle extras = data.getExtras();
           Bitmap imageBitmap = (Bitmap) extras.get("data");
           imageBt.setImageBitmap(imageBitmap);

        }
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
