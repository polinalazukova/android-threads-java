package com.example.task4;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] countriesArray = {"Бразилия", "Аргентина", "Колумбия", "Чили", "Уругвай"};

        ArrayList<String> countriesList = new ArrayList<>(Arrays.asList(countriesArray));

        countriesList.add("Перу");
        countriesList.add("Эквадор");
        countriesList.add("Венесуэла");

        countriesList.remove("Чили");

        Collections.sort(countriesList);
        int index = Collections.binarySearch(countriesList, "Перу");

        LinkedList<String> linkedList = new LinkedList<>(countriesList);
        linkedList.addFirst("Суринам");
        linkedList.addLast("Гайана");

        ListView listView = findViewById(R.id.countriesList);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                linkedList
        );

        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selected = linkedList.get(position);
            Toast.makeText(MainActivity.this, "Выбрано: " + selected, Toast.LENGTH_SHORT).show();
        });
    }
}