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
import cheetahs.storage.Storage;

/**
 * DisplayLibraryActivity lets the user view the status of all items from the main or sister libraries
 * and select to view only specific types (i.e. CDs, DVDs, magazines, and/or books).
 * mainRad and sisterRad are the selections for the libraries.
 * chBoxBooks, chBoxCDs, chBoxDVDs, and chBoxMags allow for viewing one or more of these types.
 * displayText shows the checked items for the selected library and their status.
 * controller object retrieves the library item information to be displayed.
 */
public class DisplayLibraryActivity extends AppCompatActivity implements View.OnClickListener {
    private RadioButton mainRad, sisterRad;
    private CheckBox chBoxBooks, chBoxCDs, chBoxDVDs, chBoxMags;
    TextView displayText;
    Controller controller = new Controller();

    @Override
    /* Loads the controller information and creates the screen with the two library button options,
    // check boxes, and the button to display the library buttons.
    @param savedInstanceState: loads a previous state if it was stored.
    */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
    protected void onStart() {
        super.onStart();
        controller = Storage.loadController(getExternalFilesDir(null).getPath() + "/");
    }

    @Override
    // Runs when the display library items is clicked. Gets information on which boxes are checked
    // via getMask method, and then displays those types for the library radio button selected.
    public void onClick(View view) {
        displayText.setText("");
        // Get which check boxes have been selected
        int mask = getMask();
        Library.Type lib;
        // get which library was selected so it can be passed to controller method
        if (sisterRad.isChecked())
            lib = Library.Type.SISTER;
        else
            lib = Library.Type.MAIN;
        // Retrieve the information for the types of items selected at the library
        String libData = controller.displayLibraryItems(mask, lib);
        // Display the items and their status
        displayText.append(libData + "\n");
    }

    // mask represents the selected checkboxes, and is used in a binary comparison
    // performed by the controller object to retrieve string representation of the items.
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
