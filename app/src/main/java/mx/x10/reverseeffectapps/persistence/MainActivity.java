package mx.x10.reverseeffectapps.persistence;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

import mx.x10.reverseeffectapps.libpersistence.DatabaseCoordinator;
import mx.x10.reverseeffectapps.libpersistence.MyDataModel;

public class MainActivity extends AppCompatActivity {

    DatabaseCoordinator coordinator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinator = new DatabaseCoordinator(this);

        Button testButton = (Button)findViewById(R.id.button_test_database);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testDatabase();

                Intent intent = new Intent(MainActivity.this, ViewRowsActivity.class);
                startActivity(intent);
            }
        });

        Button purgeButton = (Button)findViewById(R.id.button_purge_database);
        purgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                purgeDatabase();

                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Database Purged")
                        .setMessage("No rows remaining.")
                        .setPositiveButton("OK", null)
                        .create();
                dialog.show();
            }
        });
    }

    private void testDatabase() {
        coordinator.insertRow(new MyDataModel("Row 1", "Value 1"));
        coordinator.insertRow(new MyDataModel("Row 2", "Value 2"));
        coordinator.insertRow(new MyDataModel("Row 3", "Value 3"));

        List<MyDataModel> databaseRows = coordinator.getAllRows();

        for (MyDataModel currentRow : databaseRows) {
            System.out.println("Row Title: " + currentRow.getTitle());
            System.out.println("Row Value: " + currentRow.getValue());
            System.out.println("==========================================");
        }
    }

    private void purgeDatabase() {
        List<MyDataModel> databaseRows = coordinator.getAllRows();

        if (databaseRows != null) {
            for (MyDataModel currentRow : databaseRows) {
                coordinator.deleteRow(currentRow.getId());
                System.out.println("Row deleted: ID - " + currentRow.getId());
            }
        }

        databaseRows = coordinator.getAllRows();
        System.out.println("Rows Remaining: " + ((databaseRows != null)?databaseRows.size():0));
    }
}
