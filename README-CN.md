# Android-DropdownMenu

这是一个易于扩展的 Android 下拉菜单组件。

![](https://raw.githubusercontent.com/liamwang/Android-DropdownMenu/master/graphics/default.gif)

## 引入

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

## 如何使用

把 DropdownMenu 添加到Layout文件中，并给定一个ID。

```xml
<com.exblr.dropdownmenu.DropdownMenu
    android:id="@+id/dropdown_menu"
    android:layout_width="match_parent"
    android:layout_height="50dp"/>
```

然后在对应的 Activity 或 Fragment 文件中，调用DropdownMenu 对象的 **add** 方法来添加菜单项。

```java
mDropdownMenu.add(String, List<DropdownListItem>);
```
第一个参数为菜单标题，第二个参数为该菜单对应的下拉列表数据。

此 add 方法还有一个重载方法：

```java
mDropdownMenu.add(String, OnMenuOpenListener);
```

此方法可对菜单进行灵活的自定义，比如自定义打开菜单的方式和菜单的内容，可以是 PopupWindow、PopupMenu 或 另一个Activity。


