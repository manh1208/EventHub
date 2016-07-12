package com.linhv.eventhub.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.linhv.eventhub.R;
import com.linhv.eventhub.listener.IEventTimeListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by ManhNV on 7/12/2016.
 */
public class EventTimeDialog extends Dialog {
    private Context mContext;
    private ViewHolder viewHolder;

    public EventTimeDialog(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_event_time);
        viewHolder = new ViewHolder();
        viewHolder.txtFromDate = (EditText) findViewById(R.id.txt_search_from_date);
        viewHolder.txtToDate = (EditText) findViewById(R.id.txt_search_to_date);
        viewHolder.rbgSearch = (RadioGroup) findViewById(R.id.rbg_search);
        viewHolder.btnSubmit = (Button) findViewById(R.id.btn_event_time_submit);
        setDobField(viewHolder.txtFromDate);
        setDobField(viewHolder.txtToDate);
        viewHolder.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = viewHolder.rbgSearch.getCheckedRadioButtonId();
                String s = "";
                if (id < 0) {
                    String from = viewHolder.txtFromDate.getText().toString().trim();
                    String to = viewHolder.txtToDate.getText().toString().trim();
                    if (from.length() <= 0 && to.length() <= 0) {
                        Toast.makeText(mContext, "Hãy chọn thời gian!", Toast.LENGTH_SHORT).show();
                    } else if (from.length() <= 0) {
                        Toast.makeText(mContext, "Hãy chọn ngày bắt đầu!", Toast.LENGTH_SHORT).show();
                    } else if (to.length() <= 0) {
                        Toast.makeText(mContext, "Hãy chọn ngày kết thúc!", Toast.LENGTH_SHORT).show();
                    } else {
                        s = "Từ " + from + " đến  " + to;

                    }
                } else {

                    RadioButton radiobutton = (RadioButton) findViewById(id);
                    s = radiobutton.getText().toString();
                }
                if (s.length() > 0) {
                    IEventTimeListener listener = (IEventTimeListener) mContext;
                    listener.getTime(s);
                    dismiss();
                }
            }
        });


    }

    private void setDobField(final EditText editText) {
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        Calendar newCalendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                editText.setText(dateFormatter.format(newDate.getTime()));
                viewHolder.rbgSearch.clearCheck();
            }

        }, (newCalendar.get(Calendar.YEAR)), (newCalendar.get(Calendar.MONTH)), (newCalendar.get(Calendar.DATE)));
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    datePickerDialog.show();
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            }
        });
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            }
        });


    }

    private class ViewHolder {
        EditText txtFromDate;
        EditText txtToDate;
        RadioGroup rbgSearch;
        Button btnSubmit;
    }
}
