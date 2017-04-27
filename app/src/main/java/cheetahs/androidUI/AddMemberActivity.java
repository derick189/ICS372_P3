package cheetahs.androidUI;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cheetahs.controller.Controller;

/**
 * AddMemberActivity allows the user to have a new library card # generated for a new library user.
 * It includes an EditText object to get the new library user's name, a button to generate the
 * new card # and save it to the controller, and a TextView to output submitted user's name
 * along with their new library card #.
 */
public class AddMemberActivity extends AppCompatActivity implements View.OnClickListener {
    // EditText object for receiving new member name
    private EditText editTextMemberName;
    // TextView object to display output to user
    private TextView textNewMember;
    // Controller object that handles adding the member to the MemberList
    Controller controller;

    @Override
    // onCreate restores the previous state if there had been one for the activity.
    protected void onCreate(Bundle savedInstanceState) {
        // calls Activity's onCreate method
        super.onCreate(savedInstanceState);
        controller = (Controller) getIntent().getSerializableExtra("controller");
        controller.setSavePath(getExternalFilesDir(null).getPath() + "/");

        // Load the layout for the activity_add_member from the XML (\res\layout\activity_add_member)
        setContentView(R.layout.activity_add_member);
        // Make the editTextMemberName TextView editable for user input
        editTextMemberName = (EditText) findViewById(R.id.editTextMemberName);
        // Sets an action listener for when the Add Member button is clicked
        findViewById(R.id.btnAddMember).setOnClickListener(this);
        // Adds the TextView that will display information to the user when a new member is added
        textNewMember = (TextView) findViewById(R.id.textNewMember);
        // Allows the textNewMember view box to scroll if it goes over the length of the view
        textNewMember.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    // onClick takes the current view and passes the name entered in the EditText object, and passes
    // it to the MainActivity's controller object to be added
    public void onClick(View view) {
        // Check if Member Name is empty - don't create a new member from nothing.
        if (editTextMemberName.getText().toString().matches("")) {
            // Nothing added in EditText - tell user we need something for name
            AlertDialog.Builder builder = new AlertDialog.Builder(AddMemberActivity.this);
            // Message to user
            builder.setMessage("Name cannot be empty. Enter a name before pushing Add Member.");
            builder.setTitle("Error");
            // Make it easier for the user to return to the activity by including a negative button
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface d, int arg1) {
                    d.cancel();
                };
            });
            builder.show();

            textNewMember.append("Enter a new member name before clicking Add Member.");
            return;
        }
        // If input in member name EditText, create the new member in Controller and output the new
        // member's name and new ID to the textNewMember TextView
        textNewMember.append(controller.addMember(editTextMemberName.getText().toString().trim()));
    }
}
