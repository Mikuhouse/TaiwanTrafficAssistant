package com.example.taiwantrafficassistant.controller.bus.estimatedTimeAndStops;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.taiwantrafficassistant.R;
import com.example.taiwantrafficassistant.controller.bus.adapter.BusRouteAdapter;
import com.example.taiwantrafficassistant.controller.utilities.color.ColorUtils;

public class BusTimeOfArrivalAdapter extends RecyclerView.Adapter<BusTimeOfArrivalAdapter.StopViewHolder> {

    private static final String TAG = BusRouteAdapter.class.getSimpleName();

    final private ListItemClickListener mOnClickListener;

    private static int viewHolderCount;

    private int mNumberItems;

    private String[] mRouteData;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public BusTimeOfArrivalAdapter(int numberOfItems, ListItemClickListener listener) {
        mNumberItems = numberOfItems;
        mOnClickListener = listener;
        viewHolderCount = 0;
    }

    @Override
    public int getItemCount() {
        if (null == mRouteData) return 0;
        return mRouteData.length;
    }

    public void setRouteData(String[] routeData) {
        mRouteData = routeData;
        notifyDataSetChanged();
    }

    /**
     * ViewHolder
     */
    class StopViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        TextView listItemNumberView;
        TextView viewHolderIndex;

        public StopViewHolder(View itemView) {
            super(itemView);

            listItemNumberView = itemView.findViewById(R.id.tv_item_number);
            viewHolderIndex = itemView.findViewById(R.id.tv_view_holder_instance);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }

    @Override
    public StopViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.bus_route_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        StopViewHolder viewHolder = new StopViewHolder(view);

        viewHolder.viewHolderIndex.setText("ViewHolder index: " + viewHolderCount);

        int backgroundColorForViewHolder = ColorUtils
                .getViewHolderBackgroundColorFromInstance(context, viewHolderCount);
        viewHolder.itemView.setBackgroundColor(backgroundColorForViewHolder);

        viewHolderCount++;
        Log.d(TAG, "onCreateViewHolder: number of ViewHolders created: "
                + viewHolderCount);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StopViewHolder busRouteAdapterViewHolder, int position) {
        String weatherForThisDay = mRouteData[position];
        busRouteAdapterViewHolder.viewHolderIndex.setText(weatherForThisDay);
        busRouteAdapterViewHolder.listItemNumberView.setText(position+"");
    }
}
