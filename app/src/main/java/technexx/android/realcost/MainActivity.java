package technexx.android.realcost;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText rent_edit;
    private EditText bills_edit;
    private EditText misc_edit;
    private TextView total_edit;
    private String rent;
    private String bills;
    private String misc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rent_edit = findViewById(R.id.rent_edit);
        bills_edit = findViewById(R.id.bills_edit);
        misc_edit = findViewById(R.id.misc_edit);
        total_edit = findViewById(R.id.total_edit);

        rent_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                total_edit.setText(convert());
            }
        });

        bills_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                total_edit.setText(convert());
            }
        });

        misc_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                total_edit.setText(convert());
            }
        });

    }

    private String convert() {
        rent = rent_edit.getText().toString();
        bills = bills_edit.getText().toString();
        misc = misc_edit.getText().toString();

        int rentVal = 0;
        int billsVal = 0;
        int miscVal = 0;
        if (!rent.equals("")) {
            rentVal = Integer.parseInt(rent);
        }
        if (!bills.equals("")) {
            billsVal = Integer.parseInt(bills);
        }
        if (!misc.equals("")) {
            miscVal = Integer.parseInt(misc);
        }

        return String.valueOf(rentVal + billsVal + miscVal);
    }
}
