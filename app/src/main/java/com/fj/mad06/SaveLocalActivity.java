package com.fj.mad06;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class SaveLocalActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtFileName, edtFileContent;

    private boolean isFileExist;
    private String getFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_local);
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

//        To check is file exists
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
            String filePath = "/data/data/com.fj.mad06/files/" + getFileName;
            File yourFile = new File(filePath);

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
        switch (v.getId()) {
            case R.id.fab:
                saveIntoInternalStorage();
                break;
            case R.id.img_delete:
                if (isFileExist) {
                    File deleteFile = new File("/data/data/com.fj.mad06/files/", getFileName);
                    deleteFile.delete();

                    Toast.makeText(this, "Successfully delete the text file", Toast.LENGTH_SHORT).show();
                    backActivity();
                }
                break;
        }
    }

    private void saveIntoInternalStorage(){
        String fileName = edtFileName.getText().toString();
        String fileContent = edtFileContent.getText().toString();

        if (fileName.isEmpty()) {
            edtFileName.setError("Must be filled");
        } else if (fileContent.isEmpty()) {
            edtFileName.setError("Must be filled");
        } else {
            try {
                FileOutputStream fileOut;
                if (!isFileExist) {
                    fileOut = openFileOutput(fileName + ".txt", MODE_PRIVATE);
                } else {
                    fileOut = openFileOutput(fileName, MODE_PRIVATE);
                }
                OutputStreamWriter outputWriter = new OutputStreamWriter(fileOut);
                outputWriter.write(fileContent);
                outputWriter.close();

                Toast.makeText(this, "File saved successfully", Toast.LENGTH_SHORT).show();
                backActivity();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void backActivity(){
        Intent i = new Intent(this, LocalStorageActivity.class);
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
