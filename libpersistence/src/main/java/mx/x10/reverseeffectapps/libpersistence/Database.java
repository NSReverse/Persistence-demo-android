package mx.x10.reverseeffectapps.libpersistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Class Database -> SQLiteOpenHelper -
 *
 * This class acts as the very basic operations of the backend. Creating an instance of this
 * subclass also provides backend query methods that can be used by another class.
 *
 * @author Robert Brown
 * Created 5/2/2017.
 */
class Database extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "appdatabase.db";

    /**
     * Constructor Database(Context) -
     *
     * This is the basic constructor to create an instance of a Database object.
     *
     * @param context A Context to create/open a new database with.
     */
    Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Overridden Method onCreate(SQLiteDatabase) -
     *
     * This method simply creates a table if it doesn't exist.
     *
     * @param db A SQLiteDatabase object to perform operations on.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + MyDataModel.TABLE + "(" +
                MyDataModel.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MyDataModel.KEY_TITLE + " TEXT, " +
                MyDataModel.KEY_VALUE + " TEXT);";

        db.execSQL(query);
    }

    /**
     * Overridden Method onUpgrade(SQLiteDatabase, int, int) -
     *
     * This method handles when the backend database changes versions. This needs better handling
     * as in it needs to get all of the rows, update the table, and then repopulate the rows.
     *
     * @param db A SQLiteDatabase object to perform operations on.
     * @param oldVersion An int representing the previous version of the database.
     * @param newVersion An int representing the new version of the database.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO: Properly handle this. Do not just throw away data.
        db.execSQL("DROP TABLE IF EXISTS " + MyDataModel.TABLE);
    }
}
