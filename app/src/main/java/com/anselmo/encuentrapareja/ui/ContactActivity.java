package com.anselmo.encuentrapareja.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anselmo.encuentrapareja.R;
import com.anselmo.encuentrapareja.analytics.AnalyticsManager;
import com.github.mrengineer13.snackbar.SnackBar;
import com.vstechlab.easyfonts.EasyFonts;

public class ContactActivity extends BaseActivity {
    private EditText comment;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);


        AnalyticsManager.sendScreenView("ContactActivity");

        getSupportActionBar().setTitle(getString(R.string.contact));
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

        comment = (EditText) findViewById(R.id.edit_comment);
        btnSend = (Button) findViewById(R.id.btn_send_comment);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( comment.getText().toString().trim().length() <= 5 ) {
                    new SnackBar.Builder(ContactActivity.this)
                            .withMessage(getString(R.string.message_short_comment))
                            .withTypeFace(EasyFonts.robotoLight(ContactActivity.this))
                            .withTextColorId(R.color.color_primary)
                            .withStyle(SnackBar.Style.DEFAULT)
                            .withDuration(SnackBar.MED_SNACK)
                            .show();
                } else {
                    sendEmail();
                }
            }
        });
    }

    protected void sendEmail() {
        String[] TO = {"tipsencuentrapareja@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.extra_subject));
        emailIntent.putExtra(Intent.EXTRA_TEXT, comment.getText().toString().trim());

        try {
            startActivity(Intent.createChooser(emailIntent, getString(R.string.sending_email)));
            finish();
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ContactActivity.this, getString(R.string.no_client), Toast.LENGTH_SHORT).show();
        }
    }

}
