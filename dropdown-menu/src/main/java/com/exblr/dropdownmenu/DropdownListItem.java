package com.exblr.dropdownmenu;

/**
 * Created by Liam on 2017/2/20.
 */

public class DropdownListItem {
    private int mValue;
    private String mText;
    private boolean mSelected;
    private boolean mEmptyItem;

    public DropdownListItem(){}

    public DropdownListItem(int value, String text){
        this(value,text,false);
    }

    public DropdownListItem(int value, String text, boolean selected){
        this(value,text,selected,false);
    }

    public DropdownListItem(int value, String text, boolean selected, boolean emptyItem){
        mValue=value;
        mText=text;
        mSelected=selected;
        mEmptyItem =emptyItem;
    }

    public boolean isSelected() {
        return mSelected;
    }

    public void setSelected(boolean selected) {
        mSelected = selected;
    }

    public int getValue() {
        return mValue;
    }

    public void setValue(int value) {
        mValue = value;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public boolean isEmptyItem() {
        return mEmptyItem;
    }

    public void setEmptyItem(boolean empty) {
        mEmptyItem = empty;
    }
}
