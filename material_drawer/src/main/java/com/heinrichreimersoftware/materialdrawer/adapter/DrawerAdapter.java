/*
 * Copyright 2015 Heinrich Reimer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.heinrichreimersoftware.materialdrawer.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heinrichreimersoftware.materialdrawer.R;
import com.heinrichreimersoftware.materialdrawer.structure.DrawerHeaderItem;
import com.heinrichreimersoftware.materialdrawer.structure.DrawerItem;
import com.heinrichreimersoftware.materialdrawer.theme.DrawerTheme;
import com.vstechlab.easyfonts.EasyFonts;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter to be used with {@link com.heinrichreimersoftware.materialdrawer.DrawerView} to display a list of drawer items.
 */
public class DrawerAdapter extends ArrayAdapter<DrawerItem> {

    private int selectedPosition = -1;

    private DrawerTheme drawerTheme;

    public DrawerAdapter(Context context, List<DrawerItem> dataSet) {
        super(context, R.layout.md_drawer_item, dataSet);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DrawerItem drawerItem = getItem(position);
        DrawerTheme drawerTheme = this.drawerTheme;

        if (drawerItem.hasDrawerTheme()) {
            drawerTheme = drawerItem.getDrawerTheme();
        }

        if (drawerItem.isHeader()) {
            if (convertView == null || convertView instanceof LinearLayout) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.md_drawer_header_item, parent, false);
            }

            final HeaderViewHolder viewHolder = new HeaderViewHolder(convertView);

            if (drawerTheme.isLightTheme()) {
                viewHolder.getHeaderDivider().setBackgroundColor(getContext().getResources().getColor(R.color.md_divider_light));
            } else {
                viewHolder.getHeaderDivider().setBackgroundColor(getContext().getResources().getColor(R.color.md_divider_dark));
            }

            DrawerHeaderItem drawerHeaderItem = (DrawerHeaderItem) drawerItem;


            viewHolder.getHeaderRoot().setBackgroundColor(drawerTheme.getBackgroundColor());

