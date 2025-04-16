package com.example.stores;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Inserting extends AppCompatActivity {
    EditText name;
    EditText brand;
    EditText quantity;
    EditText desc;
    EditText price;
    Spinner spn;
    Button btn;
    ArrayList<String> cat;
    ArrayList<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.insert);

        name = findViewById(R.id.name);
        cat = Activity2.category;
        brand = findViewById(R.id.brand);
        quantity = findViewById(R.id.quantity);
        desc = findViewById(R.id.desc);
        price = findViewById(R.id.price);
        spn = findViewById(R.id.spn3);
        btn = findViewById(R.id.buttonIn);
        items = Activity2.itemList;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cat);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(adapter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameStr = name.getText().toString().trim();
                String brandStr = brand.getText().toString().trim();
                String qtyStr = quantity.getText().toString().trim();
                String descStr = desc.getText().toString().trim();
                String priceStr = price.getText().toString().trim();

                // Check if any field is empty
                if (nameStr.isEmpty() || brandStr.isEmpty() || qtyStr.isEmpty() ||
                        descStr.isEmpty() || priceStr.isEmpty()) {
                    Toast.makeText(Inserting.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    int qty = Integer.parseInt(qtyStr);
                    int prc = Integer.parseInt(priceStr);

                    if (qty <= 0 || prc <= 0) {
                        Toast.makeText(Inserting.this, "Quantity and price must be greater than 0.", Toast.LENGTH_SHORT).show();
                    } else {
                        addToList();
                        clearFields();
                        Toast.makeText(Inserting.this, "Item added successfully!", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(Inserting.this, "Please enter valid numbers for quantity and price.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void addToList() {
        String name1 = name.getText().toString();
        String category = spn.getSelectedItem().toString();
        String brandes = brand.getText().toString();
        String describe = desc.getText().toString();
        int quantities = Integer.parseInt(quantity.getText().toString());
        int prices = Integer.parseInt(price.getText().toString());
        Item item = new Item(name1, createCode(), category, describe, brandes, quantities, prices);
        items.add(item);
    }

    public String createCode() {
        return String.valueOf(items.size() + 1);
    }

    private void clearFields() {
        name.getText().clear();
        brand.getText().clear();
        quantity.getText().clear();
        desc.getText().clear();
        price.getText().clear();
    }
}