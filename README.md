# Android-DropdownMenu

This is an android dropdown menu with easy to customize.

[中文文档](https://github.com/liamwang/Android-DropdownMenu/blob/master/README-CN.md)

![](https://raw.githubusercontent.com/liamwang/Android-DropdownMenu/master/graphics/default.gif)

## Download

Maven:

```xml
<dependency>
  <groupId>com.exblr</groupId>
  <artifactId>dropdown-menu</artifactId>
  <version>1.0.2</version>
  <type>pom</type>
</dependency>
```

Gradle:

```groovy
compile 'com.exblr:dropdown-menu:1.0.2'
```

## How

Add the DropdownMenu to your layout and give it an id.

```xml
<com.exblr.dropdownmenu.DropdownMenu
    android:id="@+id/dropdown_menu"
    android:layout_width="match_parent"
    android:layout_height="50dp"/>
```

Then, Call DropdownMenu object's **Add** methed in Corresponding Activity or Fragment files, to add the menu tab item.

```java
public void add(String title, List<DropdownListItem> list)
```
The first parameter for the menu title, the second parameter for the menu drop-down list data.

And there two  overloaded public  method of **add** :

```java
public void add(String title, OnMenuOpenListener onMenuOpenListener)
```

and

```java
public void add(String title, View contentView)
```

These two methods make customization more flexible, such as customizing menu content or the way of opening the menu such as PopupWindow, PopupMenu, or another Activity.

You can also custom DropdownMenu's style by setting the the attributes with `ddm` prefix  like below :
```xml
    <com.exblr.dropdownmenu.DropdownMenu
        android:id="@+id/dropdown_menu"
        android:layout_width="match_parent"
        android:layout_height="50dp"
        app:ddmBorderColor="#EEEEEE"
        app:ddmDividerColor="#DDDDDD"
        app:ddmDividerPadding="13dp"
        app:ddmTabIconNormal="@drawable/ic_arrow_down"
        app:ddmTabIconSelected="@drawable/ic_arrow_up"
        app:ddmTabTextColorNormal="#666666"
        app:ddmTabTextColorSelected="#FF008DF2"
        app:ddmTabTextSize="13sp"/>
```


