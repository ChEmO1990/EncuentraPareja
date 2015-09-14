package com.anselmo.encuentrapareja.ui;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.anselmo.encuentrapareja.R;
import com.heinrichreimersoftware.materialdrawer.DrawerActivity;
import com.heinrichreimersoftware.materialdrawer.structure.DrawerItem;
import com.heinrichreimersoftware.materialdrawer.structure.DrawerProfile;
import com.heinrichreimersoftware.materialdrawer.theme.DrawerTheme;

/**
 * Created by naranya on 9/1/15.
 */
public class BaseActivity extends DrawerActivity {
    private Toolbar mActionBarToolbar;

    private void populateMenuLeft() {

        setDrawerTheme(new DrawerTheme(this)
                .setBackgroundColorRes(android.R.color.white)
                .setTextColorPrimaryRes(R.color.color_primary));

        addProfile(new DrawerProfile().setBackground(getResources().getDrawable(R.drawable.bg_menu_left)));

        addItem(new DrawerItem()
                .setTextPrimary(getString(R.string.action_home))
                .setColorTextPrimary(getResources().getColor(R.color.color_primary))
                .setOnItemClickListener(new DrawerItem.OnItemClickListener() {
                    @Override
                    public void onClick(DrawerItem item, long id, int position) {
                        closeDrawer();

                        Intent i = new Intent(BaseActivity.this, HomeActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(i);
                    }
                }));

        addItem(new DrawerItem()
                .setTextPrimary(getString(R.string.action_show_tips))
                .setColorTextPrimary(getResources().getColor(R.color.color_primary)))
                .setOnItemClickListener(new DrawerItem.OnItemClickListener() {
                    @Override
                    public void onClick(DrawerItem item, long id, int position) {
                        closeDrawer();
                        redirectTo(position);

                    }
                });

        addDivider();

        addItem(new DrawerItem()
                .setTextPrimary(getString(R.string.action_settings))
                .setColorTextPrimary(getResources().getColor(R.color.color_primary)))
                .setOnItemClickListener(new DrawerItem.OnItemClickListener() {
                    @Override
                    public void onClick(DrawerItem item, long id, int position) {
                        closeDrawer();
                        redirectTo(position);
                    }
                });
    }

    protected Toolbar getToolbar() {
        if (mActionBarToolbar == null) {
            mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
            if (mActionBarToolbar != null) {
                setSupportActionBar(mActionBarToolbar);
                Log.i("TOOLBAR_STATUS", "Se inicializo correctamente el Toolbar");
            }
        }
        return mActionBarToolbar;
    }

    private void redirectTo( int position ) {
        switch ( position ) {
            case 1:
                Intent i = new Intent(this, TipsActivity.class);
                startActivity(i);
                break;

            case 3:
                Intent in = new Intent(this, ContactActivity.class);
                startActivity(in);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        populateMenuLeft();
        getToolbar();
    }
}
