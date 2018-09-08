package com.fj.mad06;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.fj.mad06.adapter.LocalFileAdapter;

import java.io.File;
import java.util.ArrayList;

public class LocalStorageActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_storage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        Show back toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView txtNoData = (TextView) findViewById(R.id.txt_no_data);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_data);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        ArrayList<String> listFile = new ArrayList<>();
//        Select file path
//        TODO: Before you create a new file text, you must create folder within name "files" in /data/data/com.fj.mad06/
        File path = new File("/data/data/com.fj.mad06/files");
//        Add all file name on the folder into ArrayList
        File list[] = path.listFiles();
        if (list.length != 0) {
            for (File aList : list) {
                listFile.add(aList.getName());
            }

            txtNoData.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new LocalFileAdapter(this, listFile));
        } else {
            txtNoData.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                Intent i = new Intent(getApplicationContext(), SaveLocalActivity.class);
                startActivity(i);
                finish();
                break;
        }
    }
}
