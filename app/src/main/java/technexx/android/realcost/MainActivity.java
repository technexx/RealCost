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
    private EditText expenses_edit;
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
        expenses_edit = findViewById(R.id.expenses_edit);

        purchase_cost = findViewById(R.id.purchase_cost);
        actual_cost = findViewById(R.id.actual_cost);

        expenses_edit.addTextChangedListener(new TextWatcher() {
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

        int rentVal = 0;
        int billsVal = 0;
        int miscVal = 0;

        if(!income.equals("")) {
            incomeVal = Integer.parseInt(income);
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
            cost = Double.parseDouble(purchase);
        }

        pct = (double) incomeVal / postExpenses;
        realCost = (cost * pct);

        actual_cost.setText(String.format("%.2f", realCost));

    }
}
