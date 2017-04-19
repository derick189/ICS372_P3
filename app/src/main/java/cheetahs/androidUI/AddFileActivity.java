package cheetahs.androidUI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;

import cheetahs.library.Library;

public class AddFileActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int READ_REQUEST_CODE = 42;
    private Uri uri;
    private TextView textFindFile, textAddFileData;
    private RadioButton radioMain, radioSister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_file);
        findViewById(R.id.btnFindFile).setOnClickListener(this);
        textFindFile = (TextView) findViewById(R.id.textFindFile);
        textFindFile.setMovementMethod(new ScrollingMovementMethod());
        radioMain = (RadioButton) findViewById(R.id.radioMain);
        radioMain.setChecked(true);
        radioSister = (RadioButton) findViewById(R.id.radioSister);
        findViewById(R.id.btnAddFileData).setOnClickListener(this);
        textAddFileData = (TextView) findViewById(R.id.textAddFileData);
        textAddFileData.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnFindFile:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, READ_REQUEST_CODE);
                break;
            case R.id.btnAddFileData:
                Library.Type lib;
                InputStream inputStream = null;
                String fileType = null;

                if (radioSister.isChecked()) {
                    lib = Library.Type.SISTER;
                } else {
                    lib = Library.Type.MAIN;
                }
                try {
                    inputStream = getContentResolver().openInputStream(uri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                if (uri.toString().endsWith("json")) {
                    fileType = "json";
                } else if (uri.toString().endsWith("xml")) {
                    fileType = "xml";
                } else {
                    textAddFileData.append("Incompatible File Type" + "\n");
                }
                if (MainActivity.controller.addFileData(inputStream, fileType, lib)) {
                    textAddFileData.append("Your file data has been imported" + "\n");
                } else {
                    textAddFileData.append("File data add failed." + "\n");
                }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            if (resultData != null) {
                uri = resultData.getData();
                textFindFile.append("File used: \n" + uri.toString() + "\n");
                if (!uri.toString().endsWith("json") && !uri.toString().endsWith("xml")) {
                    textFindFile.append("Incompatible File Type" + "\n");
                }
            }
        }
    }
}