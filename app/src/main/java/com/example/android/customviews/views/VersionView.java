package com.example.android.customviews.views;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.TextView;

/** Simple TextView that show current version */
public class VersionView extends TextView {

    // when View is created in Java
    public VersionView(Context context) {
        super(context);
        setVersion();
    }

    // when View is defined in XML
    public VersionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setVersion();
    }

    // when View is defined in XML with specified Style
    public VersionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setVersion();
    }

    private void setVersion(){
        try{
            PackageInfo packageInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(),0);
            setText(packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        setBackgroundColor(Color.RED);
    }

}
