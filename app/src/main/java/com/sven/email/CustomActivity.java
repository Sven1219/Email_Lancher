package com.sven.email;

import android.app.WallpaperManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.sven.email.customLauncher.HomeScreenFragment;

public class CustomActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_frame);

        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        Drawable wallpaperDrawable = wallpaperManager.getDrawable();
        FrameLayout frame_home = findViewById(R.id.frame_home);
        frame_home.setBackground(wallpaperDrawable);

        getSupportActionBar().hide();
        loadFragment(new HomeScreenFragment());

    }
    private boolean loadFragment(Fragment fragment) {

        if (fragment != null) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_home, fragment)
                    .commit();
            return true;

        }

        return false;

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // Do not exit the launcher activity when the back button is pressed.
            loadFragment(new HomeScreenFragment());
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
