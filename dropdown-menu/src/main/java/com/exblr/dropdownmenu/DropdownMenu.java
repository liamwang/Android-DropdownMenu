package com.exblr.dropdownmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liam on 2017/2/21.
 */

public class DropdownMenu extends LinearLayout {

    private int mTabTextSize;
    private int mTabTextColorNormal;
    private int mTabTextColorSelected;
    private int mDividerColor;
    private int mDividerPadding;
    private int mBorderColor;
    private int mTabIconNormal;
    private int mTabIconSelected;

    private Context mContext;

    private int mCurrentTabIndex;

    private List<OnMenuOpenListener> mOnMenuOpenListeners;
    private PopupWindow mCurrentPopupWindow;

    public DropdownMenu(Context context) {
        this(context, null);
    }

    public DropdownMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropdownMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.HORIZONTAL);
        setWillNotDraw(false);

        mContext = context;
        mDividerPadding = dpToPx(mContext, mDividerPadding);
        mOnMenuOpenListeners = new ArrayList<>();

        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.DropdownMenu);
        mTabTextSize = t.getDimensionPixelSize(R.styleable.DropdownMenu_ddmTabTextSize, 13);
        mTabTextColorNormal = t.getColor(R.styleable.DropdownMenu_ddmTabTextColorNormal, 0xFF666666);
        mTabTextColorSelected = t.getColor(R.styleable.DropdownMenu_ddmTabTextColorSelected, 0xFF008DF2);
        mDividerColor = t.getColor(R.styleable.DropdownMenu_ddmDividerColor, 0xFFDDDDDD);
        mDividerPadding = t.getDimensionPixelSize(R.styleable.DropdownMenu_ddmDividerPadding, dpToPx(mContext, 13));
        mBorderColor = t.getColor(R.styleable.DropdownMenu_ddmBorderColor, 0xFFEEEEEE);
        mTabIconNormal = t.getResourceId(R.styleable.DropdownMenu_ddmTabIconNormal, R.drawable.ic_arrow_down);
        mTabIconSelected = t.getResourceId(R.styleable.DropdownMenu_ddmTabIconSelected, R.drawable.ic_arrow_up);
        t.recycle();
    }

    public interface OnMenuOpenListener {
        void onOpen(View tabView, int tabIndex);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int w = getWidth();
        int h = getHeight();

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        // 绘制Tab分隔线
        paint.setColor(mDividerColor);
        for (int i = 0; i < getChildCount() - 1; ++i) {
            final View child = getChildAt(i);
            if (child == null || child.getVisibility() == View.GONE) {
                continue;
            }
            canvas.drawLine(child.getRight(), mDividerPadding, child.getRight(), h - mDividerPadding, paint);
        }

        // 绘制上下边框
        int lineHeight = 1;
        paint.setColor(mBorderColor);
        canvas.drawRect(0, 0, w, lineHeight, paint);
        canvas.drawRect(0, h - lineHeight, w, h, paint);
    }

    public void add(String title, OnMenuOpenListener onMenuOpenListener) {
        addTab(title);
        mOnMenuOpenListeners.add(onMenuOpenListener);
    }

    public void add(String title, List<DropdownListItem> list) {
        ListView listView = new ListView(mContext);
        listView.setLayoutParams(new LayoutParams(-1, -2));
        listView.setScrollBarStyle(SCROLLBARS_OUTSIDE_OVERLAY);

        final PopupWindow popupWindow = createPopupWindow(title, listView);
        final DropdownListAdapter defaultAdapter = new DropdownListAdapter(mContext, title, list);

        listView.setAdapter(defaultAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                defaultAdapter.setSelectedItem(position);
                setCurrentTitle(defaultAdapter.getSelectedItemString());
                popupWindow.dismiss();
            }
        });
    }

    public void add(String title, View contentView) {
        createPopupWindow(title, contentView);
    }

    public void notifyMenuClosed() {
        setTabNormal(mCurrentTabIndex, null);
    }

    public void dismissCurrentPopupWindow() {
        if (mCurrentPopupWindow != null) {
            mCurrentPopupWindow.dismiss();
        }
    }

    private PopupWindow createPopupWindow(String title, View contentView) {
        View popupWindowView = LayoutInflater.from(mContext).inflate(R.layout.popup_window, null, false);
        final PopupWindow popupWindow = new PopupWindow(popupWindowView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
        /*popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(true);*/
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                notifyMenuClosed();
                mCurrentPopupWindow = null;
            }
        });

        FrameLayout contentContainer = (FrameLayout) popupWindowView.findViewById(R.id.popup_window_content_container);
        contentContainer.addView(contentView);

        View overlay = popupWindowView.findViewById(R.id.popup_window_overlay);
        overlay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        add(title, new OnMenuOpenListener() {
            @Override
            public void onOpen(View tabView, int tabIndex) {
                popupWindow.showAsDropDown(DropdownMenu.this);
                mCurrentPopupWindow = popupWindow;
            }
        });

        return popupWindow;
    }

    private View addTab(String title) {
        TextView titleTV = new TextView(mContext);
        titleTV.setGravity(Gravity.CENTER);
        titleTV.setText(title);
        titleTV.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTabTextSize);
        titleTV.setTextColor(mTabTextColorNormal);
        titleTV.setSingleLine();
        titleTV.setEllipsize(TextUtils.TruncateAt.END);
        // titleTV.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.level_filter), null);
        titleTV.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(mTabIconNormal), null);
        titleTV.setCompoundDrawablePadding(dpToPx(mContext, 5));

        RelativeLayout tabLayout = new RelativeLayout(mContext);
        RelativeLayout.LayoutParams titleParams = new RelativeLayout.LayoutParams(-2, -2);
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        tabLayout.addView(titleTV, titleParams);
        tabLayout.setPaddingRelative(dpToPx(mContext, 5), 0, dpToPx(mContext, 5), 0);

        LayoutParams tabParams = new LayoutParams(-1, -1, 1f);
        tabParams.gravity = Gravity.CENTER;
        tabLayout.setLayoutParams(tabParams);
        tabLayout.setOnClickListener(new MyTabClickedListener(getChildCount()));

        addView(tabLayout);

        // postInvalidate();

        return tabLayout;
    }

    private TextView getTabTextViewAt(int index) {
        return (TextView) ((ViewGroup) getChildAt(index)).getChildAt(0);
    }

    public void setCurrentTitle(String text) {
        setTabNormal(mCurrentTabIndex, text);
    }

    private void setTabNormal(int index, String title) {
        TextView tv = getTabTextViewAt(index);
        tv.setTextColor(mTabTextColorNormal);
        //tv.getCompoundDrawables()[2].setLevel(0);
        tv.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(mTabIconNormal), null);
        if (title != null && title != "") {
            tv.setText(title);
        }
    }

    private void setTabSelected(int index) {
        TextView tv = getTabTextViewAt(index);
        tv.setTextColor(mTabTextColorSelected);
        //tabTextView.getCompoundDrawables()[2].setLevel(1);
        tv.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(mTabIconSelected), null);
    }

    private class MyTabClickedListener implements OnClickListener {
        private int mIndex;

        public MyTabClickedListener(int index) {
            mIndex = index;
        }

        @Override
        public void onClick(View view) {
            setTabNormal(mCurrentTabIndex, null);
            setTabSelected(mIndex);
            mCurrentTabIndex = mIndex;

            for (int i = 0; i < mOnMenuOpenListeners.size(); i++) {
                if (i == mCurrentTabIndex) {
                    mOnMenuOpenListeners.get(i).onOpen(view, i);
                    break;
                }
            }
        }
    }

    public static int dpToPx(Context context, int dp) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()) + 0.5f);
    }
}