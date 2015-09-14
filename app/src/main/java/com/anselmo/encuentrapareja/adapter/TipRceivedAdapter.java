package com.anselmo.encuentrapareja.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.anselmo.encuentrapareja.R;
import com.anselmo.encuentrapareja.model.Tip;
import com.vstechlab.easyfonts.EasyFonts;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

/**
 * Created by naranya on 9/1/15.
 */
public class TipRceivedAdapter extends ArrayAdapter<Tip> {
    private Context context;
    private int layoutResourceId;
    private ArrayList<Tip> data;

    public TipRceivedAdapter(Context context, int layoutResourceId, ArrayList<Tip> data) {
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
            holder.title = (AutofitTextView) row.findViewById(R.id.item_title_tip);
            holder.date = (TextView) row.findViewById(R.id.item_date);

            //Set Font
            holder.title.setTypeface(EasyFonts.robotoLight(context));
            holder.date.setTypeface(EasyFonts.robotoLight(context));

            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }

        //Set info data
        holder.title.setText(data.get(position).getTip());
        holder.date.setText(data.get(position).getDate());

        return row;
    }

    class RecordHolder {
        AutofitTextView title;
        TextView date;
    }
}
