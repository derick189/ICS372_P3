package cheetahs.androidUI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cheetahs.controller.Controller;

/**
 * MembersItemsActivity lets the user enter a library card #, and then displays any items
 * that are currently associated with the number.
 * editTextMemberItems allows input for the card #.
 * textMemberItems displays the return items to the user.
 * controller contains the member and item information.
 */
public class MembersItemsActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextMemberItems;
    private TextView textMemberItems;
    Controller controller;

    @Override
    /*
    Loads the controller object, displays the edittext box, and the button to display the items.
    @param savedInstanceState: loads a previous state if it was stored.
     */
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
