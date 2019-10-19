package com.example.lateng.tide;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class MainActivity extends AppCompatActivity {
    DatePickerDialog picker;
    TimePickerDialog pickerTime;
    //    TextView eTextRefDate, eTextRefTime;
//    TextView eTextClkDate, eTextClkTime;
    TextView eTextRefDate, eTextClkDate;
    Button btnGet, btnClear;
    TextView tvw, eTextTideState;
    String strTemp;
    SharedPreferences.Editor editor;
    SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    long DialogTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvw = findViewById(R.id.textView1);
        eTextTideState = findViewById(R.id.tideState);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        eTextRefDate = findViewById(R.id.txtRefDate);
        eTextRefDate.setInputType(InputType.TYPE_NULL);

        strTemp = pref.getString("strRefTideDate", "1/1/2019 00:00");
        eTextRefDate.setText(strTemp);

        UpdateCurrentTime();
        UpdateTide();


        eTextRefDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final View dialogView = View.inflate(MainActivity.this, R.layout.date_time_picker, null);
                final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();

                DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
                TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);
                timePicker.setIs24HourView(true);

                final Calendar cldr = Calendar.getInstance();
                Date date = null;
                try {
                    date = fmt.parse(String.valueOf(eTextRefDate.getText()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                cldr.setTime(date);

                int day = cldr.get(Calendar.DATE);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minute = cldr.get(Calendar.MINUTE);
                datePicker.init(year, month, day, null);
                timePicker.setCurrentHour(hour);
                timePicker.setCurrentMinute(minute);

                dialogView.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
                        TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);

                        Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                                datePicker.getMonth(),
                                datePicker.getDayOfMonth(),
                                timePicker.getCurrentHour(),
                                timePicker.getCurrentMinute());

                        DialogTime = calendar.getTimeInMillis();
                        Date d = new Date(DialogTime);
                        eTextRefDate.setText(fmt.format(d));

                        alertDialog.dismiss();
                    }
                });
                alertDialog.setView(dialogView);
                alertDialog.show();

            }
        });

        eTextRefDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                UpdateTide();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        eTextClkDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final View dialogView = View.inflate(MainActivity.this, R.layout.date_time_picker, null);
                final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();

                DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
                TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);
                timePicker.setIs24HourView(true);

                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DATE);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minute = cldr.get(Calendar.MINUTE);

                datePicker.init(year, month, day, null);
                timePicker.setCurrentHour(hour);
                timePicker.setCurrentMinute(minute);

                dialogView.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
                        TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);

                        Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                                datePicker.getMonth(),
                                datePicker.getDayOfMonth(),
                                timePicker.getCurrentHour(),
                                timePicker.getCurrentMinute());

                        DialogTime = calendar.getTimeInMillis();
                        Date d = new Date(DialogTime);
                        eTextClkDate.setText(fmt.format(d));

                        alertDialog.dismiss();
                    }
                });
                alertDialog.setView(dialogView);
                alertDialog.show();

            }
        });

        eTextClkDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                UpdateTide();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        btnGet = (Button) findViewById(R.id.button1);
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateTide();
            }
        });

//        btnClear = (Button) findViewById(R.id.button_clear);
//        btnClear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
//                editor = pref.edit();
//                editor.clear();
//                editor.commit();
//                Toast.makeText(getApplicationContext(), "Clear preference", Toast.LENGTH_LONG).show();
//            }
//        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        UpdateCurrentTime();
        UpdateTide();

        Toast.makeText(getApplicationContext(), "onResume", Toast.LENGTH_LONG).show();
    }

    private void UpdateCurrentTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String strDate = sdf.format(c.getTime());
//        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
//        String strTime = sdf1.format(c.getTime());

        eTextClkDate = findViewById(R.id.txtClkDate);
        eTextClkDate.setText(strDate);
    }

    private void UpdateTide() {

        try {
            // save tide reference
            editor.putString("strRefTideDate", String.valueOf(eTextRefDate.getText()));
            editor.commit();

            // calculate tide
            String strRefDateTime = String.valueOf(eTextRefDate.getText());
            String strClkDateTime = String.valueOf(eTextClkDate.getText());
            Date dateRef = fmt.parse(strRefDateTime);
            Date dateClk = fmt.parse(strClkDateTime);

            long deltaSec = (dateClk.getTime() - dateRef.getTime()) / 1000;
            long tidePeriod = 6 * 3600 + 12 * 60 + 23;  //6h 12m 23s
            long count = deltaSec / tidePeriod;
            if (count >= 0) count++;
            long diff = dateRef.getTime() + (count * tidePeriod) * 1000;

            Date d = new Date(diff);
            tvw.setText(fmt.format(d));

            String strTideState;
            if ((count % 2) == 0) strTideState = "High Tide";
            else strTideState = "Low Tide";
            eTextTideState.setText(strTideState);

//            Toast.makeText(getApplicationContext(),strRefDateTime+ "|" + strClkDateTime, Toast.LENGTH_LONG).show();
//            Toast.makeText(getApplicationContext(), Long.toString(dateRef.getTime()), Toast.LENGTH_LONG).show();
//            Toast.makeText(getApplicationContext(), Long.toString(dateClk.getTime()), Toast.LENGTH_LONG).show();
//            Toast.makeText(getApplicationContext(), Long.toString(deltaSec), Toast.LENGTH_LONG).show();

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
