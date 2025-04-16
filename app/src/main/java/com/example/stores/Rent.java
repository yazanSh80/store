package com.example.stores;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Rent extends AppCompatActivity {

    SearchView search;
    Button rent, start, end, yrList;
    String selectedCategory;
    ArrayList<Item> items;
    ArrayAdapter<String> adapter;
    ArrayList<String> matchedItems;
    ListView listView;
    Calendar calendar;
    String selectedItem;
    Button logOut;
    public static ArrayList<Rented> rented;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.rent);

        // Initialize views
        search = findViewById(R.id.searchView);
        rent = findViewById(R.id.renting);
        start = findViewById(R.id.startDate);
        end = findViewById(R.id.endDate);
        listView = findViewById(R.id.listView);
        yrList = findViewById(R.id.list);
        calendar = Calendar.getInstance();
        logOut = findViewById(R.id.logOut2);

        // SharedPreferences setup
        preferences = getSharedPreferences("RentPrefs", MODE_PRIVATE);
        editor = preferences.edit();

        // Get selected category
        selectedCategory = getIntent().getStringExtra("SELECTED_CATEGORY");
        items = Activity2.itemList;
        rented = new ArrayList<>();

        Toast.makeText(this, "Category: " + selectedCategory, Toast.LENGTH_SHORT).show();

        // Load and display matching items
        matchedItems = findItems(selectedCategory);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, matchedItems);
        listView.setAdapter(adapter);

        // Load saved preferences
        String savedItem = preferences.getString("lastItem", null);
        String savedStart = preferences.getString("startDate", null);
        String savedEnd = preferences.getString("endDate", null);

        if (savedItem != null) {
            selectedItem = savedItem;
            Toast.makeText(this, "Last rented item: " + savedItem, Toast.LENGTH_SHORT).show();
        }
        if (savedStart != null) {
            start.setText(savedStart);
        }
        if (savedEnd != null) {
            end.setText(savedEnd);
        }

        // Handle item selection
        listView.setOnItemClickListener((parent, view, position, id) -> {
            selectedItem = adapter.getItem(position);
            Toast.makeText(Rent.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
        });

        // Search functionality
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<String> filtered = new ArrayList<>();
                for (String item : findItems(selectedCategory)) {
                    if (item.toLowerCase().contains(newText.toLowerCase())) {
                        filtered.add(item);
                    }
                }
                adapter.clear();
                adapter.addAll(filtered);
                adapter.notifyDataSetChanged();
                selectedItem = null; // Reset selection after filtering
                return true;
            }
        });

        // Start date picker
        start.setOnClickListener(v -> showDatePickerDialog(start));

        // End date picker
        end.setOnClickListener(v -> showDatePickerDialog(end));

        // Rent button logic
        rent.setOnClickListener(v -> {
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

            try {
                String startText = start.getText().toString();
                String endText = end.getText().toString();

                if (selectedItem == null || selectedItem.isEmpty()) {
                    Toast.makeText(Rent.this, "Please select an item from the list", Toast.LENGTH_SHORT).show();
                    return;
                }

                Date st = format.parse(startText);
                Date en = format.parse(endText);

                if (st != null && en != null) {
                    rented.add(theItemsRenting(selectedItem, st, en));
                    Toast.makeText(Rent.this, "Item rented: " + selectedItem, Toast.LENGTH_SHORT).show();

                    // Save preferences
                    editor.putString("lastItem", selectedItem);
                    editor.putString("startDate", startText);
                    editor.putString("endDate", endText);
                    editor.apply();

                    // Reset for next selection
                    selectedItem = null;
                    search.setQuery("", false);
                    adapter.clear();
                    adapter.addAll(findItems(selectedCategory));
                    adapter.notifyDataSetChanged();
                }
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(Rent.this, "Invalid date format", Toast.LENGTH_SHORT).show();
            }
        });

        // View rented list
        yrList.setOnClickListener(v -> {
            Intent intent = new Intent(Rent.this, tableActivity.class);
            startActivity(intent);
        });

        // Logout and clear preferences
        logOut.setOnClickListener(v -> {
            editor.clear();
            editor.apply();
            Intent intent = new Intent(Rent.this, MainActivity.class);
            startActivity(intent);
        });
    }

    public ArrayList<String> findItems(String cat) {
        ArrayList<String> it = new ArrayList<>();
        for (Item item : items) {
            if (item.getType().equalsIgnoreCase(cat)) {
                it.add(item.getName());
            }
        }
        return it;
    }

    private void showDatePickerDialog(Button button) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(
                Rent.this,
                (view, year1, month1, dayOfMonth) -> {
                    String date = (month1 + 1) + "/" + dayOfMonth + "/" + year1;
                    button.setText(date);
                },
                year, month, day
        );
        dpd.show();
    }

    // Find the rented item by name
    private Rented theItemsRenting(String name, Date startDate, Date endDate) {
        for (Item item : items) {
            if (name.equals(item.getName())) {
                return new Rented(item, startDate, endDate);
            }
        }
        return null;
    }
}
