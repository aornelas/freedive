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


public class InhaleActivity extends Activity implements WearableListView.ClickListener {

    private static final String DEBUG_TAG = "DEBUG.MainActivity";

    public static final String EXTRA_INHALE_SECS = "com.andres.freedive.INHALE_SECS";
    public static final String EXTRA_HOLD_SECS = "com.andres.freedive.HOLD_SECS";
    public static final String EXTRA_EXHALE_SECS = "com.andres.freedive.EXHALE_SECS";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences(getString(R.string.preference_file_key),
            Context.MODE_PRIVATE);
        int savedInhaleSecs = prefs.getInt(getString(R.string.saved_inhale_secs), -1);
        int savedHoldSecs = prefs.getInt(getString(R.string.saved_hold_secs), -1);
        int savedExhaleSecs = prefs.getInt(getString(R.string.saved_exhale_secs), -1);
        if (savedInhaleSecs != -1 && savedHoldSecs != -1 && savedExhaleSecs != -1) {
            Intent intent = new Intent(this, DiveActivity.class);
            intent.putExtra(EXTRA_INHALE_SECS, savedInhaleSecs);
            intent.putExtra(EXTRA_HOLD_SECS, savedHoldSecs);
            intent.putExtra(EXTRA_EXHALE_SECS, savedExhaleSecs);
            startActivity(intent);
        }

        setContentView(R.layout.activity_picker);

        ((TextView) findViewById(R.id.pickerTitle)).setText("inhale");
        WearableListView listView = (WearableListView) findViewById(R.id.list);
        listView.setAdapter(new Adapter(this));
        listView.setClickListener(this);
    }

    @Override
    public void onClick(WearableListView.ViewHolder holder) {
        Intent intent = new Intent(this, HoldActivity.class);
        int inhaleSecs = holder.getPosition() + 1;
        intent.putExtra(EXTRA_INHALE_SECS, inhaleSecs);
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
            view.setText((position + 1) + " secs");
            holder.itemView.setTag(position);
        }

        @Override
        public int getItemCount() {
            return 30;
        }
    }
}