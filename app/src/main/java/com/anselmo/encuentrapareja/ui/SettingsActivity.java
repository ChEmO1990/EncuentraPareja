package com.anselmo.encuentrapareja.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.anselmo.encuentrapareja.R;
import com.anselmo.encuentrapareja.analytics.AnalyticsManager;

import java.util.ArrayList;

public class SettingsActivity extends BaseActivity {
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_list);

        AnalyticsManager.sendScreenView("SettingsActivity");

        getSupportActionBar().setTitle(getString(R.string.action_settings));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setDrawerIndicatorEnabled(false);
        setCustomIconNavigationDrawer(R.mipmap.ic_up);


        getmDrawerToggle().setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        list = (ListView) findViewById(R.id.genericList);

        ArrayList<String> items = new ArrayList<>();
        items.add(getString(R.string.contact));
        items.add(getString(R.string.about));

        ArrayAdapter<String> ada = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        list.setAdapter(ada);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                redirectTo(position);
            }
        });
    }

    private void redirectTo( int position ) {
        switch ( position ) {
            case 0:
                Intent intent1 = new Intent(this, ContactActivity.class);
                startActivity(intent1);
                break;

            case 1:
                Intent intent2 = new Intent(this, About.class);
                startActivity(intent2);
                break;
        }
    }
}
