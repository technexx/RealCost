package technexx.android.realcost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText income_edit;
    private EditText expenses_edit;
    private TextView net_income;

    private String purchase;
    private EditText purchase_cost;
    private TextView hours_cost;
    private TextView real_hours_cost;

    private EditText hours_worked;
    private TextView gross_wage;
    private TextView net_wage;

    private TextView actual_pct;

    private int incomeVal;
    private int expenseVal;
    private int postExpenses;


    private int realCost;
    private double percentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = getSharedPreferences("Pref", 0);
        SharedPreferences.Editor editor = pref.edit();

        int savedIncome = pref.getInt("income", 0);
        int savedExpenses = pref.getInt("expenses", 0);
        final int postExpenses = pref.getInt("postExpenses", 0);
        final int hoursWorked = pref.getInt("hoursWorked", 0);
        final int grossWage = pref.getInt("grossWage", 0);
        final int netWage = pref.getInt("netWage", 0);

        Button about_button = findViewById(R.id.about);
        income_edit = findViewById(R.id.income_edit);
        expenses_edit = findViewById(R.id.expenses_edit);
        net_income = findViewById(R.id.net_income);

        hours_worked = findViewById(R.id.hours_worked);
        gross_wage = findViewById(R.id.gross_wage);
        net_wage = findViewById(R.id.net_wage);

        purchase_cost = findViewById(R.id.purchase_cost);
        hours_cost = findViewById(R.id.hours_cost);
        real_hours_cost = findViewById(R.id.real_hours_cost);
        actual_pct = findViewById(R.id.percentage);

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

        hours_worked.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                convert();
                hourCalc();
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

        final ConstraintLayout aboutLayout = findViewById(R.id.main_layout);

        LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View myView = layoutInflater.inflate(R.layout.about, null);

        final PopupWindow popupWindow = new PopupWindow(myView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);

        about_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.setWidth(800);
                popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
                popupWindow.showAtLocation(aboutLayout, Gravity.CENTER, 0, 0);
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

        editor.putInt("income", incomeVal);
        editor.putInt("expenses", expenseVal);
        editor.putInt("postExpenses", postExpenses);
        editor.apply();
    }

    private void hourCalc() {
        SharedPreferences pref = getSharedPreferences("Pref", 0);
        SharedPreferences.Editor editor = pref.edit();

        int hoursWorked = pref.getInt("hoursWorked", 0);
        int grossWage = pref.getInt("grossWage", 0);
        int netWage = pref.getInt("netWage", 0);

        String hours = hours_worked.getText().toString();
        if (!hours.equals("")) {
            hoursWorked = Integer.parseInt(hours);
        } else {
            hoursWorked = 1;
        }

        grossWage = incomeVal / hoursWorked;
        netWage = postExpenses / hoursWorked;

        gross_wage.setText(String.valueOf(grossWage));
        net_wage.setText(String.valueOf(netWage));

        purchase = purchase_cost.getText().toString();

        editor.putInt("hoursWorked", hoursWorked);
        editor.putInt("grossWage", grossWage);
        editor.putInt("netWage", netWage);
        editor.apply();
    }

    private void realPrice() {
        SharedPreferences pref = getSharedPreferences("Pref", 0);
        SharedPreferences.Editor editor = pref.edit();

        int hoursWorked = pref.getInt("hoursWorked", 0);
        int grossWage = pref.getInt("grossWage", 0);
        int netWage = pref.getInt("netWage", 0);

        double cost = 0;
        String hours = "";
        double hours_needed = 0;
        double realHours_needed = 0;

        purchase = purchase_cost.getText().toString();

        if (!purchase.equals("")) {
            cost = Double.parseDouble(purchase);
        }

        if (grossWage >0 && netWage >0) {
            hours_needed = (cost / grossWage);
            realHours_needed = (cost / netWage);
        }

        hours_cost.setText(String.valueOf(hours_needed));
        real_hours_cost.setText(String.valueOf(realHours_needed));

        if (postExpenses >0) {
            actual_pct.setText(getString(R.string.two_part_ns, String.format("%.2f", percentage), getString(R.string.pct)));
        } else {
            actual_pct.setText("");
        }
    }
}
