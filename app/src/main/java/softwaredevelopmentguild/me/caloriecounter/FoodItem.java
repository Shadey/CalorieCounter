package softwaredevelopmentguild.me.caloriecounter;

/**
 * Created by Matt on 4/18/2015.
 */
public class FoodItem {
    int id;
    String itemName, date;
    int calories;

    //Default constructor for if you don't plan on setting values in constructor.
    public FoodItem() {

    }

    //Use this constructor if you are creating a new item that has is not yet in the database.
    public FoodItem(String itemName, int calories, String date) {
        this.id = id;
        this.itemName = itemName;
        this.calories = calories;
        this.date = date;
    }

    public FoodItem(int id, String itemName, int calories, String date) {
        this.id = id;
        this.itemName = itemName;
        this.calories = calories;
        this.date = date;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
