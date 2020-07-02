package technexx.android.realcost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText income_edit;
    private EditText expenses_edit;
    private TextView net_income;

    private String purchase;
    private EditText purchase_cost;
    private TextView actual_cost;

    private int incomeVal;
    private int expenseVal;
    private int postExpenses;
    private int realCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = getSharedPreferences("Pref", 0);
        SharedPreferences.Editor editor = pref.edit();

        int savedIncome = pref.getInt("income", 0);
        int savedExpenses = pref.getInt("expenses", 0);
        final int postExpenses = pref.getInt("postExpenses", 0);

        income_edit = findViewById(R.id.income_edit);
        expenses_edit = findViewById(R.id.expenses_edit);
        net_income = findViewById(R.id.net_income);

        purchase_cost = findViewById(R.id.purchase_cost);
        actual_cost = findViewById(R.id.actual_cost);

        income_edit.setText(String.valueOf(savedIncome));
        expenses_edit.setText(String.valueOf(savedExpenses));
        net_income.setText(String.valueOf(postExpenses));

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
                purchase = purchase_cost.getText().toString();
                if (!purchase.equals("")) {
                    realPrice();
                }
            }
        });

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
                purchase = purchase_cost.getText().toString();
                if (!purchase.equals("")) {
                    realPrice();
                }
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
                convert();
                realPrice();
            }
        });
    }

    private void convert() {
        SharedPreferences pref = getSharedPreferences("Pref", 0);
        SharedPreferences.Editor editor = pref.edit();

        incomeVal = pref.getInt("income", 0);
        expenseVal = pref.getInt("expenses", 0);
        postExpenses = pref.getInt("postExpenses", 0);

        String income = income_edit.getText().toString();
        String expense = expenses_edit.getText().toString();

        if (income.equals("")) {
            incomeVal = 0;
        }
        if (expense.equals("")) {
            expenseVal = 0;
        }

        if(!income.equals("") && !expense.equals("")) {
            incomeVal = Integer.parseInt(income);
            expenseVal = Integer.parseInt(expense);
        }

        postExpenses = (incomeVal - expenseVal);
        net_income.setText(String.valueOf(postExpenses));

        if (postExpenses >0) {
            net_income.setTextColor(getResources().getColor(R.color.darkGreen));
        } else if (postExpenses <0) {
            net_income.setTextColor(getResources().getColor(R.color.Red_Alt));
        } else {
            net_income.setTextColor(getResources().getColor(R.color.black));
        }

        editor.putInt("income", incomeVal);
        editor.putInt("expenses", expenseVal);
        editor.putInt("postExpenses", postExpenses);
        editor.apply();
    }

    private void realPrice() {
        double pct = 0;
        double cost = 0;

        purchase = purchase_cost.getText().toString();

        if (!purchase.equals("")) {
            cost = Double.parseDouble(purchase);
        }

        pct = (double) incomeVal / postExpenses;
        realCost = (int) (cost * pct);

        if (postExpenses >0) {
            actual_cost.setTextSize(22);
            actual_cost.setTypeface(null, Typeface.NORMAL);
            actual_cost.setText(String.valueOf(realCost));
            actual_cost.setTextColor(getResources().getColor(R.color.Red_Alt));
        } else {
            actual_cost.setTextSize(15);
            actual_cost.setTypeface(null, Typeface.BOLD);
            actual_cost.setText(R.string.broke);
            actual_cost.setTextColor(getResources().getColor(R.color.black));
        }
    }
}
