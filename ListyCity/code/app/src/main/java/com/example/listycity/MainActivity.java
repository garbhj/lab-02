package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    Button btnAdd, btnDel, btnSav;
    EditText editText;
    int selectedPosition = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnAdd = findViewById(R.id.add_button);
        btnDel = findViewById(R.id.del_button);
        btnSav = findViewById(R.id.save_button);

        editText = findViewById(R.id.enter_city_name);

        editText.setVisibility(View.GONE);
        btnSav.setVisibility(View.GONE);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Toggle @+id/enter_city_name + @+id/save_button visibility
                if (editText.isShown()) {
                    editText.setVisibility(View.GONE);
                    btnSav.setVisibility(View.GONE);
                } else {
                    editText.setVisibility(View.VISIBLE);
                    btnSav.setVisibility(View.VISIBLE);
                }
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (selectedPosition != -1 && selectedPosition < dataList.size()) {
                    dataList.remove(selectedPosition);
                    cityList.setItemChecked(selectedPosition, false);
                    cityList.clearChoices();

                    cityAdapter.notifyDataSetChanged();
                    selectedPosition = -1;
                }
            }
        });

        btnSav.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Save new entry in enter_city_name to arraylist
                String cityName = editText.getText().toString();
                if (!cityName.isEmpty()) {
                    dataList.add(cityName);
                    cityAdapter.notifyDataSetChanged(); // Updates the ListView

                    // Clear and hide fields after saving
                    editText.setText("");
                    editText.setVisibility(View.GONE);
                    btnSav.setVisibility(View.GONE);
                }
            }
        });

        cityList = findViewById(R.id.city_list);

        String []cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(
                this,
                R.layout.content,
                R.id.content_view,
                dataList);
        cityList.setAdapter(cityAdapter);

        // https://stackoverflow.com/questions/47620335/how-to-get-the-position-of-a-selected-item-in-listview-onitemclicklistener
        // https://stackoverflow.com/questions/4834750/how-to-get-the-selected-item-from-listview
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (selectedPosition == position) {
                    // Deselect if same item
                    cityList.setItemChecked(position, false);
                    selectedPosition = -1;
                } else {
                    // Select if new item
                    selectedPosition = position;
                    cityList.setItemChecked(position, true);
                }
            }
        });

    }
}