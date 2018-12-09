package com.example.taiwantrafficassistant.bus;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
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

public class BusRouteSearchActivity extends AppCompatActivity implements BusRouteAdapter.ListItemClickListener{
    private Toast mToast;
    private static final int NUM_LIST_ITEMS = 100;
    private TextView mResult;
    private EditText mInput;
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
        /*
         * Using findViewById, we get a reference to our RecyclerView from xml. This allows us to
         * do things like set the adapter of the RecyclerView and toggle the visibility.
         */
        mNumbersList = findViewById(R.id.rv_bus_route);
        mResult = findViewById(R.id.tv_ptx_json_handled);
        mInput = findViewById(R.id.et_bus_route_search_box);



        /*
         * A LinearLayoutManager is responsible for measuring and positioning item views within a
         * RecyclerView into a linear list. This means that it can produce either a horizontal or
         * vertical list depending on which parameter you pass in to the LinearLayoutManager
         * constructor. By default, if you don't specify an orientation, you get a vertical list.
         * In our case, we want a vertical list, so we don't need to pass in an orientation flag to
         * the LinearLayoutManager constructor.
         *
         * There are other LayoutManagers available to display your data in uniform grids,
         * staggered grids, and more! See the developer documentation for more details.
         */
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mNumbersList.setLayoutManager(layoutManager);

        /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        mNumbersList.setHasFixedSize(true);

        // COMPLETED (13) Pass in this as the ListItemClickListener to the GreenAdapter constructor
        /*
         * The GreenAdapter is responsible for displaying each item in the list.
         */
        mAdapter = new BusRouteAdapter(NUM_LIST_ITEMS, this);
        mNumbersList.setAdapter(mAdapter);
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
                mResult.setText(response[0]);
                mAdapter.setRouteData(response);
            }else {

            }

        }

        //menu inflater

    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        // COMPLETED (11) In the beginning of the method, cancel the Toast if it isn't null
        /*
         * Even if a Toast isn't showing, it's okay to cancel it. Doing so
         * ensures that our new Toast will show immediately, rather than
         * being delayed while other pending Toasts are shown.
         *
         * Comment out these three lines, run the app, and click on a bunch of
         * different items if you're not sure what I'm talking about.
         */
        if (mToast != null) {
            mToast.cancel();
        }

        // COMPLETED (12) Show a Toast when an item is clicked, displaying that item number that was clicked
        /*
         * Create a Toast and store it in our Toast field.
         * The Toast that shows up will have a message similar to the following:
         *
         *                     Item #42 clicked.
         */
        String toastMessage = "Item #" + clickedItemIndex + " clicked.";
        mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);

        mToast.show();
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
}
