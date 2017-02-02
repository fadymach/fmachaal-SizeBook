package com.example.fady.sizebook;

import android.annotation.TargetApi;
import android.icu.text.DateFormat;
import android.icu.text.DecimalFormat;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Target;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * This class is used for entering a new person into the list of persons.
 * The user only needs to add a name, all other fields are optional.
 */

public class addEntries extends AppCompatActivity {

    String FILENAME = "people.txt";
    Person person = new Person();
    ArrayList<Person> people_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entries);

        final Button add_button = (Button) findViewById(R.id.button);

        readFile();

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (createPerson()) {
                    addEntry(person);
                    Toast.makeText(addEntries.this, "Added!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }


    boolean createPerson() {
        String name = ((EditText) findViewById(R.id.name)).getText().toString();
        if (name.isEmpty()) {
            Toast.makeText(this, "Name Field Is Mandatory", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            person.setName(name);
            String neck = ((EditText) findViewById(R.id.neck)).getText().toString();
            person.setNeck(neck);
            String bust = ((EditText) findViewById(R.id.bust)).getText().toString();
            person.setBust(bust);
            String waist = ((EditText) findViewById(R.id.waist)).getText().toString();
            person.setWaist(waist);
            String hip = ((EditText) findViewById(R.id.hip)).getText().toString();
            person.setHip(hip);
            String inseam = ((EditText) findViewById(R.id.inseam)).getText().toString();
            person.setInseam(inseam);
            String chest = ((EditText) findViewById(R.id.chest)).getText().toString();
            person.setChest(chest);
            String comment = ((EditText) findViewById(R.id.comment)).getText().toString();
            person.setComment(comment);
            DatePicker datepicker = (DatePicker) findViewById(R.id.datePicker);
            Calendar cal = Calendar.getInstance();
            cal.set(datepicker.getYear(), datepicker.getMonth(), datepicker.getDayOfMonth());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String date = format.format(cal.getTime());
            person.setDate(date);
            return true;
        }
    }


    /*Used http://stackoverflow.com/questions/30411679/android-studio-open-failed-erofs-read-only-file-system-when-creating-a-file
    for information on creating a file for storage. Found on Jan.27, 2017
     */

    void addEntry(Person person) {
        try {
            people_list.add(person);
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
    /*TODO limit entries of int values to decimal place (reference assignemnt for .** or .*
        Make sure to do it in the editPerson as well
     */


}
