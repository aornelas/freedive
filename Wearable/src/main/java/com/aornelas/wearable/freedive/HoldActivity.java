package com.aornelas.wearable.freedive;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;


public class HoldActivity extends Activity implements WearableListView.ClickListener {

    private int mInhaleSecs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mInhaleSecs = intent.getIntExtra(InhaleActivity.EXTRA_INHALE_SECS, -1);

        setContentView(R.layout.activity_picker);

        ((TextView) findViewById(R.id.pickerTitle)).setText("hold");
        WearableListView listView = (WearableListView) findViewById(R.id.list);
        listView.setAdapter(new Adapter(this));
        listView.setClickListener(this);
    }

    @Override
    public void onClick(WearableListView.ViewHolder holder) {
        Intent intent = new Intent(this, ExhaleActivity.class);
        int holdSecs = holder.getPosition() * 2 + 2;
        intent.putExtra(InhaleActivity.EXTRA_INHALE_SECS, mInhaleSecs);
        intent.putExtra(InhaleActivity.EXTRA_HOLD_SECS, holdSecs);
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
            view.setText(((position + 1) * 2) + " secs");
            holder.itemView.setTag(position);
        }

        @Override
        public int getItemCount() {
            return 30;
        }
    }
}