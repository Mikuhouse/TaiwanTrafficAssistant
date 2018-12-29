package com.example.taiwantrafficassistant.test;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.taiwantrafficassistant.R;
import com.example.taiwantrafficassistant.model.utilities.network.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class TestPtxApiActivity extends AppCompatActivity {
    // TODO 美化介面
    TextView result;
    EditText input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_ptx_api);
        //bind Views
        result = findViewById(R.id.tv_ptx_test_result);
        input = findViewById(R.id.ed_ptx_test_query);


    }
    public void makePtxApiSearchQuery(){
        new QueryTask().execute(input.getText().toString());
    }

    public class QueryTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            //Toast.makeText(TestPtxApiActivity.this, "do", Toast.LENGTH_LONG);
            String strUrl = params[0];
            String result = "fail";

            URL url = NetworkUtils.buildPtxSearchQueryUrl(strUrl);
            try{
                result = NetworkUtils.getResponseFromHttpUrl(url);
            }catch (IOException e){
                e.printStackTrace();
            }

            return result;
        }


        @Override
        protected void onPostExecute(String response) {
            result.setText(response);
        }

        //menu inflater

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.test_github_repo_api_menu, menu);
        return true;
    }

    //when menu option is selected action
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            makePtxApiSearchQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
