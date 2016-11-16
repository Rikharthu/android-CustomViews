package com.example.android.customviews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.android.customviews.views.LengthPicker;
import com.example.android.customviews.views.ShapeSelectorView;
import com.example.android.customviews.views.VersionView;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG=MainActivity.class.getSimpleName();


    private ShapeSelectorView shapeSelectorView;
    private VersionView versionView;
    private LengthPicker lengthPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shapeSelectorView= (ShapeSelectorView) findViewById(R.id.shapeSelector);
        shapeSelectorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG,"ShapeSelectorView clicked");
                Toast.makeText(MainActivity.this, "Shape: "+shapeSelectorView.getSelectedShape(), Toast.LENGTH_SHORT).show();
            }
        });

        versionView = new VersionView(this);

        lengthPicker= (LengthPicker) findViewById(R.id.lengthPicker);
        // use our custom listener
        lengthPicker.setOnChangeListener(new LengthPicker.OnChangeListener() {
            @Override
            public void onChange(int length) {
                Toast.makeText(MainActivity.this, "Length: "+length, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
