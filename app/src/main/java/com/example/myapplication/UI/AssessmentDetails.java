package com.example.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Database.Repository;
import com.example.myapplication.R;
import com.example.myapplication.entities.Assessments;
import com.example.myapplication.entities.Courses;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AssessmentDetails extends AppCompatActivity {
    EditText editDate;
    EditText editEndDate;
    EditText editName;
    EditText editType;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();
    String myFormat;
    SimpleDateFormat sdf;
    String name;
    String type;
    int id;
    int courseID;
    Assessments assessments;
    Assessments currentAssessment;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        editDate=findViewById(R.id.editdatestart2);
        editEndDate=findViewById(R.id.editdateend2);
        editName=findViewById(R.id.editAssessmentName);
        editType=findViewById(R.id.editTextassessmenttype);
        String myFormat = "MM/dd/yy";
        sdf = new SimpleDateFormat(myFormat, Locale.US);
        editDate.setText(sdf.format(new Date()));
        editEndDate.setText(sdf.format(new Date()));
        id = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        type = getIntent().getStringExtra("type");
        courseID = getIntent().getIntExtra("courseID", -1);
        editName.setText(name);
        editType.setText(type);
        repository = new Repository(getApplication());

        Button button=findViewById(R.id.savebutton3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id == -1) {
                    assessments = new Assessments(0, editName.getText().toString(),courseID, editType.getText().toString());
                    repository.insert(assessments);
                }
                else {
                    assessments = new Assessments(id, editName.getText().toString(), courseID, editType.getText().toString());
                    repository.update(assessments);
                }
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
                new DatePickerDialog(AssessmentDetails.this, startDate, myCalendarStart.get(Calendar.YEAR),
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
                new DatePickerDialog(AssessmentDetails.this, endDate, myCalendarEnd.get(Calendar.YEAR),
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
        getMenuInflater().inflate(R.menu.assessmentdetail, menu);
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
                Intent intent=new Intent(AssessmentDetails.this, MyReceiver.class);
                intent.putExtra("Key", dateFromScreen +  " " + name + " " + "Starts");
                PendingIntent sender=PendingIntent.getBroadcast(AssessmentDetails.this,MainActivity.numAlert++, intent, PendingIntent.FLAG_IMMUTABLE);
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
                Intent endIntent = new Intent(AssessmentDetails.this, MyReceiver.class);
                endIntent.putExtra("Key", endDateFromScreen +  " " + name + " " + "Starts");
                PendingIntent endSender = PendingIntent.getBroadcast(AssessmentDetails.this, MainActivity.numAlert++, endIntent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager endAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                endAlarmManager.set(AlarmManager.RTC_WAKEUP, endTrigger, endSender);
                return true;
            case R.id.deleteassessment:
                for (Assessments assessment : repository.getAllAssessments()) {
                    if (assessment.getAssessmentID() == id) currentAssessment = assessment;
                }

                repository.delete(currentAssessment);
                Toast.makeText(AssessmentDetails.this, currentAssessment.getAssessmentName() + " was deleted", Toast.LENGTH_LONG).show();
                return true;

        }
        return super.onOptionsItemSelected(item);



    }
}