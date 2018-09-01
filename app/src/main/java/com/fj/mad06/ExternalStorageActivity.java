package com.fj.mad06;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.fj.mad06.adapter.ExternalFileAdapter;

import java.io.File;
import java.util.ArrayList;

public class ExternalStorageActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_storage);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        Show back toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);

        ArrayList<String> listFile = new ArrayList<>();
//        Select file path
//        TODO: Before you create a new file text, you must create folder within name "mad" in /sdcard/
//        TODO: Don't forget to add permission in "manifest"
        File path = new File("/sdcard/mad");
//        Add all file name on the folder into ArrayList
        File list[] = path.listFiles();
        if (list != null) {
            for (File aList : list) {
                listFile.add(aList.getName());
            }

            RecyclerView recyclerView = findViewById(R.id.rv_data);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new ExternalFileAdapter(this, listFile));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                Intent i = new Intent(getApplicationContext(), SaveExternalActivity.class);
                startActivity(i);
                finish();
                break;
        }
    }
}
