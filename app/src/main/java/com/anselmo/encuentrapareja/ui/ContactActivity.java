package com.anselmo.encuentrapareja.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.anselmo.encuentrapareja.R;
import com.anselmo.encuentrapareja.utils.GenericUtils;
import com.github.mrengineer13.snackbar.SnackBar;
import com.kylewbanks.android.iconedittext.IconEditText;
import com.vstechlab.easyfonts.EasyFonts;

public class ContactActivity extends AppCompatActivity {
    private IconEditText user;
    private IconEditText comment;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        user = (IconEditText) findViewById(R.id.edit_email);
        comment = (IconEditText) findViewById(R.id.edit_comment);
        btnSend = (Button) findViewById(R.id.btn_send_comment);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( !TextUtils.isEmpty(user.getText().toString()) && !TextUtils.isEmpty(comment.getText().toString() ) ) {
                    if( !GenericUtils.isEmailValid( user.getText().toString() ) ) {
                        new SnackBar.Builder(ContactActivity.this)
                                .withMessage(getString(R.string.message_information_incomplete))
                                .withTypeFace(EasyFonts.robotoLight(ContactActivity.this))
                                .withTextColorId(R.color.color_primary)
                                .withStyle(SnackBar.Style.DEFAULT)
                                .withDuration(SnackBar.MED_SNACK)
                                .show();
                    } else {
                        sendEmail();
                    }
                } else {
                    new SnackBar.Builder(ContactActivity.this)
                            .withMessage(getString(R.string.message_invalid_email))
                            .withTypeFace(EasyFonts.robotoLight(ContactActivity.this))
                            .withTextColorId(R.color.color_primary)
                            .withStyle(SnackBar.Style.DEFAULT)
                            .withDuration(SnackBar.MED_SNACK)
                            .show();
                }
            }
        });
    }

    protected void sendEmail() {
        Log.i("Send email", "");
        String[] TO = {""};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished email..", "");
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ContactActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

}
