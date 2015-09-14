package com.anselmo.encuentrapareja.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.anselmo.encuentrapareja.R;
import com.anselmo.encuentrapareja.adapter.StepAdapter;
import com.anselmo.encuentrapareja.analytics.AnalyticsManager;
import com.anselmo.encuentrapareja.model.Step;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity {
    private static final String TAG = HomeActivity.class.getSimpleName();

    private ListView stepList;
    private StepAdapter adapter;
    private ArrayList<Step> items;

    private int idsIcons[] = {
            R.mipmap.icn_step_1,
            R.mipmap.icn_step_2,
            R.mipmap.icn_step_3,
            R.mipmap.icn_step_4,
            R.mipmap.icn_step_5,
            R.mipmap.icn_step_6,
            R.mipmap.icn_step_7,
            R.mipmap.icn_step_8,
            R.mipmap.icn_step_9,
            R.mipmap.icn_step_10
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = getToolbar();
        toolbar.setTitle(getString(R.string.app_name_title));
        setSupportActionBar(toolbar);

        //Init ListView
        stepList = (ListView) findViewById(R.id.stepList);

        //Init items array
        items = new ArrayList<Step>();

        String arrays[] = getResources().getStringArray(R.array.array_title_steps);

        for( int i = 0; i < arrays.length; i++ ) {
            items.add( new Step(idsIcons[i], "Paso " + ( i + 1), arrays[i]));
        }

        //Init adapter
        adapter = new StepAdapter(this, R.layout.item_row_home, items);

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
    }





}
