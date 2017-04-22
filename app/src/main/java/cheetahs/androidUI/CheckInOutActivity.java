package cheetahs.androidUI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import cheetahs.controller.Controller;
import cheetahs.library.Library;

public class CheckInOutActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextCardNum, editTextItemId;
    private RadioButton radioMain, radioSister;
    private TextView textCheckInOut;
    Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = (Controller) getIntent().getSerializableExtra("controller");
        controller.setSavePath(getExternalFilesDir(null).getPath() + "/");

        setContentView(R.layout.activity_check_in_out);
        editTextCardNum = (EditText) findViewById(R.id.editTextCardNum);
        editTextItemId = (EditText) findViewById(R.id.editTextItemId);
        radioMain = (RadioButton) findViewById(R.id.radioMain);
        radioMain.setChecked(true);
        radioSister = (RadioButton) findViewById(R.id.radioSister);
        findViewById(R.id.btnCheckOut).setOnClickListener(this);
        findViewById(R.id.btnCheckIn).setOnClickListener(this);
        textCheckInOut = (TextView) findViewById(R.id.textCheckInOut);
        textCheckInOut.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public void onClick(View view) {
        Library.Type lib;
        if (radioSister.isChecked()) {
            lib = Library.Type.SISTER;
        } else {
            lib = Library.Type.MAIN;
        }
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
