package cheetahs.androidUI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import cheetahs.controller.Controller;
import cheetahs.items.Item;
import cheetahs.library.Library;

/**
 *
 */
public class ItemStatusActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextItemId;
    private RadioButton rbMain, rbSister, rbCheckStatus, rbCheckedIn, rbMissing, rbOverdue, rbReference, rbRemoved, rbShelving;
    private TextView textItemStatus;
    Controller controller;

    @Override
    /*
    @param savedInstanceState: loads a previous state if it was stored.
     */
    protected void onCreate(Bundle savedInstanceState) {
        // calls Activity's onCreate method
        super.onCreate(savedInstanceState);
        controller = (Controller) getIntent().getSerializableExtra("controller");
        controller.setSavePath(getExternalFilesDir(null).getPath() + "/");

        // Sets up the GUI
        setContentView(R.layout.activity_item_status);
        rbMain = (RadioButton) findViewById(R.id.rbMain);
        rbMain.setChecked(true);
        rbSister = (RadioButton) findViewById(R.id.rbSister);
        editTextItemId = (EditText) findViewById(R.id.editTextItemId);
        rbCheckStatus = (RadioButton) findViewById(R.id.rbCheckStatus);
        rbCheckStatus.setChecked(true);
        rbCheckedIn = (RadioButton) findViewById(R.id.rbCheckedIn);
        rbMissing = (RadioButton) findViewById(R.id.rbMissing);
        rbOverdue = (RadioButton) findViewById(R.id.rbOverdue);
        rbReference = (RadioButton) findViewById(R.id.rbReference);
        rbRemoved = (RadioButton) findViewById(R.id.rbRemoved);
        rbShelving = (RadioButton) findViewById(R.id.rbShelving);
        findViewById(R.id.btnItemStatus).setOnClickListener(this);
        textItemStatus = (TextView) findViewById(R.id.textItemStatus);
        textItemStatus.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    // When Change Item Status is clicked, it checks which library is selected, and then
    // tries to process the status change for the item ID in the item text box for the view.
    public void onClick(View view) {
        // Reference to the library the user wants to work with, and is passed to the
        // checkIn or checkOut methods of the controller
        Library.Type library;
        if (rbSister.isChecked()) {
            library = Library.Type.SISTER;
        } else {
            library = Library.Type.MAIN;
        }
        // Checks which radio button is currently selected, and calls the appropriate method in
        // controller to update the item status. Also returns a string to print whether it was
        // successful or failed.
        if (rbCheckStatus.isChecked()) {
            textItemStatus.append(controller.changeItemStatus(editTextItemId.getText().toString().trim(), Item.Status.CHECK_STATUS, library));
        } else if (rbCheckedIn.isChecked()) {
            textItemStatus.append(controller.changeItemStatus(editTextItemId.getText().toString().trim(), Item.Status.CHECKED_IN, library));
        } else if (rbMissing.isChecked()) {
            textItemStatus.append(controller.changeItemStatus(editTextItemId.getText().toString().trim(), Item.Status.MISSING, library));
        } else if (rbOverdue.isChecked()) {
            textItemStatus.append(controller.changeItemStatus(editTextItemId.getText().toString().trim(), Item.Status.OVERDUE, library));
        } else if (rbReference.isChecked()) {
            textItemStatus.append(controller.changeItemStatus(editTextItemId.getText().toString().trim(), Item.Status.REFERENCE_ONLY, library));
        } else if (rbRemoved.isChecked()) {
            textItemStatus.append(controller.changeItemStatus(editTextItemId.getText().toString().trim(), Item.Status.REMOVED_FROM_CIRCULATION, library));
        } else if (rbShelving.isChecked()) {
            textItemStatus.append(controller.changeItemStatus(editTextItemId.getText().toString().trim(), Item.Status.SHELVING, library));
        }
    }
}
