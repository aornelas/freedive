package com.aornelas.wearable.freedive;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;


public class SummaryActivity extends Activity {

    private int mWeekNumber;
    private int mDayNumber;

    private final int LEVEL_COUNT = 3;
    private final int SET_COUNT = 5;

    // Source: http://hundredpushups.com/
    private final int[/* week */][/* day */][/* level */][/* set */] PUSHUPS = {
        { /* week 1 */
            { /* day 1 */
                { 2,3,2,2,3 }, { 6,6,4,4,5 }, { 10,12,7,7,9 }
            },
            { /* day 2 */
                { 3,4,2,3,4 }, { 6,8,6,6,7 }, { 10,12,8,8,12 }
            },
            { /* day 3 */
                { 4,5,4,4,5 }, { 8,10,7,7,10 }, { 11,15,9,9,13 }
            }
        },
        { /* week 2 */
            { /* day 1 */
                { 4,6,4,4,6 }, { 9,11,8,8,11 }, { 14,14,10,10,15 }
            },
            { /* day 2 */
                { 5,6,4,4,7 }, { 10,12,9,9,13 }, { 14,16,12,12,17 }
            },
            { /* day 3 */
                { 5,7,5,5,8 }, { 12,13,10,10,15 }, { 16,17,14,14,20 }
            }
        },
        { /* week 3 */
            { /* day 1 */
                { 10,12,7,7,9 }, { 12,17,13,13,17 }, { 14,18,14,14,20 }
            },
            { /* day 2 */
                { 10,12,8,8,12 }, { 14,19,14,14,19 }, { 20,25,15,15,25 }
            },
            { /* day 3 */
                { 11,13,9,9,13 }, { 16,21,15,15,21 }, { 22,30,20,20,28 }
            }
        },
        { /* week 4 */
            { /* day 1 */
                { 12,14,11,10,16 }, { 18,22,16,16,25 }, { 21,25,21,21,32 }
            },
            { /* day 2 */
                { 14,16,12,12,18 }, { 20,25,20,20,28 }, { 25,29,25,25,36 }
            },
            { /* day 3 */
                { 16,18,13,13,20 }, { 23,28,23,23,33 }, { 29,33,29,29,40 }
            }
        },
        { /* week 5 */
            { /* day 1 */
                { 17,19,15,15,20 }, { 28,35,25,25,35 }, { 36,40,30,24,40 }
            },
            { /* day 2 */
                { 10,13,10,9,25 }, { 18,20,14,16,40 }, { 19,22,18,22,45 }
            },
            { /* day 3 */
                { 13,15,12,10,30 }, { 18,20,17,20,45 }, { 20,24,20,22,50 }
            }
        },
        { /* week 6 */
            { /* day 1 */
                { 25,30,20,15,40 }, { 40,50,25,25,50 }, { 45,55,35,30,55 }
            },
            { /* day 2 */
                { 14,15,14,10,44 }, { 20,23,20,18,53 }, { 22,30,24,18,58 }
            },
            { /* day 3 */
                { 13,17,16,14,50 }, { 22,30,25,18,55 }, { 26,33,26,22,60 }
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Prevent the screen timeout from stopping this application
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Intent intent = getIntent();
        mWeekNumber = intent.getIntExtra(MainActivity.EXTRA_WEEK_NUMBER, 1);
        mDayNumber = intent.getIntExtra(MainActivity.EXTRA_DAY_NUMBER, 1);

        SharedPreferences prefs = getSharedPreferences(getString(R.string.preference_file_key),
            Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(getString(R.string.saved_week), mWeekNumber);
        editor.putInt(getString(R.string.saved_day), mDayNumber);
        editor.commit();

        setContentView(R.layout.summary_layout);

        ((TextView) findViewById(R.id.summaryTitle)).setText("week " + mWeekNumber + " day " + mDayNumber);

        int weekIndex = mWeekNumber - 1;
        int dayIndex = mDayNumber - 1;
        // TODO: handle dupe sets in weeks 5 and 6
        int[] levelIds = { R.id.rookieSets, R.id.veteranSets, R.id.eliteSets };
        for (int levelIndex = 0 ; levelIndex < LEVEL_COUNT; levelIndex++) {
            for (int setIndex = 0; setIndex < SET_COUNT; setIndex++) {
                String setLabel = String.valueOf(PUSHUPS[weekIndex][dayIndex][levelIndex][setIndex]);
                ((TextView) findViewById(levelIds[levelIndex] + setIndex + 1)).setText(setLabel);
            }
        }
    }

}
