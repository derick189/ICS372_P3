package cheetahs.androidUI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MembersItemsActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextMemberItems;
    private TextView textMemberItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_items);
        editTextMemberItems = (EditText) findViewById(R.id.editTextMemberItems);
        ((Button) findViewById(R.id.btnMemberItems)).setOnClickListener(this);
        textMemberItems = (TextView) findViewById(R.id.textMemberItems);
    }

    @Override
    public void onClick(View view) {
        textMemberItems.append(MainActivity.controller.displayMemberCheckedOutItems((Integer.parseInt(editTextMemberItems.getText().toString().trim()))));
    }
}
