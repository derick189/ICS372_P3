package cheetahs.androidUI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;

import cheetahs.controller.Controller;
import cheetahs.library.Library;

/*
AddFileActivity runs when the add file button is clicked in MainActivity.

 */
public class AddFileActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int READ_REQUEST_CODE = 42;
    private Uri uri;
    private TextView textFindFile, textAddFileData;
    private RadioButton radioMain, radioSister;
    Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = (Controller) getIntent().getSerializableExtra("controller");
        controller.setSavePath(getExternalFilesDir(null).getPath() + "/");

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
    // Actions for when the Find File and Add File Data buttons are clicked.
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
                }
                // Issue with using the file selected - tell user to retry and make sure file is
                // still available (in case they selected external and it got disconnected, or it
                // was mis-entered?
                catch (FileNotFoundException e) {
                    // Create an alert stating no URI was chosen
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddFileActivity.this);
                    // 2. Chain together various setter methods to set the dialog characteristics
                    builder.setMessage("Could not load that file. Make sure the file is still accessible " +
                            "in the location that you chose.");
                    builder.setTitle("Error");
                    builder.show();
                    return;
                }
                // URI is null if a file isn't picked - catch it and tell the user to select a file
                catch (NullPointerException e) {
                    // Create an alert stating no URI was chosen
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddFileActivity.this);
                    // 2. Chain together various setter methods to set the dialog characteristics
                    builder.setMessage("No file was chosen. Select a file first and try again.");
                    builder.setTitle("Error");
                    builder.show();
                    return;
                }
                if (uri.toString().endsWith("json")) {
                    fileType = "json";
                } else if (uri.toString().endsWith("xml")) {
                    fileType = "xml";
                } else {
                    textAddFileData.append("Incompatible File Type" + "\n");
                }
                if (controller.addFileData(inputStream, fileType, lib)) {
                    textAddFileData.append("Your file data has been imported" + "\n");
                } else {
                    textAddFileData.append("File data add failed." + "\n");
                }
        }
        String libData = controller.displayLibraryItems(15, Library.Type.MAIN);
        textAddFileData.append(libData + "\n");
    }

    @Override
    // onActivityResult runs when Find File is clicked.
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