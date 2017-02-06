package com.example.fady.sizebook;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;


public class showDetails extends AppCompatActivity {

    String person_name;
    String person;
    Person detailed_person;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        TextView name = (TextView) findViewById(R.id.name);
        TextView neck = (TextView) findViewById(R.id.neck);
        TextView bust = (TextView) findViewById(R.id.bust);
        TextView waist = (TextView) findViewById(R.id.waist);
        TextView hip = (TextView) findViewById(R.id.hip);
        TextView inseam = (TextView) findViewById(R.id.inseam);
        TextView chest = (TextView) findViewById(R.id.chest);
        TextView comment = (TextView) findViewById(R.id.comment);
        TextView date = (TextView) findViewById(R.id.date);



        Intent intent = getIntent();
        person = intent.getStringExtra("person");
        position = intent.getIntExtra("position", 0);
        Gson gson = new Gson();
        detailed_person = gson.fromJson(person, Person.class);

        name.setText(detailed_person.getName());
        neck.setText(detailed_person.getNeck());
        bust.setText(detailed_person.getBust());
        waist.setText(detailed_person.getWaist());
        hip.setText(detailed_person.getHip());
        inseam.setText(detailed_person.getInseam());
        chest.setText(detailed_person.getChest());
        comment.setText(detailed_person.getComment());
        date.setText(detailed_person.getDate());

        person_name = detailed_person.getName();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.item_delete){
            AlertDialog delete = deleteOption();
            delete.show();
        }
        if(item.getItemId() == R.id.item_edit){
            ItemEdit();
        }
        return super.onOptionsItemSelected(item);
    }

    void DeletePerson(){
        Intent view_entries = new Intent(this, viewEntries.class);
        view_entries.putExtra("delete_name", person_name);
        startActivity(view_entries);
        finish();
    }

    void ItemEdit(){
        Intent edit_intent = new Intent(this, editPerson.class);
        edit_intent.putExtra("person", person);
        edit_intent.putExtra("position", position);
        startActivity(edit_intent);
        finish();
    }


    /*Used this for creating a popup menu when delete is selected from the options menu - Found on Jan.27, 2017
    http://stackoverflow.com/questions/11740311/android-confirmation-message-for-delete
     */
    private AlertDialog deleteOption() {
        AlertDialog delete = new AlertDialog.Builder(this)
                .setTitle("Delete?")
                .setMessage("Are you sure you want delete this person?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DeletePerson();
                    }
                })

                .setNegativeButton("NO!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
                return delete;
    }

}
