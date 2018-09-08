package com.fj.mad06;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SaveExternalActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtFileName, edtFileContent;

    private boolean isFileExist;
    private String getFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_external);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        ImageView imgDelete = (ImageView) findViewById(R.id.img_delete);
        imgDelete.setOnClickListener(this);

        edtFileName = (EditText) findViewById(R.id.edt_file_name);
        edtFileContent = (EditText) findViewById(R.id.edt_file_content);

        getFileName = getIntent().getStringExtra("KeyFileName");

        if (getFileName == null) {
            isFileExist = false;
            getSupportActionBar().setTitle("Add Text File");
            
            imgDelete.setVisibility(View.GONE);
        } else {
            imgDelete.setVisibility(View.VISIBLE);
            
            getSupportActionBar().setTitle("Update Text File");
            edtFileName.setText(getFileName);
            edtFileName.setEnabled(false);

            StringBuilder getFileContent = new StringBuilder();

            String yourFilePath = "/sdcard/mad/" + getFileName;
            File yourFile = new File(yourFilePath);
            try {
                BufferedReader br = new BufferedReader(new FileReader(yourFile));

                String line;
                while ((line = br.readLine()) != null) {
                    getFileContent.append(line);
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            edtFileContent.setText(getFileContent);
            isFileExist = true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                saveIntoExternalStorage();
                break;
            case R.id.img_delete:
                if (isFileExist) {
                    File deleteFile = new File("/sdcard/mad", getFileName);
                    deleteFile.delete();

                    Toast.makeText(this, "Successfully delete the text file", Toast.LENGTH_SHORT).show();
                    backActivity();
                }
                break;
        }
    }

    private void saveIntoExternalStorage(){
        String fileName = edtFileName.getText().toString();
        String fileContent = edtFileContent.getText().toString();

        if (fileName.isEmpty()) {
            edtFileName.setError("Must be filled");
        } else if (fileContent.isEmpty()) {
            edtFileName.setError("Must be filled");
        } else {
            writeToSDFile(fileName, fileContent);
        }
    }

    private void writeToSDFile(String getFileName, String getFileContent){
        try {
//            To check external storage in android device
            if(isExternalStorageMounted()) {
//                To show permission dialog
                int writeExternalStoragePermission = ContextCompat.checkSelfPermission(
                                SaveExternalActivity.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        );

                if(writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SaveExternalActivity.this, new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                            },1
                    );
                }

                File newFile;
                if (!isFileExist) {
                    newFile = new File("/sdcard/mad", getFileName + ".txt");
                } else {
                    newFile = new File("/sdcard/mad", getFileName);
                }
                FileWriter fw = new FileWriter(newFile);
                fw.write(getFileContent);
                fw.flush();
                fw.close();

                Toast.makeText(this, "File saved successfully", Toast.LENGTH_SHORT).show();
                backActivity();
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private boolean isExternalStorageMounted() {
        String dirState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(dirState);
    }

    private void backActivity(){
        Intent i = new Intent(this, ExternalStorageActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        backActivity();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backActivity();
    }
}
