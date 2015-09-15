package com.anselmo.encuentrapareja.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.anselmo.encuentrapareja.R;
import com.anselmo.encuentrapareja.adapter.TipRceivedAdapter;
import com.anselmo.encuentrapareja.analytics.AnalyticsManager;
import com.anselmo.encuentrapareja.model.Tip;
import com.anselmo.encuentrapareja.sqlite.Querys;
import com.github.mrengineer13.snackbar.SnackBar;
import com.vstechlab.easyfonts.EasyFonts;

import java.util.ArrayList;

public class TipsActivity extends BaseActivity {
    private ListView list;
    private TipRceivedAdapter adapter;
    private ArrayList<Tip> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_list);

        AnalyticsManager.sendScreenView("TipsActivity");
        list = (ListView) findViewById(R.id.genericList);

        getSupportActionBar().setTitle(getString(R.string.action_show_tips));
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

        items = new ArrayList<>();

        if ( Querys.getAllTips() != null ) {
            items.addAll(Querys.getAllTips());
            adapter = new TipRceivedAdapter(this, R.layout.item_row_tip, items);
            list.setAdapter(adapter);
        } else {
            new SnackBar.Builder(this)
                    .withMessage(getString(R.string.message_tips_empty))
                    .withTypeFace(EasyFonts.robotoLight(this))
                    .withTextColorId(R.color.color_primary)
                    .withStyle(SnackBar.Style.DEFAULT)
                    .withDuration(SnackBar.MED_SNACK)
                    .show();
        }
    }
}
