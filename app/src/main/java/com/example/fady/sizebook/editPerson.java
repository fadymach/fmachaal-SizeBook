package com.example.fady.sizebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
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
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/** This class is used when the user chooses to edit a person in the list.
 * It reads list of people,
 */

public class editPerson extends AppCompatActivity {

    String FILENAME = "people.txt";
    ArrayList<Person> people_list = new ArrayList<>();
    Calendar newdate = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_person);

        readFile();


        Intent intent = getIntent();
        String temp = intent.getStringExtra("person");
        final int position = intent.getIntExtra("position", 0);
        Gson gson = new Gson();
        final Person edit_person = gson.fromJson(temp, Person.class);

        final EditText name = (EditText) findViewById(R.id.name);
        name.setText(edit_person.getName());
        final EditText neck = (EditText) findViewById(R.id.neck);
        neck.setText(edit_person.getNeck());
        final EditText bust = (EditText) findViewById(R.id.bust);
        bust.setText(edit_person.getBust());
        final EditText waist = (EditText) findViewById(R.id.waist);
        waist.setText(edit_person.getWaist());
        final EditText hip = (EditText) findViewById(R.id.hip);
        hip.setText(edit_person.getHip());
        final EditText inseam = (EditText) findViewById(R.id.inseam);
        inseam.setText(edit_person.getInseam());
        final EditText chest = (EditText) findViewById(R.id.chest);
        chest.setText(edit_person.getChest());
        final EditText comment = (EditText) findViewById(R.id.comment);
        comment.setText(edit_person.getComment());
        final TextView date_view = (TextView) findViewById(R.id.date_view);
        date_view.setText(edit_person.getDate());


        Button update_button = (Button) findViewById(R.id.button);
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().isEmpty()){
                    Toast.makeText(editPerson.this, "Name Field Is Mandatory", Toast.LENGTH_SHORT).show();
                }
                else {
                    edit_person.setName(name.getText().toString());
                    edit_person.setNeck(neck.getText().toString());
                    edit_person.setBust(bust.getText().toString());
                    edit_person.setWaist(waist.getText().toString());
                    edit_person.setHip(hip.getText().toString());
                    edit_person.setInseam(inseam.getText().toString());
                    edit_person.setChest(chest.getText().toString());
                    edit_person.setComment(comment.getText().toString());
                    String newdate = getNewDate();
                    edit_person.setDate(newdate);

                    people_list.add(position, edit_person);
                    people_list.remove(position + 1);
                    writeOverFile();
                    Toast.makeText(editPerson.this, "Updated!", Toast.LENGTH_SHORT).show();
                    finish();
                }
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


    String getNewDate(){
        DatePicker datepicker = (DatePicker) findViewById(R.id.datePicker2);
        Calendar cal = Calendar.getInstance();
        cal.set(datepicker.getYear(), datepicker.getMonth(), datepicker.getDayOfMonth());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(cal.getTime());
    }

}
