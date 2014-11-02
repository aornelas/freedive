package com.aornelas.wearable.freedive;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.wearable.view.DelayedConfirmationView;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;


public class DiveActivity extends Activity {

    private int mInhaleSecs;
    private int mHoldSecs;
    private int mExhaleSecs;
    private DelayedConfirmationView delayedConfirmationView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Prevent the screen timeout from stopping this application
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Intent intent = getIntent();
        mInhaleSecs = intent.getIntExtra(InhaleActivity.EXTRA_INHALE_SECS, 1);
        mHoldSecs = intent.getIntExtra(InhaleActivity.EXTRA_HOLD_SECS, 1);
        mExhaleSecs = intent.getIntExtra(InhaleActivity.EXTRA_EXHALE_SECS, 1);

        SharedPreferences prefs = getSharedPreferences(getString(R.string.preference_file_key),
            Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(getString(R.string.saved_inhale_secs), mInhaleSecs);
        editor.putInt(getString(R.string.saved_hold_secs), mHoldSecs);
        editor.putInt(getString(R.string.saved_exhale_secs), mExhaleSecs);
        editor.commit();

        setContentView(R.layout.dive_layout);

        startTimer("inhale", mInhaleSecs, R.color.blue, new InhaleListener());
    }

    private void startTimer(String action, int secs, int color, DelayedConfirmationView.DelayedConfirmationListener handler) {
        ((TextView) findViewById(R.id.summaryTitle)).setText(action);
        delayedConfirmationView = (DelayedConfirmationView) findViewById(R.id.delayed_confirmation);
        delayedConfirmationView.setCircleColor(getResources().getColor(color));
        delayedConfirmationView.setTotalTimeMs(secs * 1000);
        delayedConfirmationView.start();
        delayedConfirmationView.setListener(handler);
    }

    private class InhaleListener implements DelayedConfirmationView.DelayedConfirmationListener {
        @Override
        public void onTimerFinished(View view) {
            startTimer("hold", mHoldSecs, R.color.red, new HoldListener());
        }

        @Override
        public void onTimerSelected(View view) { }
    }
    private class HoldListener implements DelayedConfirmationView.DelayedConfirmationListener {
        @Override
        public void onTimerFinished(View view) {
            startTimer("exhale", mExhaleSecs, R.color.green, new ExhaleListener());
        }

        @Override
        public void onTimerSelected(View view) { }
    }
    private class ExhaleListener implements DelayedConfirmationView.DelayedConfirmationListener {
        @Override
        public void onTimerFinished(View view) {
            startTimer("inhale", mInhaleSecs, R.color.blue, new InhaleListener());
        }

        @Override
        public void onTimerSelected(View view) { }
    }
}
