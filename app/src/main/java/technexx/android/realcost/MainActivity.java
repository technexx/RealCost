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

    private EditText purchase_cost;
    private TextView actual_cost;

    private int incomeVal;
    private int postExpenses;
    private double realCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        income_edit = findViewById(R.id.income_edit);
        rent_edit = findViewById(R.id.rent_edit);
        bills_edit = findViewById(R.id.bills_edit);
        misc_edit = findViewById(R.id.misc_edit);
        total_edit = findViewById(R.id.total_edit);

        purchase_cost = findViewById(R.id.purchase_cost);
        actual_cost = findViewById(R.id.actual_cost);

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

        purchase_cost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                realCost();
            }
        });

    }

    private void convert() {
        String income = income_edit.getText().toString();
        String rent = rent_edit.getText().toString();
        String bills = bills_edit.getText().toString();
        String misc = misc_edit.getText().toString();

        int rentVal = 0;
        int billsVal = 0;
        int miscVal = 0;

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

    private void realCost() {
        double pct = 0;
        double cost = 0;
        String purchase = purchase_cost.getText().toString();

        if (!purchase.equals("")) {
            cost = Integer.parseInt(purchase);
        }

        pct = (double) incomeVal / postExpenses;
        realCost = (cost * pct);

        actual_cost.setText(String.format("%.2f", realCost));

    }
}
