package cheetahs.androidUI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/*
AddMemberActivity allows the user to have a new library card # generated for a new library user.
It includes an EditText object to get the new library user's name, a button to generate the
new card # and save it to the controller, and a TextView to output submitted user's name
along with their new library card #. 
 */
public class AddMemberActivity extends AppCompatActivity implements View.OnClickListener {
    // EditText object for receiving new member name
    private EditText editTextMemberName;
    // TextView object to display output to user
    private TextView textNewMember;

    @Override
    // onCreate restores the previous state if there had been one for the activity.
    protected void onCreate(Bundle savedInstanceState) {
        // calls Activity's onCreate method
        super.onCreate(savedInstanceState);
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
        textNewMember.append(MainActivity.controller.addMember(editTextMemberName.getText().toString().trim()));
    }
}
