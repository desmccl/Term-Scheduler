package com.example.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.Database.Repository;
import com.example.myapplication.R;
import com.example.myapplication.entities.Assessments;
import com.example.myapplication.entities.Courses;
import com.example.myapplication.entities.Notes;
import com.example.myapplication.entities.Term;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CourseDetails extends AppCompatActivity {
    EditText editDate;
    EditText editEndDate;
    EditText editPhone;
    EditText editEmail;
    EditText courseInstructor;
    EditText courseStatus;
    EditText editName;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();
    String myFormat;
    SimpleDateFormat sdf;
    String name;
    String note;
    int id;
    int termId;
    int mentorID;

    String mentorName;
    String mentorPhone;
    String mentorEmail;
    String status;
    Courses courses;
    Courses currentCourse;
    int numAssessments;
    Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        editDate=findViewById(R.id.editdatestart);
        editEndDate=findViewById(R.id.editdateend);
        editName=findViewById(R.id.editCourseName);
        courseStatus=findViewById(R.id.edittextstatus);
        courseInstructor=findViewById(R.id.edittextmentorname);
        editPhone=findViewById(R.id.editTextPhone);
        editEmail=findViewById(R.id.editTextEmailAddress);
        String myFormat = "MM/dd/yy";
        sdf = new SimpleDateFormat(myFormat, Locale.US);
        editDate.setText(sdf.format(new Date()));
        editEndDate.setText(sdf.format(new Date()));
        id = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        note = getIntent().getStringExtra("note");
        termId = getIntent().getIntExtra("termID", -1);
        status = getIntent().getStringExtra("status");
        mentorName = getIntent().getStringExtra("mentor");
        mentorPhone = getIntent().getStringExtra("phone");
        mentorEmail = getIntent().getStringExtra("email");
        editName.setText(name);
        courseStatus.setText(status);
        courseInstructor.setText(mentorName);
        editPhone.setText(mentorPhone);
        editEmail.setText(mentorEmail);
        repository = new Repository(getApplication());

        RecyclerView recyclerView = findViewById(R.id.assessmentrecyclerview);
        repository = new Repository(getApplication());

        RecyclerView recyclerView1 = findViewById(R.id.notesrecyclerview);
        repository = new Repository(getApplication());

        final NotesAdapter notesAdapter = new NotesAdapter(this);
        recyclerView1.setAdapter(notesAdapter);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));

        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Assessments> filteredAssessments = new ArrayList<>();
        for (Assessments a : repository.getAllAssessments()) {
            if (a.getCourseID() == id) filteredAssessments.add(a);
        }

        List<Notes> filteredNotes = new ArrayList<>();
        for (Notes n : repository.getAllNotes()) {
            if (n.getCourseID() == id) filteredNotes.add(n);
        }

        notesAdapter.setNotes(filteredNotes);

        assessmentAdapter.setAssessments(filteredAssessments);

        Button button=findViewById(R.id.savebutton2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id == -1) {
                    courses = new Courses(0, editName.getText().toString(),termId, courseStatus.getText().toString(),0,courseInstructor.getText().toString(),
                            editPhone.getText().toString(), editEmail.getText().toString());
                    repository.insert(courses);
                }
                else {
                    courses = new Courses(id, editName.getText().toString(), termId, courseStatus.getText().toString(),mentorID,courseInstructor.getText().toString(),
                            editPhone.getText().toString(), editEmail.getText().toString());
                    repository.update(courses);
                }

            }
        });

        Button button1=findViewById(R.id.buttonaddnote);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CourseDetails.this, NotesDetails.class);
                startActivity(intent);
            }
        });

        editDate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Date date;
                String info=editDate.getText().toString();
                if(info.equals(" "))info="04/19/23";
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                new DatePickerDialog(CourseDetails.this, startDate, myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        startDate=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR,year);
                myCalendarStart.set(Calendar.MONTH,monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateLabelStart();
            }
        };
        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String info = editEndDate.getText().toString();
                if (info.equals(" ")) {
                    info = "04/19/23";
                }
                try {
                    myCalendarEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                new DatePickerDialog(CourseDetails.this, endDate, myCalendarEnd.get(Calendar.YEAR),
                        myCalendarEnd.get(Calendar.MONTH), myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, monthOfYear);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();
            }
        };

        FloatingActionButton fab=findViewById(R.id.floatingActionButton5);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CourseDetails.this, AssessmentDetails.class);
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Assessments> allAssessments = repository.getAllAssessments();
        RecyclerView recyclerView = findViewById(R.id.assessmentrecyclerview);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter.setAssessments(allAssessments);

        List<Notes> allNotes = repository.getAllNotes();
        RecyclerView recyclerView1 = findViewById(R.id.notesrecyclerview);
        final NotesAdapter notesAdapter = new NotesAdapter(this);
        recyclerView1.setAdapter(notesAdapter);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        notesAdapter.setNotes(allNotes);

    }

    private void updateLabelEnd() {
        myFormat = "MM/dd/yy";
        sdf = new SimpleDateFormat(myFormat, Locale.US);
        editEndDate.setText(sdf.format(myCalendarEnd.getTime()));
    }

    private void updateLabelStart() {
        String myFormat = "MM/dd/yy";
        sdf = new SimpleDateFormat(myFormat, Locale.US);
        editDate.setText(sdf.format(myCalendarStart.getTime()));
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.coursedetail, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return  true;
            case R.id.notifystart:
                String dateFromScreen=editDate.getText().toString();
                String myFormat = "MM/dd/yy";
                sdf = new SimpleDateFormat(myFormat, Locale.US);
                Date myDate=null;
                try{
                    myDate=sdf.parse(dateFromScreen);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                Long trigger=myDate.getTime();
                Intent intent=new Intent(CourseDetails.this, MyReceiver.class);
                intent.putExtra("Key", dateFromScreen + " " + name + " " + "Starts");
                PendingIntent sender=PendingIntent.getBroadcast(CourseDetails.this,MainActivity.numAlert++, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP,trigger,sender);
                return true;
            case R.id.notifyend:
                String endDateFromScreen = editEndDate.getText().toString();
                String endFormat = "MM/dd/yy";
                sdf = new SimpleDateFormat(endFormat, Locale.US);
                Date endDate = null;
                try {
                    endDate = sdf.parse(endDateFromScreen);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                Long endTrigger = endDate.getTime();
                Intent endIntent = new Intent(CourseDetails.this, MyReceiver.class);
                endIntent.putExtra("Key", endDateFromScreen +  " " + name + " " + "Ends");
                PendingIntent endSender = PendingIntent.getBroadcast(CourseDetails.this, MainActivity.numAlert++, endIntent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager endAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                endAlarmManager.set(AlarmManager.RTC_WAKEUP, endTrigger, endSender);
                return true;
            case R.id.deletecourse:
                for (Courses courses : repository.getAllCourses()) {
                    if (courses.getCourseID() == id) currentCourse = courses;
                }

                numAssessments = 0;
                for (Assessments assessments : repository.getAllAssessments()) {
                    if (assessments.getCourseID() == id) ++numAssessments;
                }

                if (numAssessments == 0) {
                    repository.delete(currentCourse);
                    Toast.makeText(CourseDetails.this, currentCourse.getCourseName() + " was deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(CourseDetails.this, "Can't delete a course with assessments", Toast.LENGTH_LONG).show();
                }
                return true;

        }
        return super.onOptionsItemSelected(item);



    }
}