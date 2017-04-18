package cheetahs.androidUI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddMemberActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextMemberName;
    private TextView textNewMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);
        editTextMemberName = (EditText) findViewById(R.id.editTextMemberName);
        ((Button) findViewById(R.id.btnAddMember)).setOnClickListener(this);
        textNewMember = (TextView) findViewById(R.id.textNewMember);
    }

    @Override
    public void onClick(View view) {
        textNewMember.append(MainActivity.controller.addMember(editTextMemberName.getText().toString().trim()));
    }
}
