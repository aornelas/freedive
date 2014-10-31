package com.aornelas.wearable.freedive;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;


public class MainActivity extends Activity implements WearableListView.ClickListener {

    private static final String DEBUG_TAG = "DEBUG.MainActivity";

    public static final String EXTRA_WEEK_NUMBER = "com.andres.pushups.WEEK_NUMBER";
    public static final String EXTRA_DAY_NUMBER = "com.andres.pushups.DAY_NUMBER";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences(getString(R.string.preference_file_key),
            Context.MODE_PRIVATE);
        int savedWeek = prefs.getInt(getString(R.string.saved_week), -1);
        int savedDay = prefs.getInt(getString(R.string.saved_day), -1);
        if (savedWeek != -1 && savedDay != -1) {
            Intent intent = new Intent(this, SummaryActivity.class);
            intent.putExtra(EXTRA_WEEK_NUMBER, savedWeek);
            intent.putExtra(EXTRA_DAY_NUMBER, savedDay);
            startActivity(intent);
        }

        setContentView(R.layout.activity_main);

        WearableListView listView = (WearableListView) findViewById(R.id.list);
        listView.setAdapter(new Adapter(this));
        listView.setClickListener(this);
    }

    @Override
    public void onClick(WearableListView.ViewHolder holder) {
        Intent intent = new Intent(this, DayActivity.class);
        int weekNumber = holder.getPosition() + 1;
        intent.putExtra(EXTRA_WEEK_NUMBER, weekNumber);
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
            view.setText("week " + (position + 1));
            holder.itemView.setTag(position);
        }

        @Override
        public int getItemCount() {
            return 6;
        }
    }
}