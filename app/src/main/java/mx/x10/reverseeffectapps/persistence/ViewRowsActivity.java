package mx.x10.reverseeffectapps.persistence;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import mx.x10.reverseeffectapps.libpersistence.DatabaseCoordinator;
import mx.x10.reverseeffectapps.libpersistence.MyDataModel;

public class ViewRowsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rows);

        DatabaseCoordinator coordinator = new DatabaseCoordinator(this);
        final List<MyDataModel> dataSource = coordinator.getAllRows();

        ArrayAdapter<MyDataModel> adapter = new ArrayAdapter<MyDataModel>(this, R.layout.activity_view_rows, dataSource) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.row_item_database_row, null);
                }

                TextView titleTextView = (TextView)convertView.findViewById(R.id.text_view_title);
                titleTextView.setText(String.format(Locale.US, "%s (ID: %d)",
                        dataSource.get(position).getTitle(),
                        dataSource.get(position).getId()));

                TextView subtitleTextView = (TextView)convertView.findViewById(R.id.text_view_subtitle);
                subtitleTextView.setText(dataSource.get(position).getValue());

                return convertView;
            }
        };

        ListView databaseListView = (ListView)findViewById(R.id.list_view_database_row);
        databaseListView.setAdapter(adapter);
        databaseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog dialog = new AlertDialog.Builder(ViewRowsActivity.this)
                        .setTitle("ID: " + dataSource.get(position).getTitle())
                        .setMessage(dataSource.get(position).getTitle() + "\n" +
                                    dataSource.get(position).getValue())
                        .setPositiveButton("OK", null)
                        .create();
                dialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_rows_activity, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_dismiss) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
