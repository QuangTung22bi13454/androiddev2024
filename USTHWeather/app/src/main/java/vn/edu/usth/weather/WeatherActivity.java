package vn.edu.usth.weather;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class WeatherActivity extends AppCompatActivity {

    public static String TAG = "WeatherActivity";

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Thiết lập Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Điều chỉnh hệ thống Insets để cải thiện trải nghiệm UI
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Thiết lập ViewPager và TabLayout cho việc điều hướng giữa các fragment
        PagerAdapter adapter = new HomeFragmentPagerAdapter(getSupportFragmentManager());
        ViewPager pager = findViewById(R.id.view_pager);
        pager.setOffscreenPageLimit(3);
        pager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(pager);

        // Phát nhạc
        playMusic();
    }

    private void playMusic() {
        // Khởi tạo MediaPlayer với file MP3 trong thư mục raw
        mediaPlayer = MediaPlayer.create(this, R.raw.meta);

        if (mediaPlayer != null) {
            mediaPlayer.start();
            Log.i(TAG, "Playing MP3 from raw folder");

            // Xử lý khi nhạc phát xong
            mediaPlayer.setOnCompletionListener(mp -> {
                Log.i(TAG, "MP3 playback completed");
                mediaPlayer.release();
                mediaPlayer = null;
            });
        } else {
            Log.e(TAG, "Failed to create MediaPlayer");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "on pause");
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "on resume");
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "on destroy");
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu từ file menu.xml
        getMenuInflater().inflate(R.menu.menu_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Xử lý hành động Refresh
        if (id == R.id.action_refresh) {
            // Hiển thị thông báo khi nhấn Refresh
            Toast.makeText(this, "Refreshing...", Toast.LENGTH_SHORT).show();

            new Thread(() -> {
                // Giả lập việc lấy dữ liệu trong 2 giây
                try {
                    Thread.sleep(2000);  // Giả lập thời gian chờ
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Cập nhật UI sau khi hoàn thành
                runOnUiThread(() -> {
                    // Hiển thị thông báo hoàn thành
                    Toast.makeText(WeatherActivity.this, "Data refreshed!", Toast.LENGTH_SHORT).show();
                });
            }).start();

            return true;
        }

        // Xử lý hành động Settings
        if (id == R.id.action_settings) {
            // Mở PrefActivity
            Intent intent = new Intent(this, PrefActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
