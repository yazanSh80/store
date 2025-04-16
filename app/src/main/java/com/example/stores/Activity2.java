package com.example.stores;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class Activity2 extends AppCompatActivity {

    public static ArrayList<Item> itemList = new ArrayList<>(); //the item list
    public static ArrayList<String> category; // the category list
    String selectedCategory; // to use it in the other classes
    Button insertScreen; // the button that goes to the inserting screen
    Button btn_action; // the button that goes to rent or buy
    Switch switchMode; // switch between rent and buy
    Spinner spn; // spinner to choose the category

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private static final String PREF_NAME = "StorePrefs";
    private static final String KEY_SWITCH_MODE = "switch_mode";
    private static final String KEY_SELECTED_SPINNER = "spinner_selection";
    Button logOut; // to go to main activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        editor = preferences.edit();

        btn_action = findViewById(R.id.btn_action);
        switchMode = findViewById(R.id.switchMode);
        insertScreen = findViewById(R.id.insertItems);
        spn = findViewById(R.id.SpinnerCategory);
        logOut =findViewById(R.id.logOut1);
        fillTheList();

        Toast.makeText(this, "Activity2 opened!", Toast.LENGTH_SHORT).show();

        ArrayList<String> categories = findCategories(itemList);
        categories.add(0, "choose");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(adapter);

        // Load saved preferences
        boolean savedSwitchState = preferences.getBoolean(KEY_SWITCH_MODE, false);
        int savedSpinnerPosition = preferences.getInt(KEY_SELECTED_SPINNER, 0);
        switchMode.setChecked(savedSwitchState);
        spn.setSelection(savedSpinnerPosition);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity2.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btn_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCategory = spn.getSelectedItem().toString();
                if (selectedCategory != null && !selectedCategory.equals("choose")) {
                    // Save preferences
                    editor.putBoolean(KEY_SWITCH_MODE, switchMode.isChecked());
                    editor.putInt(KEY_SELECTED_SPINNER, spn.getSelectedItemPosition());
                    editor.apply();

                    Intent intent;
                    if (switchMode.isChecked()) {
                        intent = new Intent(Activity2.this, Rent.class);
                    } else {
                        intent = new Intent(Activity2.this, ActivityShowing.class);
                    }
                    intent.putExtra("SELECTED_CATEGORY", selectedCategory);
                    startActivity(intent);
                } else {
                    Toast.makeText(Activity2.this, "Fill the spinner", Toast.LENGTH_SHORT).show();
                }
            }
        });

        insertScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity2.this, Inserting.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main2), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private ArrayList<String> findCategories(ArrayList<Item> list) {
        category = new ArrayList<>();
        for (Item item : list) {
            if (!category.contains(item.getType())) {
                category.add(item.getType());
            }
        }
        return category;
    }

    public void fillTheList() {
        if (!itemList.isEmpty()) return; // prevent duplicating
        itemList.add(new Item("Shoes", "001", "Clothes", "Red sneakers, size 42", "Nike", 10, 300));
        itemList.add(new Item("Laptop", "002", "Electronics", "i7, 16GB RAM, 512GB SSD", "HP", 5, 4000));
        itemList.add(new Item("Chair", "003", "Furniture", "Ergonomic, black mesh", "IKEA", 8, 500));
        itemList.add(new Item("Watch", "004", "Accessories", "Stainless steel, waterproof", "Casio", 12, 200));
        itemList.add(new Item("Blender", "005", "Kitchenware", "700W motor, glass jar", "Philips", 6, 220));
        itemList.add(new Item("Book", "006", "Books", "The Alchemist, paperback", "Harper", 15, 50));
        itemList.add(new Item("T-shirt", "007", "Clothes", "White, cotton, size L", "Adidas", 20, 100));
        itemList.add(new Item("Smartphone", "008", "Electronics", "128GB, AMOLED display", "Samsung", 7, 3500));
        itemList.add(new Item("Headphones", "009", "Electronics", "Wireless, noise-cancelling", "Sony", 9, 650));
        itemList.add(new Item("Notebook", "010", "Stationery", "200 pages, A5 size", "Classmate", 25, 10));
        itemList.add(new Item("Sofa", "011", "Furniture", "3-seater, leather", "Home Center", 3, 2500));
        itemList.add(new Item("Backpack", "012", "Accessories", "Waterproof, 30L", "North Face", 13, 180));
        itemList.add(new Item("Fan", "013", "Home Appliance", "16-inch, 3-speed", "LG", 10, 250));
        itemList.add(new Item("Microwave", "014", "Kitchenware", "900W, digital", "Samsung", 4, 600));
        itemList.add(new Item("Puzzle", "015", "Toys", "1000-piece scenic", "Ravensburger", 10, 70));
        itemList.add(new Item("Printer", "016", "Electronics", "All-in-one, WiFi", "Canon", 6, 800));
        itemList.add(new Item("Perfume", "017", "Cosmetics", "50ml, floral", "Dior", 18, 400));
        itemList.add(new Item("Camera", "018", "Electronics", "DSLR, 24MP", "Canon", 2, 2700));
        itemList.add(new Item("Jeans", "019", "Clothes", "Blue, slim fit, size 32", "Levi's", 14, 160));
        itemList.add(new Item("Desk Lamp", "020", "Furniture", "LED, adjustable", "Xiaomi", 10, 90));
        itemList.add(new Item("Tablet", "021", "Electronics", "10.1-inch, 64GB", "Lenovo", 5, 1200));
        itemList.add(new Item("Sunglasses", "022", "Accessories", "UV400 protection", "Ray-Ban", 11, 350));
        itemList.add(new Item("Kettle", "023", "Kitchenware", "1.5L, stainless", "Tefal", 8, 190));
        itemList.add(new Item("Pen", "024", "Stationery", "Gel, black ink", "Pilot", 40, 5));
        itemList.add(new Item("Jacket", "025", "Clothes", "Winter, waterproof", "Columbia", 6, 450));
    }
}
