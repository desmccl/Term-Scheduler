package com.example.myapplication.UI;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Database.Repository;
import com.example.myapplication.R;
import com.example.myapplication.entities.Assessments;
import com.example.myapplication.entities.Notes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NotesDetails extends AppCompatActivity {
    EditText courseNote;
    String note;
    int id;
    int courseID;
    Repository repository;
    Notes notes;
    Notes currentNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        courseNote=findViewById(R.id.editCourseNote);
        id = getIntent().getIntExtra("id", -1);
        courseID = getIntent().getIntExtra("courseID", -1);
        note = getIntent().getStringExtra("name");
        courseNote.setText(note);

        repository = new Repository(getApplication());

        Button button=findViewById(R.id.savebutton4);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id == -1) {
                    notes = new Notes(0, courseID, courseNote.getText().toString());
                    repository.insert(notes);
                }
                else {
                    notes = new Notes(id, courseID, courseNote.getText().toString());
                    repository.update(notes);
                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notedetail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return  true;
            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,courseNote.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Course Note");
                sendIntent.setType("text/plain");
                Intent shareIntent=Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;
            case R.id.deletenote:
                for (Notes note : repository.getAllNotes()) {
                    if (note.getNotesID() == id) currentNotes = note;
                }

                repository.delete(currentNotes);
                Toast.makeText(NotesDetails.this,  "Note was deleted", Toast.LENGTH_LONG).show();
                return true;

        }
        return super.onOptionsItemSelected(item);



    }

}
