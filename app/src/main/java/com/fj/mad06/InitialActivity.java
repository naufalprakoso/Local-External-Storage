package com.fj.mad06;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InitialActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        Button btnExternalStorage = findViewById(R.id.btn_external_storage);
        Button btnLocalStorage = findViewById(R.id.btn_local_storage);

        btnExternalStorage.setOnClickListener(this);
        btnLocalStorage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_external_storage:
                openActivity(ExternalStorageActivity.class);
                break;
            case R.id.btn_local_storage:
                openActivity(LocalStorageActivity.class);
                break;
        }
    }

    private void openActivity(Class className){
        Intent i = new Intent(this, className);
        startActivity(i);
    }
}
