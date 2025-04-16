package com.example.stores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ActivityShowing extends AppCompatActivity {
    ArrayList<Item> items;
    String selectedCategory;
    TextView about;
    RadioButton buy;
    RadioButton ignore;
    Button btn;
    String chosen = null;
    Button yrList;
    public static ArrayList<Item> bought = new ArrayList<>();
    int prices = 0;
    TextView res;
    Spinner spn;
    Button logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.showing);

        spn = findViewById(R.id.spinnerItems);
        buy = findViewById(R.id.buy);
        ignore = findViewById(R.id.ignore);
        btn = findViewById(R.id.btn_next2);
        about = findViewById(R.id.itemInfo);
        yrList = findViewById(R.id.yourList);
        res = findViewById(R.id.res);
        logOut = findViewById(R.id.logOut);

        selectedCategory = getIntent().getStringExtra("SELECTED_CATEGORY");
        Toast.makeText(this, "Selected category: " + selectedCategory, Toast.LENGTH_SHORT).show();

        items = Activity2.itemList;
        TextView searchIn = findViewById(R.id.search);
        searchIn.setText(selectedCategory != null ? "Search in " + selectedCategory : "No category selected.");

        ArrayList<String> filteredItems = findItems(selectedCategory);
        filteredItems.add(0, "choose");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, filteredItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(adapter);

        spn.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                chosen = parent.getItemAtPosition(position).toString();
                if (!chosen.equals("choose")) {
                    about.setText(searchItem(chosen));
                } else {
                    about.setText("");
                    chosen = null;
                }
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
                chosen = null;
            }
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityShowing.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btn.setOnClickListener(e -> {
            if (chosen == null) {
                Toast.makeText(this, "Please select an item first.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (buy.isChecked()) {
                boolean success = buying(chosen);
                if (success) {
                    res.setVisibility(View.VISIBLE);
                    res.setText("Item bought: " + chosen + "\nTotal price = " + prices);
                    about.setText(searchItem(chosen)); // update quantity shown
                } else {
                    Toast.makeText(this, "This item is out of stock!", Toast.LENGTH_SHORT).show();
                }
            } else if (ignore.isChecked()) {
                spn.setSelection(0); // Back to "choose"
                about.setText("");
            } else {
                Toast.makeText(this, "Please choose Buy or Ignore", Toast.LENGTH_SHORT).show();
            }
        });

        yrList.setOnClickListener(v -> {
            Toast.makeText(this, "Opening your bought items list...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ActivityShowing.this, tableActivity.class);
            intent.putExtra("SOURCE", "BUY");
            startActivity(intent);
        });
    }

    public ArrayList<String> findItems(String cat) {
        ArrayList<String> it = new ArrayList<>();
        for (Item item : items) {
            if (item.getType().equals(cat)) {
                it.add(item.getName());
            }
        }
        return it;
    }

    public boolean buying(String chs) {
        for (Item item : items) {
            if (item.getName().equals(chs)) {
                if (item.getQuantity() > 0) {
                    bought.add(item);
                    prices += item.getPrice();
                    item.setQuantity(item.getQuantity() - 1); // Reduce quantity
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public String searchItem(String chs) {
        for (Item item : items) {
            if (chs.equals(item.getName())) {
                return item.toString() + "\nQuantity left: " + item.getQuantity();
            }
        }
        return "";
    }
}
