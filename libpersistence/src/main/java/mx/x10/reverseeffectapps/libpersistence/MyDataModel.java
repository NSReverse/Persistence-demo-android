package mx.x10.reverseeffectapps.libpersistence;

/**
 * Class MyDataModel -
 *
 * This class is a simple data model to represent data when records are being transacted back and
 * forth from the backend database.
 *
 * @author Robert Brown
 * Created 5/2/2017.
 */
public class MyDataModel {
    public static final String TABLE = "MyDataModel";

    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_VALUE = "value";

    private int id;
    private String title;
    private String value; // TODO: Think about making this generic.

    public MyDataModel() {
        this("", "");
    }

    public MyDataModel(String title, String value) {
        this(-1, title, value);
    }

    public MyDataModel(int id, String title, String value) {
        this.id = id;
        this.title = title;
        this.value = value;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setValue(String value) {
        // TODO: Think about making this generic.
        this.value = value;
    }

    public String getValue() {
        // TODO: Think about making this generic.
        return value;
    }
}
