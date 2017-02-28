package com.exblr.dropdownmenu.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.exblr.dropdownmenu.DropdownListItem;
import com.exblr.dropdownmenu.DropdownMenu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DropdownMenu mDropdownMenu;

    private GridViewAdapter mGridViewAdapter;

    private ArrayList list = new ArrayList() {{
        add(new DropdownListItem(1, "Item 1"));
        add(new DropdownListItem(2, "Item 2"));
    }};

    private ArrayList list1 = createMockList(5, false);
    private ArrayList list2 = createMockList(15, true);
    private ArrayList list3 = createMockList(5, false);
    private ArrayList list4 = createMockList(5, false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGridViewAdapter = new GridViewAdapter(this, list2);

        View customContentView = getLayoutInflater().inflate(R.layout.ddm_custom_content, null, false);
        GridView gridView = (GridView) customContentView.findViewById(R.id.ddm_custom_content_gv);
        gridView.setAdapter(mGridViewAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DropdownListItem item = mGridViewAdapter.setSelectedItem(position);
                mDropdownMenu.setCurrentTitle(item.isEmptyItem() ? "Menu 2" : item.getText());
                mDropdownMenu.dismissCurrentPopupWindow();
            }
        });

        mDropdownMenu = (DropdownMenu) findViewById(R.id.dropdown_menu);
        mDropdownMenu.add("Menu 1", list1);
        mDropdownMenu.add("Menu 2", customContentView);
        mDropdownMenu.add("Menu 3", list3);
        mDropdownMenu.add("Menu 4", list4);
    }

    private ArrayList createMockList(int count, boolean hasEmpty) {
        ArrayList list = new ArrayList();
        if (hasEmpty) {
            list.add(new DropdownListItem(0, "不限", true, true));
        }
        for (int i = 1; i <= count; i++) {
            list.add(new DropdownListItem(10 + i, "Item-1-" + i));
        }
        return list;
    }
}
