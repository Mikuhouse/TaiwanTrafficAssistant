package com.example.taiwantrafficassistant.controller.bus.estimatedTimeAndStops;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.taiwantrafficassistant.model.bus.api.BusTimeOfArrivalAsync;

import com.example.taiwantrafficassistant.R;

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
            //updateInformation();

            List<Information> informationList = getIformationList();
            mLvEstimattedTimeAndStops.setAdapter(new MemberAdapter(this, informationList));
        }else{
            setTitle("Null");
        }

    }
    private class MemberAdapter extends BaseAdapter {
        Context context;
        List<Information> informationList;

        MemberAdapter(Context context, List<Information> informationList) {
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
            Information information = informationList.get(position);

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
        //(new BusTimeOfArrivalAsync()).execute(routeNameToSearch);

    }
    public void reloadListItem(String[] Information){

    }
    public List<Information> getIformationList(){
        List<Information> informationList = new ArrayList<>();
        informationList.add(new Information("新莊站", 20));
        informationList.add(new Information("2", 21));
        informationList.add(new Information("3", 22));
        informationList.add(new Information("4", 23));
        for(int i = 0;i < 100;i++){
            informationList.add(new Information("4", 23));
        }
        return informationList;
    }


}
