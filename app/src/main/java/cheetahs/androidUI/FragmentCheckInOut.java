package cheetahs.androidUI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import cheetahs.controller.Controller;
import cheetahs.library.Library;
import cheetahs.storage.Storage;

/**
 * FragmentCheckInOut provides user input for checking specific items in or out from the libraries.
 * editTextCardNum: input for the card # that will be associated with check-in or check-out
 * editTextItemID: input for the item's ID for check-in or check-out
 * radioMain and @radioSister: two radio button options associated with the items
 * textCheckInOut: TextView to display processing information to the user.
 * controller: controller object that holds library, member, and library item information and
 * performs related processing.
 */
public class FragmentCheckInOut extends Fragment implements View.OnClickListener {
    Controller controller = new Controller();
    View view;
    private EditText editTextCardNum, editTextItemId;
    private RadioButton radioMain, radioSister;
    private TextView textCheckInOut;

    /* Loads the controller object and then creates the view to include input for a card number,
    / item id, two radio button selections for the libraries options, and the buttons for check in
     and check out.
     @param savedInstanceState: loads a previous state if it was stored.
    */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_check_in_out, container, false);

        editTextCardNum = (EditText) view.findViewById(R.id.editTextCardNum);
        editTextItemId = (EditText) view.findViewById(R.id.editTextItemId);
        radioMain = (RadioButton) view.findViewById(R.id.radioMain);
        radioMain.setChecked(true);
        radioSister = (RadioButton) view.findViewById(R.id.radioSister);
        view.findViewById(R.id.btnCheckOut).setOnClickListener(this);
        view.findViewById(R.id.btnCheckIn).setOnClickListener(this);
        textCheckInOut = (TextView) view.findViewById(R.id.textCheckInOut);
        textCheckInOut.setMovementMethod(new ScrollingMovementMethod());
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        controller = Storage.loadController(getActivity().getExternalFilesDir(null).getPath() + "/");
    }

    /*
    onClick checks whether the user wants to work with the sister or main library, then whether they're
    trying to use the check out or check in button. The controller handles validation of the card
    number and item ID before attempting the check-in/check-out, and displays errors or successes
    to the TextView.
    @param view: either the check out or check in button that was clicked
     */
    @Override
    public void onClick(View view) {
        Library.Type lib;
        // set library type based on which radio button is selected
        if (radioSister.isChecked()) {
            lib = Library.Type.SISTER;
        } else {
            lib = Library.Type.MAIN;
        }
        // call appropriate controller method based on whether the user wants to do a check in or check out
        switch (view.getId()) {
            case R.id.btnCheckOut:
                textCheckInOut.append(controller.checkOut(Integer.parseInt(editTextCardNum.getText().toString().trim()), editTextItemId.getText().toString().trim(), lib));
                break;
            case R.id.btnCheckIn:
                textCheckInOut.append(controller.checkIn(editTextItemId.getText().toString().trim(), lib));
                break;
        }
    }
}
