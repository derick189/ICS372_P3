package cheetahs.androidUI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import cheetahs.library.Library;

public class DisplayLibraryActivity extends AppCompatActivity implements View.OnClickListener{
    private CheckBox chBoxBooks, chBoxCDs, chBoxDVDs, chBoxMags;
    private RadioButton mainRad, sisterRad;
    TextView displayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_library);
        chBoxBooks = (CheckBox) findViewById(R.id.chBoxBooks);
        chBoxBooks.setChecked(true);
        chBoxCDs = (CheckBox) findViewById(R.id.chBoxCDs);
        chBoxCDs.setChecked(true);
        chBoxDVDs = (CheckBox) findViewById(R.id.chBoxDVDs);
        chBoxDVDs.setChecked(true);
        chBoxMags = (CheckBox) findViewById(R.id.chBoxMags);
        chBoxMags.setChecked(true);
        ((Button) findViewById(R.id.displayLibrary)).setOnClickListener(this);
        mainRad = (RadioButton) findViewById(R.id.radioMain);
        mainRad.setChecked(true);
        sisterRad = (RadioButton) findViewById(R.id.radioSister);
        displayText = (TextView) findViewById(R.id.libraryDisplayText);
    }

    public void onClick(View view){
        int mask = getMask();
        displayText.append(String.valueOf(mask));
        Library.Type lib;
        if (sisterRad.isChecked())
            lib = Library.Type.SISTER;
        else
            lib = Library.Type.MAIN;
        String libData = MainActivity.controller.displayLibraryItems(mask, lib);
        displayText.append(libData);
    }

    private int getMask(){
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
