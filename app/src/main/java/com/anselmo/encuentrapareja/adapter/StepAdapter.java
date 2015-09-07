package com.anselmo.encuentrapareja.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.anselmo.encuentrapareja.R;
import com.anselmo.encuentrapareja.model.Step;
import com.vstechlab.easyfonts.EasyFonts;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

/**
 * Created by naranya on 9/1/15.
 */
public class StepAdapter extends ArrayAdapter<Step> {
    private Context context;
    private int layoutResourceId;
    private ArrayList<Step> data;

    public StepAdapter(Context context, int layoutResourceId, ArrayList<Step> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecordHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new RecordHolder();
            holder.image = (ImageView) row.findViewById(R.id.imageStep);
            holder.title = (AutofitTextView) row.findViewById(R.id.textStep);
            holder.subtitle = (AutofitTextView) row.findViewById(R.id.subtitleStep);

            //Set Font
            holder.title.setTypeface(EasyFonts.robotoLight(context));
            holder.subtitle.setTypeface(EasyFonts.robotoLight(context));

            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }

        //Set info data
        holder.image.setImageResource( data.get(position).getResourceImage() );
        holder.title.setText(data.get(position).getTitle());
        holder.subtitle.setText(data.get(position).getSubtitle());

        return row;
    }

    class RecordHolder {
        ImageView image;
        AutofitTextView title;
        AutofitTextView subtitle;
    }
}