            if (drawerHeaderItem.hasTitle()) {
                viewHolder.getHeaderTitleRoot().setVisibility(View.VISIBLE);
                viewHolder.getHeaderRoot().setPadding(0, getContext().getResources().getDimensionPixelSize(R.dimen.md_divider_margin), 0, 0);
                viewHolder.getHeaderTitle().setText(drawerHeaderItem.getTitle());
                viewHolder.getHeaderTitle().setTextColor(drawerTheme.getTextColorSecondary());
            } else {
                viewHolder.getHeaderTitleRoot().setVisibility(View.GONE);
                viewHolder.getHeaderRoot().setPadding(0, getContext().getResources().getDimensionPixelSize(R.dimen.md_divider_margin), 0, getContext().getResources().getDimensionPixelSize(R.dimen.md_divider_margin));
            }


        } else {
            if (convertView == null || !(convertView instanceof RelativeLayout)) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.md_drawer_item, parent, false);
            }

            final ViewHolder viewHolder = new ViewHolder(convertView);

            viewHolder.getTextViewPrimary().setTypeface(EasyFonts.robotoLight(getContext()));


            int iconTint = -1;
            int textColorPrimary = drawerItem.getColorTextPrimary();

            if (drawerTheme.isLightTheme()) {
                viewHolder.getRoot().setForeground(getContext().getResources().getDrawable(R.drawable.md_selector_light));

            } else {
                viewHolder.getRoot().setForeground(getContext().getResources().getDrawable(R.drawable.md_selector_dark));
            }

            if (drawerTheme.getBackgroundColor() != 0) {
                viewHolder.getRoot().setBackgroundColor(drawerTheme.getBackgroundColor());
            } else {
                viewHolder.getRoot().setBackgroundColor(getContext().getResources().getColor(android.R.color.transparent));
            }

            if (position == selectedPosition) {
                viewHolder.getRoot().setSelected(true);

                textColorPrimary = iconTint = drawerTheme.getHighlightColor();
            } else {
                viewHolder.getRoot().setSelected(false);
            }

            if (drawerItem.hasImage()) {
                viewHolder.getImageView().setVisibility(View.VISIBLE);
                viewHolder.getImageView().setImageDrawable(drawerItem.getImage());

                int imageSize;

                if (drawerItem.getImageMode() == DrawerItem.AVATAR) {
                    imageSize = getContext().getResources().getDimensionPixelSize(R.dimen.md_avatar_size);
                } else {
                    imageSize = getContext().getResources().getDimensionPixelSize(R.dimen.md_icon_size);

                    if (iconTint != -1 && drawerItem.getImageMode() == DrawerItem.ICON) {
                        viewHolder.getImageView().setColorFilter(iconTint, PorterDuff.Mode.SRC_IN);
                    } else {
                        viewHolder.getImageView().getDrawable().clearColorFilter();
                    }
                }

                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) viewHolder.getImageView().getLayoutParams();
                layoutParams.height = imageSize;
                layoutParams.width = getContext().getResources().getDimensionPixelSize(R.dimen.md_baseline_content) - getContext().getResources().getDimensionPixelSize(R.dimen.md_baseline);

                int imagePaddingEnd = getContext().getResources().getDimensionPixelSize(R.dimen.md_baseline_content) - getContext().getResources().getDimensionPixelSize(R.dimen.md_baseline) - imageSize;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    viewHolder.getImageView().setPaddingRelative(0, 0, imagePaddingEnd, 0);
                } else {
                    viewHolder.getImageView().setPadding(0, 0, imagePaddingEnd, 0);
                }

            } else {
                viewHolder.getImageView().setVisibility(View.GONE);
            }

            if (drawerItem.hasTextPrimary()) {
                viewHolder.getTextViewPrimary().setText(drawerItem.getTextPrimary());
                viewHolder.getTextViewPrimary().setTextColor(textColorPrimary);

                //Check if bold is true
                if( drawerItem.isTextPrimaryBold() ) {
                    viewHolder.getTextViewPrimary().setTypeface(Typeface.DEFAULT_BOLD);
                }

                if (drawerItem.hasTextSecondary() && (drawerItem.getTextMode() == DrawerItem.TWO_LINE || drawerItem.getTextMode() == DrawerItem.THREE_LINE)) {
                    viewHolder.getTextViewSecondary().setText(drawerItem.getTextSecondary());
                    viewHolder.getTextViewSecondary().setTextColor(drawerTheme.getTextColorSecondary());
                    viewHolder.getTextViewSecondary().setVisibility(View.VISIBLE);

                    if (drawerItem.getTextMode() == DrawerItem.THREE_LINE) {
                        viewHolder.getTextViewSecondary().setMaxLines(2);
                    } else {
                        viewHolder.getTextViewSecondary().setMaxLines(1);
                    }

                } else {
                    viewHolder.getTextViewSecondary().setVisibility(View.GONE);
                }
            } else if (drawerItem.hasTextSecondary()) {
                viewHolder.getTextViewPrimary().setText(drawerItem.getTextPrimary());
                viewHolder.getTextViewPrimary().setTextColor(textColorPrimary);

                viewHolder.getTextViewSecondary().setVisibility(View.GONE);
            }
        }

        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return getItem(position).hasOnItemClickListener();
    }

    public void setDrawerTheme(DrawerTheme theme) {
        this.drawerTheme = theme;
        notifyDataSetChanged();
    }

    public List<DrawerItem> getItems() {
        List<DrawerItem> items = new ArrayList<DrawerItem>();
        for (int i = 0; i < getCount(); i++) {
            items.add(getItem(i));
        }
        return items;
    }

    public void select(int position) {
        if (position >= 0 && position < getCount()) {
            selectedPosition = position;
            notifyDataSetChanged();
        } else {
            selectedPosition = -1;
            notifyDataSetChanged();
        }
    }

    public void clearSelection() {
        select(-1);
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    private static class ViewHolder {
        FrameLayout mRoot;
        ImageView mImageView;
        LinearLayout mTextRoot;
        TextView mTextViewPrimary;
        TextView mTextViewSecondary;

        public ViewHolder(View root) {
            mRoot = (FrameLayout) root;
            mImageView = (ImageView) root.findViewById(R.id.mdImage);
            mTextRoot = (LinearLayout) root.findViewById(R.id.mdTextRoot);
            mTextViewPrimary = (TextView) root.findViewById(R.id.mdTextPrimary);
            mTextViewSecondary = (TextView) root.findViewById(R.id.mdTextSecondary);
        }

        public FrameLayout getRoot() {
            return mRoot;
        }

        public ImageView getImageView() {
            return mImageView;
        }

        public LinearLayout getTextRoot() {
            return mTextRoot;
        }

        public TextView getTextViewPrimary() {
            return mTextViewPrimary;
        }

        public TextView getTextViewSecondary() {
            return mTextViewSecondary;
        }
    }

    private static class HeaderViewHolder {
        LinearLayout mHeaderRoot;
        View mHeaderDivider;
        LinearLayout mHeaderTitleRoot;
        TextView mHeaderTitle;

        public HeaderViewHolder(View root) {
            mHeaderRoot = (LinearLayout) root;
            mHeaderDivider = root.findViewById(R.id.mdDivider);
            mHeaderTitleRoot = (LinearLayout) root.findViewById(R.id.mdHeaderTitleRoot);
            mHeaderTitle = (TextView) root.findViewById(R.id.mdHeaderTitle);
        }

        public LinearLayout getHeaderRoot() {
            return mHeaderRoot;
        }

        public View getHeaderDivider() {
            return mHeaderDivider;
        }

        public LinearLayout getHeaderTitleRoot() {
            return mHeaderTitleRoot;
        }

        public TextView getHeaderTitle() {
            return mHeaderTitle;
        }
    }
}