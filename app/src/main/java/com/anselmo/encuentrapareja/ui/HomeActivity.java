package com.anselmo.encuentrapareja.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.anselmo.encuentrapareja.R;
import com.anselmo.encuentrapareja.adapter.StepAdapter;
import com.anselmo.encuentrapareja.analytics.AnalyticsManager;
import com.anselmo.encuentrapareja.model.Step;
import com.pushwoosh.BasePushMessageReceiver;
import com.pushwoosh.BaseRegistrationReceiver;
import com.pushwoosh.PushManager;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity {
    private static final String TAG = HomeActivity.class.getSimpleName();

    private ListView stepList;
    private StepAdapter adapter;
    private ArrayList<Step> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getToolbar().setTitle(getResources().getString(R.string.app_name));
        getToolbar().setSubtitle(getResources().getString(R.string.slogan));

        //Init ListView
        stepList = (ListView) findViewById(R.id.stepList);

        //Init items array
        items = new ArrayList<Step>();

        String arrays[] = getResources().getStringArray(R.array.array_title_steps);

        for( int i = 0; i < arrays.length; i++ ) {
            items.add( new Step(R.mipmap.ic_launcher, "Paso " + ( i + 1), arrays[i]));
        }

        //Init adapter
        adapter = new StepAdapter(this, R.layout.item_row, items);

        //Set adapter
        stepList.setAdapter(adapter);

        stepList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AnalyticsManager.sendEvent("ListView Item", "click", "Paso: " + position);
                Intent i = new Intent(HomeActivity.this, DescriptionDetailActivity.class);
                i.putExtra("position_array", position);
                startActivity(i);
            }
        });

        AnalyticsManager.sendScreenView("HomeActivity");

        //Register receivers for push notifications
        registerReceivers();

        //Create and start push manager
        PushManager pushManager = PushManager.getInstance(this);

        //Start push manager, this will count app open for Pushwoosh stats as well
        try {
            pushManager.onStartup(this);
        }
        catch(Exception e)
        {
            //push notifications are not available or AndroidManifest.xml is not configured properly
        }

        //Register for push!
        pushManager.registerForPushNotifications();

        checkMessage(getIntent());

    }




    //Push
    //Registration receiver
    BroadcastReceiver mBroadcastReceiver = new BaseRegistrationReceiver()
    {
        @Override
        public void onRegisterActionReceive(Context context, Intent intent)
        {
            checkMessage(intent);
        }
    };

    //Push message receiver
    private BroadcastReceiver mReceiver = new BasePushMessageReceiver()
    {
        @Override
        protected void onMessageReceive(Intent intent)
        {
            //JSON_DATA_KEY contains JSON payload of push notification.
            showMessage("push message is " + intent.getExtras().getString(JSON_DATA_KEY));
        }
    };

    //Registration of the receivers
    public void registerReceivers()
    {
        IntentFilter intentFilter = new IntentFilter(getPackageName() + ".action.PUSH_MESSAGE_RECEIVE");

        registerReceiver(mReceiver, intentFilter, getPackageName() +".permission.C2D_MESSAGE", null);

        registerReceiver(mBroadcastReceiver, new IntentFilter(getPackageName() + "." + PushManager.REGISTER_BROAD_CAST_ACTION));
    }

    public void unregisterReceivers()
    {
        //Unregister receivers on pause
        try
        {
            unregisterReceiver(mReceiver);
        }
        catch (Exception e)
        {
            // pass.
        }

        try
        {
            unregisterReceiver(mBroadcastReceiver);
        }
        catch (Exception e)
        {
            //pass through
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();

        //Re-register receivers on resume
        registerReceivers();
    }

    @Override
    public void onPause()
    {
        super.onPause();

        //Unregister receivers on pause
        unregisterReceivers();
    }

    private void checkMessage(Intent intent)
    {
        if (null != intent)
        {
            if (intent.hasExtra(PushManager.PUSH_RECEIVE_EVENT))
            {
                showMessage("push message is " + intent.getExtras().getString(PushManager.PUSH_RECEIVE_EVENT));
            }
            else if (intent.hasExtra(PushManager.REGISTER_EVENT))
            {
                showMessage("register");
            }
            else if (intent.hasExtra(PushManager.UNREGISTER_EVENT))
            {
                showMessage("unregister");
            }
            else if (intent.hasExtra(PushManager.REGISTER_ERROR_EVENT))
            {
                showMessage("register error");
            }
            else if (intent.hasExtra(PushManager.UNREGISTER_ERROR_EVENT))
            {
                showMessage("unregister error");
            }

            resetIntentValues();
        }
    }

    /**
     * Will check main Activity intent and if it contains any PushWoosh data, will clear it
     */
    private void resetIntentValues()
    {
        Intent mainAppIntent = getIntent();

        if (mainAppIntent.hasExtra(PushManager.PUSH_RECEIVE_EVENT))
        {
            mainAppIntent.removeExtra(PushManager.PUSH_RECEIVE_EVENT);
        }
        else if (mainAppIntent.hasExtra(PushManager.REGISTER_EVENT))
        {
            mainAppIntent.removeExtra(PushManager.REGISTER_EVENT);
        }
        else if (mainAppIntent.hasExtra(PushManager.UNREGISTER_EVENT))
        {
            mainAppIntent.removeExtra(PushManager.UNREGISTER_EVENT);
        }
        else if (mainAppIntent.hasExtra(PushManager.REGISTER_ERROR_EVENT))
        {
            mainAppIntent.removeExtra(PushManager.REGISTER_ERROR_EVENT);
        }
        else if (mainAppIntent.hasExtra(PushManager.UNREGISTER_ERROR_EVENT))
        {
            mainAppIntent.removeExtra(PushManager.UNREGISTER_ERROR_EVENT);
        }

        setIntent(mainAppIntent);
    }

    // A integer, that identifies each notification uniquely
    public static final int NOTIFICATION_ID = 1;

    private void showMessage(String message)
    {

    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        setIntent(intent);

        checkMessage(intent);
    }
}
