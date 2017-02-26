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
  <version>0.0.1</version>
  <type>pom</type>
</dependency>
```

Gradle:

```groovy
compile 'com.exblr:dropdown-menu:0.0.1'
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
mDropdownMenu.add(String, List<DropdownListItem>);
```
The first parameter for the menu title, the second parameter for the menu drop-down list data.

And the **add** method has an overloaded method：

```java
mDropdownMenu.add(String, OnMenuOpenListener);
```

This method can be used for customize menus flexible, such as customizing menu content, or the way of opening the menu as PopupWindow, PopupMenu, or another Activity.

