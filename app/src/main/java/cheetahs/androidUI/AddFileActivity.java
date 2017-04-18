package cheetahs.androidUI;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;

import cheetahs.controller.Controller;
import cheetahs.library.Library;

public class AddFileActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int READ_REQUEST_CODE = 42;
    private Uri uri;
    private TextView fileText;
    private Controller controller;
    private File file;
    private RadioButton mainRad, sisterRad;
    private TextView text;

//    private RadioButton mainRad = (RadioButton) findViewById(R.id.radioMain);
  //  private RadioButton sisterRad = (RadioButton) findViewById(R.id.radioSister);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_file);
        ((Button) findViewById(R.id.addFileData)).setOnClickListener(this);
        ((Button) findViewById(R.id.findFile)).setOnClickListener(this);
        fileText = (TextView) findViewById(R.id.file);
        sisterRad = (RadioButton) findViewById(R.id.radioSister);
        mainRad = (RadioButton) findViewById(R.id.radioMain);
        text =  (TextView) findViewById(R.id.textView);
    }
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.findFile:

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                //intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                startActivityForResult(intent, READ_REQUEST_CODE);
                break;

            case R.id.addFileData:
                Library.Type lib;
                if (sisterRad.isChecked())
                    lib = Library.Type.SISTER;
                else
                    lib = Library.Type.MAIN;
                controller.addFileData(file, lib);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData){

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            try {
                if (resultData != null) {
                    uri = resultData.getData();
                    fileText.append("Uri: " + uri.toString());
                    if (uri.toString().endsWith("json"))
                        file = new File("sendFile.json");
                    if (uri.toString().endsWith("xml"))
                        file = new File("sendFile.xml");
                    else
                        fileText.append("Incompatible File Type");

                }
            }catch(RuntimeException re){}
            try {
                readTextFromUri(uri);
            }catch(RuntimeException re){};
        }
    }

    private void readTextFromUri(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            PrintWriter outFile = new PrintWriter(file);

            while ((line = reader.readLine()) != null) {
                outFile.write(line);
            }
            inputStream.close();
            reader.close();
            outFile.close();

            return;
        } catch (IOException e) {
        }

    }


}