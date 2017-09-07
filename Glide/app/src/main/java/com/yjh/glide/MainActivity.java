package com.yjh.glide;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.bumptech.glide.MemoryCategory;

import javax.xml.transform.Transformer;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setAdapter(new GlideAdapter(this));

        // temporarily allow Glide to use more or less memory in certain parts
        GlideApp.get(this).setMemoryCategory(MemoryCategory.LOW);
        GlideApp.get(this).setMemoryCategory(MemoryCategory.HIGH);
        // do something...
        // Make sure to reset the memory
        GlideApp.get(this).setMemoryCategory(MemoryCategory.HIGH);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        // reduce memory when on low memory
        GlideApp.with(this).onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        // reduce memory when trim memory
        GlideApp.with(this).onTrimMemory(level);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_clear_cache:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // clear all cache
                        GlideApp.get(MainActivity.this).clearDiskCache();
                    }
                }).start();
                break;
            case R.id.action_reload:
                // reload images
                mRecyclerView.setAdapter(new GlideAdapter(MainActivity.this));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
