package cheetahs.androidUI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cheetahs.controller.Controller;

/**
 *
 */
public class MembersItemsActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextMemberItems;
    private TextView textMemberItems;
    Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = (Controller) getIntent().getSerializableExtra("controller");
        controller.setSavePath(getExternalFilesDir(null).getPath() + "/");

        setContentView(R.layout.activity_members_items);
        editTextMemberItems = (EditText) findViewById(R.id.editTextMemberItems);
        findViewById(R.id.btnAddMember).setOnClickListener(this);
        textMemberItems = (TextView) findViewById(R.id.textMemberItems);
        textMemberItems.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public void onClick(View view) {
        textMemberItems.append(controller.displayMemberCheckedOutItems((Integer.parseInt(editTextMemberItems.getText().toString().trim()))));
    }
}
