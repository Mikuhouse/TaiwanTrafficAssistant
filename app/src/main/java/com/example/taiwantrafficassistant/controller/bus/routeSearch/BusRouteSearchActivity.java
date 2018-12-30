package com.example.taiwantrafficassistant.controller.bus.routeSearch;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taiwantrafficassistant.R;
import com.example.taiwantrafficassistant.controller.bus.estimatedTimeAndStops.BusTimeOfArrivalActivity;
import com.example.taiwantrafficassistant.model.bus.json.BusRouteJsonUtils;
import com.example.taiwantrafficassistant.model.utilities.network.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BusRouteSearchActivity extends AppCompatActivity {
    //private TextView mResult;
    private EditText mInput;
    private ListView mResult;
    private List<BusRouteInformation> mInformationList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_route_search);
        //bind view
        //mResult = findViewById(R.id.tv_route_result_test);
        mInput = findViewById(R.id.et_bus_route_search_box);
        mResult = findViewById(R.id.lv_bus_route_search_result);
    }

    public void makeRouteSearchQuery(){
        new QueryTask().execute(mInput.getText().toString());
        //mInformationList = testList();
        //reloadRouteInformation();
    }
    public void reloadRouteInformation(){
        mResult.setAdapter(new InformationAdapter(this, mInformationList));
        mResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                BusRouteInformation information = (BusRouteInformation) parent.getItemAtPosition(position);

                Intent numbersIntent = new Intent( BusRouteSearchActivity.this, BusTimeOfArrivalActivity.class);
                //numbersIntent.putExtra(Intent.EXTRA_TEXT, information.getRouteName());
                Bundle bundle = new Bundle();
                bundle.putSerializable("information", information);
                numbersIntent.putExtras(bundle);
                startActivity(numbersIntent);
            }
        });
    }
    public class InformationAdapter extends BaseAdapter {
        Context context;
        List<BusRouteInformation> informationList;

        InformationAdapter(Context context, List<BusRouteInformation> List) {
            this.context = context;
            this.informationList = List;
        }

        @Override
        public int getCount() {
            return informationList.size();
        }

        @Override
        public View getView(int position, View itemView, ViewGroup parent) {
            if (itemView == null) {
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                itemView = layoutInflater.inflate(R.layout.bus_route_list_item, parent, false);
            }
            TextView routeName = itemView.findViewById(R.id.tv_bus_route_name);
            TextView endPoint = itemView.findViewById(R.id.tv_end_point);
            String routeNameStr = informationList.get(position).getRouteName();
            String depStr = informationList.get(position).getDepartureStopNam();
            String desStr = informationList.get(position).getDestinationStopName();
            routeName.setText(routeNameStr);
            endPoint.setText(depStr + "-" + desStr);
            return itemView;
        }

        @Override
        public Object getItem(int position) {
            return informationList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
    }
    public class QueryTask extends AsyncTask<String, Void, List<BusRouteInformation>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<BusRouteInformation> doInBackground(String... params) {
            URL taipeiUrl = NetworkUtils.buildPtxSearchQueryUrl(params[0]);
            URL newTaipeiUrl = NetworkUtils.buildPtxSearchQueryTwoUrl(params[0]);
            String jsonResponseOne = null;
            String jsonResponseTwo = null;
            try{
                jsonResponseOne = NetworkUtils.getResponseFromHttpUrl(taipeiUrl);
                jsonResponseTwo = NetworkUtils.getResponseFromHttpUrl(newTaipeiUrl);
            }catch (Exception e){
                System.out.println("Error:NetworkUtils.getResponseFromHttpUrl(taipeiUrl);");
            }
            List<BusRouteInformation> resultListOne = null;
            List<BusRouteInformation> resultListTwo = null;
            List<BusRouteInformation> result;
            if(jsonResponseOne == null || jsonResponseTwo == null){
                return null;
            }else{
                result = BusRouteJsonUtils.getSimpleRouteNumberFromJson(jsonResponseOne, jsonResponseTwo);
            }
            return result;
        }

        @Override
        protected void onPostExecute(List<BusRouteInformation> result) {
            if(result != null) {
                mInformationList = result;
                reloadRouteInformation();
            }
        }
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

    public List<BusRouteInformation> testList(){
        List<BusRouteInformation> fakeData = new ArrayList<>();
        fakeData.add(new BusRouteInformation("299", "1", "2"));
        fakeData.add(new BusRouteInformation("299", "1", "2"));
        fakeData.add(new BusRouteInformation("299", "1", "2"));
        fakeData.add(new BusRouteInformation("299", "1", "2"));
        for(int i = 0;i < 100;i++){
            fakeData.add(new BusRouteInformation("299區", "1", "2"));
        }
        return fakeData;
    }
}
