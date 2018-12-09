package com.example.taiwantrafficassistant.bus;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.taiwantrafficassistant.R;

public class BusRouteAdapter extends RecyclerView.Adapter<BusRouteAdapter.NumberViewHolder> {

    private static final String TAG = BusRouteAdapter.class.getSimpleName();

    final private ListItemClickListener mOnClickListener;

    private static int viewHolderCount;

    private int mNumberItems;

    private String[] mRouteData;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }
    public BusRouteAdapter(int numberOfItems, ListItemClickListener listener) {
        mNumberItems = numberOfItems;
        mOnClickListener = listener;
        viewHolderCount = 0;
    }
    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.bus_route_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        NumberViewHolder viewHolder = new NumberViewHolder(view);

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
    public void onBindViewHolder(NumberViewHolder busRouteAdapterViewHolder, int position) {
        String weatherForThisDay = mRouteData[position];
        busRouteAdapterViewHolder.viewHolderIndex.setText(weatherForThisDay);
        busRouteAdapterViewHolder.listItemNumberView.setText(position+"");
    }


    // COMPLETED (5) Implement OnClickListener in the NumberViewHolder class
    /**
     * Cache of the children views for a list item.
     */

    class NumberViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        TextView listItemNumberView;
        TextView viewHolderIndex;

        public NumberViewHolder(View itemView) {
            super(itemView);

            listItemNumberView = (TextView) itemView.findViewById(R.id.tv_item_number);
            viewHolderIndex = (TextView) itemView.findViewById(R.id.tv_view_holder_instance);
            // COMPLETED (7) Call setOnClickListener on the View passed into the constructor (use 'this' as the OnClickListener)
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }


    @Override
    public int getItemCount() {
        if (null == mRouteData) return 0;
        return mRouteData.length;
    }

    public void setRouteData(String[] weatherData) {
        mRouteData = weatherData;
        notifyDataSetChanged();
    }
}
