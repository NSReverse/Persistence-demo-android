package mx.x10.reverseeffectapps.libpersistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Class DatabaseCoordinator -
 *
 * This class interacts with the backend to perform CRUD operations on the database.
 *
 * @author Robert Brown
 * Created 5/2/2017.
 */
public class DatabaseCoordinator {
    private Database database;

    /**
     * Constructor DatabaseCoordinator(Context) -
     *
     * This is the basic constructor to create a DatabaseCoordinator object.
     *
     * @param context A Context to pass to the database backend.
     */
    public DatabaseCoordinator(Context context) {
        database = new Database(context);
    }

    /**
     * Method insertRow(MyDataModel) -
     *
     * This method takes in a MyDataModel object to insert into the database as a row.
     *
     * @param row A MyDataModel object to insert into the database.
     */
    public void insertRow(MyDataModel row) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MyDataModel.KEY_TITLE, row.getTitle());
        values.put(MyDataModel.KEY_VALUE, row.getValue());

        db.insert(MyDataModel.TABLE, null, values);

        if (db.isOpen()) {
            db.close();
        }
    }

    /**
     * Method deleteRow(int) -
     *
     * This method deletes a row from the database by an int-represented row id.
     *
     * @param id An int representing the id of the row to delete.
     */
    public void deleteRow(int id) {
        SQLiteDatabase db = database.getWritableDatabase();
        db.delete(MyDataModel.TABLE, MyDataModel.KEY_ID + " =? ", new String[] { String.valueOf(id) });

        if (db.isOpen()) {
            db.close();
        }
    }

    /**
     * Method updateRow(MyDataModel) -
     *
     * This method updates a row in the database with data represented by a MyDataModel object.
     *
     * @param row A MyDataModel object whose data will be used to update an existing row.
     */
    public void updateRow(MyDataModel row) {
        SQLiteDatabase db = database.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MyDataModel.KEY_TITLE, row.getTitle());
        values.put(MyDataModel.KEY_VALUE, row.getValue());

        db.update(MyDataModel.TABLE, values, MyDataModel.KEY_ID + " =? ",
                  new String[] { String.valueOf(row.getId()) });

        if (db.isOpen()) {
            db.close();
        }
    }

    /**
     * Method getAllRows() -
     *
     * This method gets all the rows in the database and returns a List containing the records
     * retrieved.
     *
     * @return A List containing MyDataModel objects representing all of the rows in a database. If
     * there are no records, then null is returned.
     */
    public List<MyDataModel> getAllRows() {
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "SELECT * FROM " + MyDataModel.TABLE;

        List<MyDataModel> temp = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                MyDataModel currentRow = new MyDataModel();
                currentRow.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(MyDataModel.KEY_ID))));
                currentRow.setTitle(cursor.getString(cursor.getColumnIndex(MyDataModel.KEY_TITLE)));
                currentRow.setValue(cursor.getString(cursor.getColumnIndex(MyDataModel.KEY_VALUE)));

                temp.add(currentRow);
            }
            while (cursor.moveToNext());
        }

        if (db.isOpen()) {
            db.close();
        }

        if (!cursor.isClosed()) {
            cursor.close();
        }

        if (temp.size() == 0) {
            return null;
        }
        else {
            return temp;
        }
    }

    /**
     * Method getRowById(int) -
     *
     * This method gets a row by an int-represented id and the row is
     * returned as a MyDataModel object.
     *
     * @param id An int representing the id of the row.
     * @return A MyDataModel object representing the data contained in the row. If not found, returns null.
     */
    @Nullable
    public MyDataModel getRowById(int id) {
        List<MyDataModel> databaseRepresentation = getAllRows();

        if (databaseRepresentation != null) {
            for (MyDataModel currentRow : databaseRepresentation) {
                if (currentRow.getId() == id) {
                    return currentRow;
                }
            }
        }

        return null;
    }
}
