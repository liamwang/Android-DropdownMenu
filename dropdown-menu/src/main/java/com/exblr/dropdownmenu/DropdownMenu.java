package com.exblr.dropdownmenu;

import android.content.Context;
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

    private int mCurrentTabIndex;
    private int mTabTextSize = 13;
    private int mTabNormalColor = 0xFF666666;
    private int mTabSelectedColor = 0xFF008DF2;
    private int mTabArrowLeftPadding = 5;

    private int mDividerColor = 0xFFdddddd;
    private int mDividerPadding = 13;

    private int mLineHeight = 1;
    private int mLineColor = 0xFFeeeeee;

    private Context mContext;

    private List<OnMenuOpenListener> mOnMenuOpenListeners;

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
        mTabArrowLeftPadding = dpToPx(mContext, mTabArrowLeftPadding);
        mOnMenuOpenListeners = new ArrayList<>();
    }

    public interface OnMenuOpenListener {
        void onOpen(View tabView, int tabIndex);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int measureHeight = getMeasuredHeight();
        int measuredWidth = getMeasuredWidth();

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        // 绘制Tab分隔线
        paint.setColor(mDividerColor);
        for (int i = 0; i < getChildCount() - 1; ++i) {
            final View child = getChildAt(i);
            if (child == null || child.getVisibility() == View.GONE) {
                continue;
            }
            canvas.drawLine(child.getRight(), mDividerPadding, child.getRight(), measureHeight - mDividerPadding, paint);
        }

        // 绘制上下边框
        paint.setColor(mLineColor);
        canvas.drawRect(0, 0, measuredWidth, mLineHeight, paint);
        canvas.drawRect(0, measureHeight - mLineHeight, measuredWidth, measureHeight, paint);
    }

    public void add(String title, OnMenuOpenListener onMenuOpenListener) {
        addTab(title);
        mOnMenuOpenListeners.add(onMenuOpenListener);
    }

    public void add(String title, List<DropdownListItem> list) {
        View popupWindowView = LayoutInflater.from(mContext).inflate(R.layout.popup_window, null, false);
        final PopupWindow popupWindow = new PopupWindow(popupWindowView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                notifyMenuCanceled();
            }
        });

        View overlay = popupWindowView.findViewById(R.id.popup_window_overlay);
        overlay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        final DropdownListAdapter defaultAdapter = new DropdownListAdapter(mContext, title, list);
        ListView listView = (ListView) popupWindowView.findViewById(R.id.popup_window_list_view);
        listView.setAdapter(defaultAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                defaultAdapter.setSelectedItem(position);
                setCurrentTitle(defaultAdapter.getSelectedItemString());
                popupWindow.dismiss();
            }
        });


        add(title, new OnMenuOpenListener() {
            @Override
            public void onOpen(View tabView, int tabIndex) {
                popupWindow.showAsDropDown(DropdownMenu.this);
            }
        });
    }

    public void notifyMenuCanceled() {
        setTabNormal(mCurrentTabIndex, null);
    }

    private View addTab(String title) {
        TextView titleTV = new TextView(mContext);
        titleTV.setGravity(Gravity.CENTER);
        titleTV.setText(title);
        titleTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTabTextSize);
        titleTV.setTextColor(mTabNormalColor);
        titleTV.setSingleLine();
        titleTV.setEllipsize(TextUtils.TruncateAt.END);
        titleTV.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.level_filter), null);
        titleTV.setCompoundDrawablePadding(mTabArrowLeftPadding);

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
        tv.setTextColor(mTabNormalColor);
        tv.getCompoundDrawables()[2].setLevel(0);
        if (title != null && title != "") {
            tv.setText(title);
        }
    }

    private void setTabActive(int index) {
        TextView tabTextView = getTabTextViewAt(index);
        tabTextView.setTextColor(mTabSelectedColor);
        tabTextView.getCompoundDrawables()[2].setLevel(1);
    }

    private class MyTabClickedListener implements OnClickListener {
        private int mIndex;

        public MyTabClickedListener(int index) {
            mIndex = index;
        }

        @Override
        public void onClick(View view) {
            setTabNormal(mCurrentTabIndex, null);
            setTabActive(mIndex);
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