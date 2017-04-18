package cheetahs.androidUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import cheetahs.controller.Controller;
import cheetahs.storage.Storage;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static Controller controller = new Controller();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((Button) findViewById(R.id.addFileData)).setOnClickListener(this);
        ((Button) findViewById(R.id.addMember)).setOnClickListener(this);
        ((Button) findViewById(R.id.checkInOut)).setOnClickListener(this);
        ((Button) findViewById(R.id.changeCheckItemStatus)).setOnClickListener(this);
        ((Button) findViewById(R.id.usersCheckedOut)).setOnClickListener(this);
        ((Button) findViewById(R.id.displayLibrary)).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        controller = Storage.loadController();
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

            case R.id.changeCheckItemStatus:
                intent = new Intent(this, ItemStatusActivity.class);
                startActivity(intent);
                break;

            case R.id.usersCheckedOut:
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
