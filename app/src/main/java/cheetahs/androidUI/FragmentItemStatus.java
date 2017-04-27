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
import cheetahs.items.Item;
import cheetahs.library.Library;
import cheetahs.storage.Storage;

/**
 *
 */
public class FragmentItemStatus extends Fragment implements View.OnClickListener {
    Controller controller = new Controller();
    View view;
    private EditText editTextItemId;
    private RadioButton rbMain, rbSister, rbCheckStatus, rbCheckedIn, rbMissing, rbOverdue, rbReference, rbRemoved, rbShelving;
    private TextView textItemStatus;

    /*
    @param savedInstanceState: loads a previous state if it was stored.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_item_status, container, false);

        rbMain = (RadioButton) view.findViewById(R.id.rbMain);
        rbMain.setChecked(true);
        rbSister = (RadioButton) view.findViewById(R.id.rbSister);
        editTextItemId = (EditText) view.findViewById(R.id.editTextItemId);
        rbCheckStatus = (RadioButton) view.findViewById(R.id.rbCheckStatus);
        rbCheckStatus.setChecked(true);
        rbCheckedIn = (RadioButton) view.findViewById(R.id.rbCheckedIn);
        rbMissing = (RadioButton) view.findViewById(R.id.rbMissing);
        rbOverdue = (RadioButton) view.findViewById(R.id.rbOverdue);
        rbReference = (RadioButton) view.findViewById(R.id.rbReference);
        rbRemoved = (RadioButton) view.findViewById(R.id.rbRemoved);
        rbShelving = (RadioButton) view.findViewById(R.id.rbShelving);
        view.findViewById(R.id.btnItemStatus).setOnClickListener(this);
        textItemStatus = (TextView) view.findViewById(R.id.textItemStatus);
        textItemStatus.setMovementMethod(new ScrollingMovementMethod());
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        controller = Storage.loadController(getActivity().getExternalFilesDir(null).getPath() + "/");
    }

    @Override
    public void onClick(View view) {
        Library.Type library;
        if (rbSister.isChecked()) {
            library = Library.Type.SISTER;
        } else {
            library = Library.Type.MAIN;
        }
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
