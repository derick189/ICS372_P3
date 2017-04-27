package cheetahs.androidUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cheetahs.controller.Controller;
import cheetahs.storage.Storage;

/**
 * MainActivity is the start screen when the library app loads.
 * It has buttons that call other activities for the application to add files,
 * add members, change item status, display member's checked out items, and show
 * items in either library.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public Controller controller = new Controller();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.addFileData).setOnClickListener(this);
        findViewById(R.id.addMember).setOnClickListener(this);
        findViewById(R.id.checkInOut).setOnClickListener(this);
        findViewById(R.id.itemStatus).setOnClickListener(this);
        findViewById(R.id.membersItems).setOnClickListener(this);
        findViewById(R.id.displayLibrary).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        controller = Storage.loadController(getExternalFilesDir(null).getPath() + "/");
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.addFileData:
                intent = new Intent(this, AddFileActivity.class);
                startActivity(intent);
                break;
            case R.id.addMember:
                intent = new Intent(this, AddMemberActivity.class);
                startActivity(intent);
                break;
            case R.id.checkInOut:
                intent = new Intent(this, CheckInOutActivity.class);
                startActivity(intent);
                break;
            case R.id.itemStatus:
                intent = new Intent(this, ItemStatusActivity.class);
                startActivity(intent);
                break;
            case R.id.membersItems:
                intent = new Intent(this, MembersItemsActivity.class);
                startActivity(intent);
                break;
            case R.id.displayLibrary:
                intent = new Intent(this, DisplayLibraryActivity.class);
                startActivity(intent);
                break;
        }
    }
}
