package com.exblr.dropdownmenu.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.exblr.dropdownmenu.DropdownListItem;
import com.exblr.dropdownmenu.DropdownMenu;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DropdownMenu mDropdownMenu;

    private ArrayList list = new ArrayList() {{
        add(new DropdownListItem(1, "Item 1"));
        add(new DropdownListItem(2, "Item 2"));
    }};

    private ArrayList list1 = new ArrayList();
    private ArrayList list2 = new ArrayList();
    private ArrayList list3 = new ArrayList();
    private ArrayList list4 = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initMockData();

        mDropdownMenu = (DropdownMenu) findViewById(R.id.dropdown_menu);
        mDropdownMenu.add("Menu 1", list1);
        mDropdownMenu.add("Menu 2", list2);
        mDropdownMenu.add("Menu 3", list3);
        mDropdownMenu.add("Menu 4", list4);
    }

    private void initMockData() {
        for (int i = 1; i < 6; i++) {
            list1.add(new DropdownListItem(10 + i, "Item-1-" + i));
            list2.add(new DropdownListItem(20 + i, "Item-2-" + i));
            list3.add(new DropdownListItem(30 + i, "Item-3-" + i));
            list4.add(new DropdownListItem(40 + i, "Item-4-" + i));
        }
    }
}
