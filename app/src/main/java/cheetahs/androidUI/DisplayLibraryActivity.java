package cheetahs.androidUI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import cheetahs.controller.Controller;
import cheetahs.library.Library;

public class DisplayLibraryActivity extends AppCompatActivity implements View.OnClickListener{

    private CheckBox chBoxBooks, chBoxCDs, chBoxDVDs, chBoxMags;
    private RadioButton mainRad, sisterRad;
    Controller controller = new Controller();
    TextView displayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_library);
        chBoxBooks = (CheckBox) findViewById(R.id.chBoxBooks);
        chBoxCDs = (CheckBox) findViewById(R.id.chBoxCDs);
        chBoxDVDs = (CheckBox) findViewById(R.id.chBoxDVDs);
        chBoxMags = (CheckBox) findViewById(R.id.chBoxMags);
        ((Button) findViewById(R.id.displayLibrary)).setOnClickListener(this);
        sisterRad = (RadioButton) findViewById(R.id.radioSister);
        mainRad = (RadioButton) findViewById(R.id.radioMain);
        displayText = (TextView) findViewById(R.id.libraryDisplayText);
    }

    public void onClick(View view){

        int mask = getMask();
        Library.Type lib;
        if (sisterRad.isChecked())
            lib = Library.Type.SISTER;
        else
            lib = Library.Type.MAIN;
        String libData = controller.displayLibraryItems(mask, lib);
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
