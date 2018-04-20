# 说明


``
本源码基础来自借鉴微信,通过改良支持设置内部按钮颜色,宽度，圆角 选中背景 未选中背景 可以随意定制修改.


### 下载
```groovy
implementation 'cn.qssq666:myswitchbutton:v0.1'

```
### 使用方法

```xml


<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:id="@+id/line"
    android:layout_height="match_parent"

    android:orientation="vertical"
    tools:context=".MainActivity">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!" />


    <cn.qssq666.switchbtn.QSSQSwitchBtn

        android:layout_margin="5dp"
        android:id="@+id/btn_switch_btn"
        android:layout_width="60dp"
        app:innerPadding="2dp"
        app:btnRadius="15dp"
        app:innerBtnColor="#000"
        app:checkBgColor="#0f0"
        app:uncheckBgColor="#ccc"
        android:layout_height="45dp" />




    <cn.qssq666.switchbtn.QSSQSwitchBtn

        android:layout_margin="5dp"
        android:id="@+id/btn_switch_btn1"
        android:layout_width="60dp"
        app:innerPadding="2dp"
        app:btnRadius="15dp"
        app:checkBgColor="#f00"
        app:uncheckBgColor="#000"
        android:layout_height="50dp" />




    <cn.qssq666.switchbtn.QSSQSwitchBtn

        android:layout_margin="5dp"
        android:id="@+id/btn_switch_btn2"
        android:layout_width="60dp"
        app:innerPadding="2dp"
        app:btnRadius="15dp"
        app:innerBtnColor="#fff"
        app:checkBgColor="#9e0"
        app:uncheckBgColor="#ccc"
        android:layout_height="50dp" />



    <cn.qssq666.switchbtn.QSSQSwitchBtn

        android:layout_margin="5dp"
        android:id="@+id/btn_switch_btn3"
        android:layout_width="60dp"
        app:innerPadding="1dp"
        app:btnRadius="15dp"

        app:checkBgColor="#fe0"
        app:uncheckBgColor="#ccc"
        android:layout_height="50dp" />


</LinearLayout>
```

#### java方式
```java

QSSQSwitchBtn switchBtn = (QSSQSwitchBtn) findViewById(R.id.btn_switch_btn);
QSSQSwitchBtn qssqSwitchBtn = new QSSQSwitchBtn(this);
public void setInnerBtnHeight(int innerBtnHeight) ;
public void setInnerBtnWidth(float innerBtnWidth) ;
public void setInnerbtnColor(int mInnerbtnColor);
public void setInnerUncheckBgColor(int mInnerUncheckBgColor) ;
public void setInnercheckBgColor(int mInnercheckBgColor);
public void setInnerPadding(int innerPadding);
public void setBtnRadius(float btnRadius);
```



#### 操作功能
```java

switchBtn.setChecked(true);

switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Toast.makeText(MainActivity.this, "check " + isChecked, Toast.LENGTH_SHORT).show();
            }
        });

```

#### 效果图

