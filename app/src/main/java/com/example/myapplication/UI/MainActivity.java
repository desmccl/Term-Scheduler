package com.example.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.Database.Repository;
import com.example.myapplication.R;
import com.example.myapplication.entities.Assessments;
import com.example.myapplication.entities.Courses;
import com.example.myapplication.entities.Notes;
import com.example.myapplication.entities.Term;

public class MainActivity extends AppCompatActivity {
    public static int numAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView text=findViewById(R.id.textView3);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, TermList.class);
                startActivity(intent);
            }
        });





    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.addSampleData:
                Term term1=new Term(0,"Term 1");
                Repository repository=new Repository(getApplication());
                repository.insert(term1);

                Courses courses=new Courses(0,"Course 1",1,"In Progress", 0,"Sally", "555-444-9874","sally@email.com");
                repository.insert(courses);

                Notes notes=new Notes(0,1, "note");
                repository.insert(notes);

                Assessments assessments =new Assessments(0,"Assessment 1",1);
                repository.insert(assessments);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}