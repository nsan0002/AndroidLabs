package com.example.jo_1.androidlabs;

import android.app.Activity;
import android.os.Bundle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherForecast extends Activity {

    protected static final String TAG = "WeatherForecast";

    private ImageView cWeather;
    private TextView cTemp;
    private TextView minTemp;
    private TextView maxTemp;
    private ProgressBar progressB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        cWeather= (ImageView)findViewById(R.id.cWeather);
        cTemp = (TextView)findViewById(R.id.cTemp);
        minTemp = (TextView)findViewById(R.id.minTemp);
        maxTemp = (TextView)findViewById(R.id.maxTemp);

        progressB = (ProgressBar)findViewById(R.id.progressB);
        progressB.setVisibility(View.VISIBLE);


        ForecastQuery forecast = new ForecastQuery();
        String  url = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";
        forecast.execute(url);
        //Log.i(TAG,"In onCreate()");

    }
    public class ForecastQuery extends AsyncTask<String, Integer, String> {

        private String minTemperature;
        private String maxTemperature;
        private String currentTemperature;
        private String image;
        private Bitmap picture;

        @Override
        protected void onPreExecute(){
            Log.i(TAG,"Starting AsyncTask");
            progressB.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            Log.i(TAG, "In doInBackgroud");
            // HttpURLConnection connection = null;
            try{
                URL dataUrl = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection)dataUrl.openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("GET");
                connection.connect();
                int status = connection.getResponseCode();
                Log.d("connection", "status " + status);
                InputStream is = connection.getInputStream();
                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(is, null);

                while(parser.next() != XmlPullParser.END_DOCUMENT){
                    if(parser.getEventType() != XmlPullParser.START_TAG){
                        continue;

                    }
                    if(parser.getName().equals("temperature")){
                        currentTemperature = parser.getAttributeValue(null, "value");
                        publishProgress(25);
                        minTemperature = parser.getAttributeValue(null,"min");
                        publishProgress(50);
                        maxTemperature= parser.getAttributeValue(null, "max");
                        publishProgress(75);

                    }
                    if(parser.getName().equals("weather")){
                        image = parser.getAttributeValue(null, "icon");
                        String iconFile = image + ".png";
                        if(isFileExists(iconFile)){
                            FileInputStream inputStream = null;
                            try{
                                inputStream = new FileInputStream(getBaseContext().getFileStreamPath(iconFile));
                            }catch(Exception e ){
                                e.printStackTrace();
                            }
                            picture = BitmapFactory.decodeStream(inputStream);
                            Log.i(TAG, "image file exists already");
                        }
                        else{
                            URL ImageURL = new URL("http://openweathermap.org/img/w/" + image + ".png");
                            picture  = getImage(ImageURL);
                            FileOutputStream outputStream = openFileOutput( image + ".png", Context.MODE_PRIVATE);
                           picture.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                            outputStream.flush();
                            outputStream.close();
                            Log.i(TAG, "downloaded image");
                        }
                        Log.i(TAG, "filename " + iconFile);
                        publishProgress(100);
                    }
                }

            }catch(MalformedURLException e ){

            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }catch (NullPointerException e) {
                e.printStackTrace();
            }
            return null;
        }

        public void onProgressUpdate(Integer... value){
            Log.i(TAG, "in onProgressUpdate");
            progressB.setVisibility(View.VISIBLE);
            progressB.setProgress(value[0]);

        }

        public void onPostExecute(String result){
            String degree = Character.toString((char) 0x00B0);
            cTemp.setText("Current Temperature" + cTemp.getText()+ currentTemperature + degree + "C");
            minTemp.setText("Min Temperature:" + minTemp.getText() + minTemperature + degree + "C");
            maxTemp.setText("Max Temperature:" + maxTemp.getText()+ maxTemperature + degree + "C");
            cWeather.setImageBitmap(picture);
            progressB.setVisibility(View.INVISIBLE);
        }



    }

    public boolean isFileExists(String fileName){
        File file = getBaseContext().getFileStreamPath(fileName);
        Log.i(TAG, file.toString());

        return file.exists();

    }

    public Bitmap getImage(URL url){
        Log.i(TAG, "In getImage");
        HttpURLConnection connection = null;
        try{
            connection = (HttpURLConnection)url.openConnection();
            int responseCode = connection.getResponseCode();
            if(responseCode == 200){
                Log.i(TAG, "downloading image");
                Bitmap bm = BitmapFactory.decodeStream(connection.getInputStream());
                return bm;

            }
            else{
                return null;
            }

        }catch(MalformedURLException e){
            e.printStackTrace();
            return null;
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null){
                connection.disconnect();
            }
        }
    }
}
