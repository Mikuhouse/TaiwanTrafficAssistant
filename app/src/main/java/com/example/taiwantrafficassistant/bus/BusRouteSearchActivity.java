package com.example.taiwantrafficassistant.bus;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taiwantrafficassistant.R;
import com.example.taiwantrafficassistant.test.TestPtxApiActivity;
import com.example.taiwantrafficassistant.utilities.BusRouteJsonUtils;
import com.example.taiwantrafficassistant.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class BusRouteSearchActivity extends AppCompatActivity
        implements BusRouteAdapter.ListItemClickListener
        {
    private Toast mToast;
    private static final int NUM_LIST_ITEMS = 100;
    private TextView mResult;
    private EditText mInput;
    private String[] mPositionMap;
    /*
     * References to RecyclerView and Adapter to reset the list to its
     * "pretty" state when the reset menu item is clicked.
     */
    private BusRouteAdapter mAdapter;
    private RecyclerView mNumbersList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_route_search);
        //bind view
        mNumbersList = findViewById(R.id.rv_bus_route);
        mResult = findViewById(R.id.tv_ptx_json_handled);
        mInput = findViewById(R.id.et_bus_route_search_box);

        //recyclerView setup
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mNumbersList.setLayoutManager(layoutManager);
        mNumbersList.setHasFixedSize(true);
        mAdapter = new BusRouteAdapter(NUM_LIST_ITEMS, this);
        mNumbersList.setAdapter(mAdapter);


        //this.registerForContextMenu(mNumbersList);
    }

    public  void makeRouteSearchQuery(){
        new QueryTask().execute(mInput.getText().toString());
    }

    public class QueryTask extends AsyncTask<String, Void, String[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String[] doInBackground(String... params) {
            String strUrl = params[0];
            String jsonResponse = "fail";
            String[] parsedRouteData = null;
            URL url = NetworkUtils.buildPtxSearchQueryUrl(strUrl);
            try{
                jsonResponse = NetworkUtils.getResponseFromHttpUrl(url);
            }catch (IOException e){
                e.printStackTrace();
            }
            try{
                parsedRouteData = BusRouteJsonUtils.getSimpleRouteNumberFromJson(jsonResponse);
            }catch (JSONException e){
                e.printStackTrace();
            }


            return parsedRouteData;
        }

        @Override
        protected void onPostExecute(String[] response) {
            if(response != null){
                //mResult.setText(response[0]);
                mPositionMap = response;
                mAdapter.setRouteData(response);
            }else {

            }

        }
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        if (mToast != null) {
            mToast.cancel();
        }

        String toastMessage = "Item #" + clickedItemIndex +" " +mPositionMap[clickedItemIndex] +" clicked.";

        mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);

        mToast.show();

        Intent numbersIntent = new Intent( BusRouteSearchActivity.this, BusTimeOfArrivalActivity.class);
        numbersIntent.putExtra(Intent.EXTRA_TEXT, mPositionMap[clickedItemIndex]);
        startActivity(numbersIntent);
    }

    //menu inflater
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
            makeRouteSearchQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //设置Menu显示内容
        menu.setHeaderTitle("文件操作");
        //menu.setHeaderIcon(R.drawable.file);
        menu.add(1, 100, 1, "复制");
        menu.add(1, 101, 1, "剪切");
        menu.add(1, 102, 1, "粘贴");
    }
}
