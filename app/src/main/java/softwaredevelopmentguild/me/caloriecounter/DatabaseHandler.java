package softwaredevelopmentguild.me.caloriecounter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 4/18/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "calorieCounter";
    private static final String TABLE_EATEN = "eatenFood";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_CALORIES = "calories";
    private static final String KEY_DATE = "date";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_EATEN + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_CALORIES + " TEXT," + KEY_DATE + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EATEN);

        // Create tables again
        onCreate(db);
    }

    //Add new item
    public void addItem(FoodItem item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, item.getItemName());
        values.put(KEY_CALORIES, item.getCalories());
        values.put(KEY_DATE, item.getDate());

        db.insert(TABLE_EATEN, null, values);
        db.close();
    }

    //Getting a FoodItem by ID:
    public FoodItem getFoodItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EATEN, new String[] { KEY_ID,
                        KEY_NAME, KEY_CALORIES, KEY_DATE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        FoodItem item = new FoodItem(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                Integer.parseInt(cursor.getString(2)), cursor.getString(3));

        return item;
    }

    //Getting FoodItem list by Name:
    public List<FoodItem> getFoodItemsByName(String itemName) {
        List<FoodItem> items = new ArrayList<FoodItem>();

        String selectQuery = "SELECT  * FROM " + TABLE_EATEN + " WHERE " +
                KEY_NAME + " = " + itemName;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                //Create empty item, and populate it with tale date from row:
                FoodItem item = new FoodItem();
                item.setID(Integer.parseInt(cursor.getString(0)));
                item.setItemName(cursor.getString(1));
                item.setCalories(Integer.parseInt(cursor.getString(2)));
                item.setDate(cursor.getString(3));
                //Add item to list:
                items.add(item);
            } while (cursor.moveToNext());
        }

        return items;
    }

    //Getting FoodItem list by Date:
    public List<FoodItem> getFoodItemsByDate(String date) {
        List<FoodItem> items = new ArrayList<FoodItem>();

        String selectQuery = "SELECT  * FROM " + TABLE_EATEN + " WHERE " +
                KEY_DATE + " = " + date;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                //Create empty item, and populate it with tale date from row:
                FoodItem item = new FoodItem();
                item.setID(Integer.parseInt(cursor.getString(0)));
                item.setItemName(cursor.getString(1));
                item.setCalories(Integer.parseInt(cursor.getString(2)));
                item.setDate(cursor.getString(3));
                //Add item to list:
                items.add(item);
            } while (cursor.moveToNext());
        }

        return items;
    }

    public List<FoodItem> getAllFoodItems() {
        List<FoodItem> items = new ArrayList<FoodItem>();

        String selectQuery = "SELECT  * FROM " + TABLE_EATEN;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                //Create empty item, and populate it with tale date from row:
                FoodItem item = new FoodItem();
                item.setID(Integer.parseInt(cursor.getString(0)));
                item.setItemName(cursor.getString(1));
                item.setCalories(Integer.parseInt(cursor.getString(2)));
                item.setDate(cursor.getString(3));
                //Add item to list:
                items.add(item);
            } while (cursor.moveToNext());
        }

        return items;
    }

    public int getItemsCout() {
        String countQuery = "SELECT  * FROM " + TABLE_EATEN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    public int updateItem(FoodItem item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, item.getItemName());
        values.put(KEY_CALORIES, item.getCalories());
        values.put(KEY_DATE, item.getDate());

        //Update record:
        return db.update(TABLE_EATEN, values, KEY_ID + " = ?",
                new String[] { String.valueOf(item.getID()) });
    }

    public void deleteItem(FoodItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EATEN, KEY_ID + " = ?",
                new String[] { String.valueOf(item.getID()) });
        db.close();
    }
}
