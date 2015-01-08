package com.example.jakie_chan.myapplication;

//request to server and getting result from him
//working!

import android.os.AsyncTask;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements OnClickListener {

    private EditText value;
    private Button btn;
    private ProgressBar pb;

    ATask at;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        value=(EditText)findViewById(R.id.inValue);
        pb=(ProgressBar)findViewById(R.id.progressBar1);
        pb.setVisibility(View.GONE);
        btn=(Button)findViewById(R.id.button);
        btn.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        at = new ATask();
        pb.setVisibility(View.VISIBLE);
        at.execute(value.getText().toString());
    }






    class ATask extends AsyncTask <String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://192.168.2.5:8080/show.php");

            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("myHttpData", params[0]));
                nameValuePairs.add(new BasicNameValuePair("host", "localhost"));
                nameValuePairs.add(new BasicNameValuePair("dbname", "postgres"));
                nameValuePairs.add(new BasicNameValuePair("user", "postgres"));
                nameValuePairs.add(new BasicNameValuePair("port", "5432"));
                nameValuePairs.add(new BasicNameValuePair("password", "123"));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpclient.execute(httppost);
                return IOUtils.toString(response.getEntity().getContent());

            } catch (ClientProtocolException e) {
                return e.toString();
            } catch (UnsupportedEncodingException e) {
                return e.toString();
            } catch (IOException e) {
                return e.toString();
            }

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pb.setVisibility(View.GONE);
            value.setText(result);
            Toast.makeText(getApplicationContext(), "command sent", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress){
            pb.setProgress(progress[0]);
        }

    }
}
