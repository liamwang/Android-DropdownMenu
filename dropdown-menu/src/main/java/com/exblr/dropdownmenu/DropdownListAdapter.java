package com.exblr.dropdownmenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Liam on 2017/2/22.
 */

public class DropdownListAdapter extends BaseAdapter{

    private Context mContext;
    private String mDefaultTitle;
    private List<DropdownListItem> mList;

    private int mNormalTextColor = 0xFF666666;
    private int mSelectedTextColor = 0xFF3971BC;

    public DropdownListAdapter(Context context, String defaultTitle, List<DropdownListItem> list) {
        mContext = context;
        mDefaultTitle = defaultTitle;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public DropdownListItem getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mList.get(position).getValue();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.dopdown_list_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder.bind(getItem(position));
        return convertView;
    }

    public String getSelectedItemString() {
        for (DropdownListItem item : mList) {
            if (item.isSelected() && !item.isEmptyItem())
                return item.getText();
        }
        return mDefaultTitle;
    }

    public void setSelectedItem(int position) {
        for (int i = 0; i < mList.size(); i++) {
            mList.get(i).setSelected(position == i);
        }
        notifyDataSetChanged();
    }

    private class ViewHolder {
        TextView mTextView;

        ViewHolder(View view) {
            mTextView = (TextView) view.findViewById(R.id.dropdown_list_item_text_view);
        }

        public void bind(DropdownListItem item) {
            mTextView.setText(item.getText());
            if (item.isSelected()) {
                mTextView.setTextColor(mSelectedTextColor);
                mTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.drawable.ic_list_item_checked), null);
            } else {
                mTextView.setTextColor(mNormalTextColor);
                mTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
        }
    }

}