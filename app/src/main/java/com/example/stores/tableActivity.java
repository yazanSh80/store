package com.example.stores;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class tableActivity extends AppCompatActivity {

    ListView list;
    ListView list2;
    Button delete;
    String selectedItem;
    ArrayAdapter<Item> boughtAdapter;
    ArrayAdapter<Rented> rentedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.thelists);

        // Initialize UI elements
        delete = findViewById(R.id.deleteRent);
        list = findViewById(R.id.listView);
        list2 = findViewById(R.id.listViewRent);

        // Get data
        ArrayList<Item> boughtList = ActivityShowing.bought;
        ArrayList<Rented> rentedList = Rent.rented;

        // Set adapters
        boughtAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, boughtList);
        list.setAdapter(boughtAdapter);

        rentedAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, rentedList);
        list2.setAdapter(rentedAdapter);

        // Handle selecting a rented item
        list2.setOnItemClickListener((parent, view, position, id) -> {
            Rented rented = rentedAdapter.getItem(position);
            if (rented != null) {
                selectedItem = rented.toString();
                Toast.makeText(tableActivity.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
            }
        });

        // Delete button click
        delete.setOnClickListener(v -> {
            if (selectedItem != null) {
                for (int i = 0; i < rentedList.size(); i++) {
                    if (rentedList.get(i).toString().equals(selectedItem)) {
                        rentedList.remove(i);
                        rentedAdapter.notifyDataSetChanged();
                        Toast.makeText(tableActivity.this, "Deleted: " + selectedItem, Toast.LENGTH_SHORT).show();
                        selectedItem = null;
                        break;
                    }
                }
            } else {
                Toast.makeText(tableActivity.this, "No item selected to delete", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
