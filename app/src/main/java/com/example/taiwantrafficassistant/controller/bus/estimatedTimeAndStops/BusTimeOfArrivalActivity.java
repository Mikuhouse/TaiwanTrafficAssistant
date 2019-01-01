package com.example.taiwantrafficassistant.controller.bus.estimatedTimeAndStops;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;



import com.example.taiwantrafficassistant.R;
import com.example.taiwantrafficassistant.controller.MainActivity;
import com.example.taiwantrafficassistant.controller.bus.routeSearch.BusRouteInformation;
import com.example.taiwantrafficassistant.model.bus.json.StopsAndEstimateTimeJsonAnalysis;
import com.example.taiwantrafficassistant.model.utilities.network.NetworkUtils;
import com.example.taiwantrafficassistant.test.TestGithubRepoApiActivity;
import com.example.taiwantrafficassistant.test.TestPtxApiActivity;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class   BusTimeOfArrivalActivity extends AppCompatActivity {

    ListView mLvEstimattedTimeAndStops;
    String departureStopName;
    String destinationStopName;
    String routeNameToSearch = null;
    String direction = "1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //bind layout
        setContentView(R.layout.activity_bus_time_of_arrival);
        mLvEstimattedTimeAndStops = findViewById(R.id.lv_estimated_time_and_stops);
        mLvEstimattedTimeAndStops.setDivider(null);

        //取回intent的EXTRA_TEXT
        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra("information")) {
            Bundle bundle = getIntent().getExtras();
            BusRouteInformation information = (BusRouteInformation) bundle.getSerializable("information");
            routeNameToSearch = information.getRouteName();
            departureStopName = information.getDepartureStopNam();
            destinationStopName = information.getDestinationStopName();

            setTitle(routeNameToSearch + " " + destinationStopName + "-" + departureStopName);
            updateInformation();



        }else{
            setTitle("Null");
        }

    }
    private class MemberAdapter extends BaseAdapter {
        Context context;
        List<StopsAndEstimatedTimeInformation> informationList;

        MemberAdapter(Context context, List<StopsAndEstimatedTimeInformation> informationList) {
            this.context = context;
            this.informationList = informationList;
        }

        @Override
        public int getCount() {
            return informationList.size();
        }

        @Override
        public View getView(int position, View itemView, ViewGroup parent) {
            if (itemView == null) {
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                itemView = layoutInflater.inflate(R.layout.list_bus_time_of_arrive, parent, false);
            }
            if(position == 0){
                itemView.findViewById(R.id.iv_top_bar).setVisibility(View.INVISIBLE);
            }
            else if(position == informationList.size() - 1){
                itemView.findViewById(R.id.iv_bottom_bar).setVisibility(View.INVISIBLE);
            }else{
                itemView.findViewById(R.id.iv_top_bar).setVisibility(View.VISIBLE);
                itemView.findViewById(R.id.iv_bottom_bar).setVisibility(View.VISIBLE);
            }
            StopsAndEstimatedTimeInformation information = informationList.get(position);

            TextView tvStopName = itemView.findViewById(R.id.tv_stop_name);
            tvStopName.setText(information.getStopName());

            TextView tvArrivalStatus = itemView.findViewById(R.id.tv_arrival_status);
            int time = information.getEstimatedTime();
            String display = "-";
            if(time == -1) display = "未發車";
            else if(time == -2) display = "交管不停靠";
            else if(time == -3) display = "末班已過";
            else if(time == -4) display = "今日停駛";
            else if(time >= 0 && time <= 60) display = "進站中";
            else if(time > 60 && time <= 180) display = "即將進站";
            else if(time > 180) display = Integer.toString(time / 60)+"分";
            tvArrivalStatus.setText(display);

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
    /**
            更新資訊
     */
    public void updateInformation(){
        (new BusTimeOfArrivalAsync()).execute(routeNameToSearch, direction);
    }

    public void reloadListItem(List<StopsAndEstimatedTimeInformation> informationList){
        mLvEstimattedTimeAndStops.setAdapter(new MemberAdapter(this, informationList));
    }

    public class BusTimeOfArrivalAsync extends AsyncTask<String, Void, List<StopsAndEstimatedTimeInformation>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<StopsAndEstimatedTimeInformation> doInBackground(String... params) {
            URL url = StopsAndEstimateTimeJsonAnalysis.buildUrl(params[0], params[1]);
            String jsonResponse = null;
            if(url == null){
                return null;
            }else{
                try{
                    jsonResponse = NetworkUtils.getResponseFromHttpUrl(url);
                }catch (Exception e){
                    System.out.println("Error:NetworkUtils.getResponseFromHttpUrl(url);");
                }
            }
            //return jsonResponse;
            List<StopsAndEstimatedTimeInformation> result = null;
            if(jsonResponse == null) {
                return null;
            }else{
                result = StopsAndEstimateTimeJsonAnalysis.analysisResponse(jsonResponse);
            }
            return result;
        }

        @Override
        protected void onPostExecute(List<StopsAndEstimatedTimeInformation> result) {
            if(result != null){
                reloadListItem(result);

            }else {

            }
        }
    }

    public List<StopsAndEstimatedTimeInformation> getIformationList(){
        List<StopsAndEstimatedTimeInformation> informationList = new ArrayList<>();
        informationList.add(new StopsAndEstimatedTimeInformation("新莊站", 20));
        informationList.add(new StopsAndEstimatedTimeInformation("2", 21));
        informationList.add(new StopsAndEstimatedTimeInformation("3", 22));
        informationList.add(new StopsAndEstimatedTimeInformation("4", 23));
        for(int i = 0;i < 100;i++){
            informationList.add(new StopsAndEstimatedTimeInformation("4", 23));
        }
        return informationList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bus_estimeted_time, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();

        if (itemThatWasClickedId == R.id.action_change) {
            if(direction.equals("1")) {
                direction = "0";
                setTitle(routeNameToSearch + " " + departureStopName + "-" + destinationStopName);
            }
            else {
                direction = "1";
                setTitle(routeNameToSearch + " " + destinationStopName + "-" + departureStopName);
            }
            updateInformation();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
