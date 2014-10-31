package com.aornelas.wearable.freedive;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;


public class DayActivity extends Activity implements WearableListView.ClickListener {

    private int mWeekNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mWeekNumber = intent.getIntExtra(MainActivity.EXTRA_WEEK_NUMBER, -1);

        setContentView(R.layout.activity_main);

        WearableListView listView = (WearableListView) findViewById(R.id.list);
        listView.setAdapter(new Adapter(this));
        listView.setClickListener(this);
    }

    @Override
    public void onClick(WearableListView.ViewHolder holder) {
        Intent intent = new Intent(this, SummaryActivity.class);
        int dayNumber = holder.getPosition() + 1;
        intent.putExtra(MainActivity.EXTRA_WEEK_NUMBER, mWeekNumber);
        intent.putExtra(MainActivity.EXTRA_DAY_NUMBER, dayNumber);
        startActivity(intent);
    }

    @Override
    public void onTopEmptyRegionClick() { }

    private static final class Adapter extends WearableListView.Adapter {
        private final Context mContext;
        private final LayoutInflater mInflater;

        private Adapter(Context context) {
            mContext = context;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new WearableListView.ViewHolder(
                    mInflater.inflate(R.layout.list_item, null));
        }

        @Override
        public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
            TextView view = (TextView) holder.itemView.findViewById(R.id.name);
            view.setText("day " + (position + 1));
            holder.itemView.setTag(position);
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}