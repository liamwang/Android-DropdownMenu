# Android-DropdownMenu

这是一个易于扩展的 Android 下拉菜单组件。

![](https://raw.githubusercontent.com/liamwang/Android-DropdownMenu/master/graphics/default.gif)

## 引入

Maven:

```xml
<dependency>
  <groupId>com.exblr</groupId>
  <artifactId>dropdown-menu</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```

Gradle:

```groovy
compile 'com.exblr:dropdown-menu:1.0.0'
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
public void add(String title, List<DropdownListItem> list)
```

第一个参数为菜单标题，第二个参数为该菜单对应的下拉列表数据。

此 add 方法还有两个公开的重载方法：

```java
public void add(String title, OnMenuOpenListener onMenuOpenListener)
```

和

```java
public void add(String title, View contentView)
```

这两个方法使得菜单的自定义更加灵活，比如自定义菜单的内容和打开菜单的方式，如 PopupWindow、PopupMenu 或另一个Activity。

你还可以像下面这样通过自定义以 `ddm` 为前缀的属性来设置 DropdownMenu 的样式 :
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


