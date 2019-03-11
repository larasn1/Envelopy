package com.mamimu.envelopy;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class AddFormActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,TimePickerDialog.OnTimeSetListener{

    Button btnPick, btnTime;
    private static final int PICK_IMAGE = 100;

    Calendar c;
    DatePickerDialog dpd;
    RadioGroup rgReminder;
    RadioButton rbDay, rbWeek, rbMonth;
    TextView tvAt;
    Switch sReminder;
    ImageView img;
    Uri imageUri;
    int day, month, year;

    Spinner spin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_form);


        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        img = findViewById(R.id.addImage);
        sReminder = findViewById(R.id.sReminder);
        rgReminder = findViewById(R.id.radioGroup);
        tvAt = findViewById(R.id.tvAt);
        spin = findViewById(R.id.spinDay);
        btnTime = findViewById(R.id.btnTime);
        rbDay = findViewById(R.id.rbDay);
        rbWeek = findViewById(R.id.rbWeek);
        rbMonth = findViewById(R.id.rbMonth);

        rgReminder.setVisibility(View.GONE);
        tvAt.setVisibility(View.GONE);
        spin.setVisibility(View.GONE);
        btnTime.setVisibility(View.GONE);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        sReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sReminder.isChecked()==true){
                    rgReminder.setVisibility(View.VISIBLE);
                    rbDay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (rbDay.isChecked()) {
                                tvAt.setVisibility(View.VISIBLE);
                                btnTime.setVisibility(View.VISIBLE);
                            }else{
                                tvAt.setVisibility(View.GONE);
                                btnTime.setVisibility(View.GONE);
                            }
                        }
                    });

                    rbWeek.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (rbWeek.isChecked()) {
                                tvAt.setVisibility(View.VISIBLE);
                                spin.setVisibility(View.VISIBLE);
                                btnTime.setVisibility(View.VISIBLE);
                            }else{
                                tvAt.setVisibility(View.GONE);
                                spin.setVisibility(View.GONE);
                                btnTime.setVisibility(View.GONE);
                            }
                        }
                    });
                    rbMonth.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (rbMonth.isChecked()) {
                                tvAt.setVisibility(View.GONE);
                                spin.setVisibility(View.GONE);
                                btnTime.setVisibility(View.GONE);
                            }
                        }
                    });
                }else {
                    rgReminder.setVisibility(View.GONE);

                }

            }
        });



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.days, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        btnPick = (Button)findViewById(R.id.btnPick);

        c = Calendar.getInstance();

        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);



        btnPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dpd = new DatePickerDialog(AddFormActivity.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {
//                        mMonth = mMonth+1;
//                        btnPick.setText(mDay+"/"+mMonth+"/"+mYear);
//
//                    }
//                },day, month, year);
//                dpd.show();

                dpd = new DatePickerDialog(AddFormActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month=month+1;
                        btnPick.setText(dayOfMonth+"/"+month+"/"+year);
                        c.set(year,month,dayOfMonth);
                    }
                }, year, month, day);
                dpd.show();
            }
        });

    }

    public void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            img.setImageURI(imageUri);
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        btnTime = (Button) findViewById(R.id.btnTime);
        btnTime.setText(hourOfDay + ":" + minute);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
