package technexx.android.realcost;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText income_edit;
    private EditText rent_edit;
    private EditText bills_edit;
    private EditText misc_edit;
    private TextView total_edit;
    private String income;
    private String rent;
    private String bills;
    private String misc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        income_edit = findViewById(R.id.income_edit);
        rent_edit = findViewById(R.id.rent_edit);
        bills_edit = findViewById(R.id.bills_edit);
        misc_edit = findViewById(R.id.misc_edit);
        total_edit = findViewById(R.id.total_edit);

        income_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                convert();
            }
        });

        rent_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                convert();
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
                convert();
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
                convert();
            }
        });

    }

    private void convert() {
        income = income_edit.getText().toString();
        rent = rent_edit.getText().toString();
        bills = bills_edit.getText().toString();
        misc = misc_edit.getText().toString();

        int incomeVal = 0;
        int rentVal = 0;
        int billsVal = 0;
        int miscVal = 0;
        int postExpenses = 0;

        if(!income.equals("")) {
            incomeVal = Integer.parseInt(income);
        }
        if (!rent.equals("")) {
            rentVal = Integer.parseInt(rent);
        }
        if (!bills.equals("")) {
            billsVal = Integer.parseInt(bills);
        }
        if (!misc.equals("")) {
            miscVal = Integer.parseInt(misc);
        }

        if (!income.equals("") && !rent.equals("") && !bills.equals("") && !misc.equals("")) {
            postExpenses = (incomeVal - (rentVal + billsVal + miscVal));
            if (postExpenses <0) {
                postExpenses = 0;
            }
            total_edit.setText(String.valueOf(postExpenses));
        }

    }
}
