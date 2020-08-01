package com.xdeveloper.alphaone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SetWallpaper extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_wallpaper);

    }


    public void apply(View view) {
        MainApplication application = (MainApplication) getApplication();
        application.gif = ((EditText) findViewById(R.id.gif)).getText().toString();
        application.posX = Float.parseFloat(((EditText) findViewById(R.id.positionX)).getText().toString());
        application.posY = Float.parseFloat(((EditText) findViewById(R.id.positionY)).getText().toString());
        application.scaleX = Float.parseFloat(((EditText) findViewById(R.id.scaleX)).getText().toString());
        application.scaleY = Float.parseFloat(((EditText) findViewById(R.id.scaleY)).getText().toString());
        Intent intent = new Intent(
                WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                new ComponentName(this, GIFWallpaperService.class));
        startActivity(intent);
    }
}
