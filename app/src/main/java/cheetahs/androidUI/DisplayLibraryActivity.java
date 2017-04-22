package cheetahs.androidUI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import cheetahs.controller.Controller;
import cheetahs.library.Library;

public class DisplayLibraryActivity extends AppCompatActivity implements View.OnClickListener {
    private RadioButton mainRad, sisterRad;
    private CheckBox chBoxBooks, chBoxCDs, chBoxDVDs, chBoxMags;
    TextView displayText;
    Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = (Controller) getIntent().getSerializableExtra("controller");
        controller.setSavePath(getExternalFilesDir(null).getPath() + "/");

        setContentView(R.layout.activity_display_library);
        mainRad = (RadioButton) findViewById(R.id.radioMain);
        mainRad.setChecked(true);
        sisterRad = (RadioButton) findViewById(R.id.radioSister);
        chBoxBooks = (CheckBox) findViewById(R.id.chBoxBooks);
        chBoxBooks.setChecked(true);
        chBoxCDs = (CheckBox) findViewById(R.id.chBoxCDs);
        chBoxCDs.setChecked(true);
        chBoxDVDs = (CheckBox) findViewById(R.id.chBoxDVDs);
        chBoxDVDs.setChecked(true);
        chBoxMags = (CheckBox) findViewById(R.id.chBoxMags);
        chBoxMags.setChecked(true);
        findViewById(R.id.btnDisplayLibrary).setOnClickListener(this);
        displayText = (TextView) findViewById(R.id.textDisplayLibrary);
        displayText.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public void onClick(View view) {
        int mask = getMask();
        Library.Type lib;
        if (sisterRad.isChecked())
            lib = Library.Type.SISTER;
        else
            lib = Library.Type.MAIN;
        String libData = controller.displayLibraryItems(mask, lib);
        displayText.append(libData + "\n");
    }

    private int getMask() {
        int fmask = 0;
        if (chBoxBooks.isChecked())
            fmask = fmask + 1;
        if (chBoxCDs.isChecked())
            fmask = fmask + 2;
        if (chBoxDVDs.isChecked())
            fmask = fmask + 4;
        if (chBoxMags.isChecked())
            fmask = fmask + 8;
        return fmask;
    }
}
