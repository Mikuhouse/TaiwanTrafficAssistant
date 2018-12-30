package com.example.taiwantrafficassistant.controller.bus.estimatedTimeAndStops;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;



import com.example.taiwantrafficassistant.R;
import com.example.taiwantrafficassistant.model.bus.json.StopsAndEstimateTimeJsonAnalysis;
import com.example.taiwantrafficassistant.model.utilities.network.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class   BusTimeOfArrivalActivity extends AppCompatActivity {

    ListView mLvEstimattedTimeAndStops;
    String routeNameToSearch = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //bind layout
        setContentView(R.layout.activity_bus_time_of_arrival);
        mLvEstimattedTimeAndStops = findViewById(R.id.lv_estimated_time_and_stops);
        mLvEstimattedTimeAndStops.setDivider(null);

        //取回intent的EXTRA_TEXT
        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
            String textEntered = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
            routeNameToSearch = textEntered;
            setTitle(routeNameToSearch);
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
            tvArrivalStatus.setText(information.getEstimatedTime() + "");

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
        (new BusTimeOfArrivalAsync()).execute(routeNameToSearch);
    }

    public void reloadListItem(List<StopsAndEstimatedTimeInformation> informationList){
        //TextView tvTest = findViewById(R.id.tv_bus_test);
        //tvTest.setText(information);
        mLvEstimattedTimeAndStops.setAdapter(new MemberAdapter(this, informationList));
    }

    public class BusTimeOfArrivalAsync extends AsyncTask<String, Void, List<StopsAndEstimatedTimeInformation>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<StopsAndEstimatedTimeInformation> doInBackground(String... params) {
            URL url = StopsAndEstimateTimeJsonAnalysis.buildUrl(params[0]);
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


}
