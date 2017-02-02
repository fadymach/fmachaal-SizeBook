package com.example.fady.sizebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used for viewing the list of Persons.
 * It shows the user a list of names that are in the list, allowing them to select a person
 */

public class viewEntries extends AppCompatActivity {

    String FILENAME = "people.txt";
    ArrayList<Person> people_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entries);


        final Intent details = new Intent(this, showDetails.class);

        ListView listview = (ListView) findViewById(R.id.listview);
        TextView textView = (TextView) findViewById(R.id.entry_count);

        readFile();
        DeletePerson();

        textView.setText(Integer.toString(people_list.size()));


        ArrayList<String> names = new ArrayList<>();

        for (int i = 0; i < people_list.size(); i++) {
            String temp_name = people_list.get(i).getName();
            names.add(temp_name);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Gson gson = new Gson();
                String person = gson.toJson(people_list.get(position));
                details.putExtra("person", person);
                details.putExtra("position", position);
                startActivity(details);
                finish();
            }
        });

    }

    void readFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type list_type = new TypeToken<ArrayList<Person>>(){}.getType();
            people_list = gson.fromJson(reader, list_type);
        } catch (FileNotFoundException e) {
            people_list = new ArrayList<>();
        }
    }

    void writeOverFile(){
        try {
            FileOutputStream fos = new FileOutputStream(new File(getFilesDir(), FILENAME));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(people_list, writer);
            writer.flush();
            writer.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //This function is only called when the delete option is selected from showDetails activity
    void DeletePerson(){
        Intent intent = getIntent();
        String delete_name = intent.getStringExtra("delete_name");
        if(delete_name != null){
            for(Person person : people_list){
                if(person.getName().equals(delete_name)){
                    people_list.remove(person);
                    writeOverFile();
                    break;
                }
            }
        }
    }
}