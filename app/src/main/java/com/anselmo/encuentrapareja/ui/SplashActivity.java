package com.anselmo.encuentrapareja.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.anselmo.encuentrapareja.R;
import com.anselmo.encuentrapareja.model.Tip;
import com.anselmo.encuentrapareja.sqlite.Querys;
import com.anselmo.encuentrapareja.utils.TimeUtil;
import com.bumptech.glide.Glide;
import com.pushwoosh.BasePushMessageReceiver;
import com.pushwoosh.BaseRegistrationReceiver;
import com.pushwoosh.PushManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by naranya on 9/1/15.
 */
public class SplashActivity extends AppCompatActivity {
    private long splashDelay = 2500; //5 secs
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        image = (ImageView) findViewById(R.id.splash_image);

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


        Glide.with(this)
                .load(R.drawable.splash_image)
                .centerCrop()
                .crossFade()
                .into(image);
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

                String strJson = intent.getExtras().getString(PushManager.PUSH_RECEIVE_EVENT);

                try {
                    JSONObject jsonObject = new JSONObject(strJson);
                    String title = jsonObject.getString("title");

                    Querys.addTip(new Tip(0, title, TimeUtil.getCurrentDate()));

                    Intent i = new Intent(this, ShowTipActivity.class);
                    i.putExtra("push_tip", title);
                    startActivity(i);
                    finish();

                } catch (JSONException error) {
                    error.printStackTrace();
                }
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
            } else  {
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        Intent mainIntent = new Intent().setClass(SplashActivity.this, HomeActivity.class);
                        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(mainIntent);
                        finish();
                    }
                };

                Timer timer = new Timer();
                timer.schedule(task, splashDelay);
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
        Log.i("PUSH_STATUS", message);
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        setIntent(intent);

        checkMessage(intent);
    }
}
