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
        final String storeGross = pref.getString("storeGross", "");
        final String storeNet = pref.getString("storeNet", "");

        double grossWage = 0;
        double netWage = 0;

        if (!storeGross.equals("")) {
            grossWage = Double.parseDouble(storeGross);
        }
        if (!storeNet.equals("")) {
            netWage = Double.parseDouble(storeNet);
        }

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

        hours_worked.setText(String.valueOf(hoursWorked));
        gross_wage.setText(String.format("%.2f", grossWage));
        net_wage.setText(String.format("%.2f", netWage));

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
                hourCalc();
                realPrice();
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
                hourCalc();
                realPrice();
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
                realPrice();
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
                popupWindow.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
                popupWindow.setHeight(598);
                popupWindow.showAtLocation(aboutLayout, Gravity.BOTTOM, 0, 0);
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
        String storeGross = pref.getString("storeGross", "");
        String storeNet = pref.getString("storeNet", "");

        double grossWage = 0;
        double netWage = 0;

        if (!storeGross.equals("")) {
            grossWage = Double.parseDouble(storeGross);
        }
        if (!storeNet.equals("")) {
            netWage = Double.parseDouble(storeNet);
        }

        String hours = hours_worked.getText().toString();
        if (!hours.equals("")) {
            hoursWorked = Integer.parseInt(hours);
        } else {
            hoursWorked = 0;
            gross_wage.setText("");
            net_wage.setText("");
        }

        if (hoursWorked >0) {
            grossWage = (double) incomeVal / hoursWorked;
            netWage = (double) postExpenses / hoursWorked;
            gross_wage.setText(String.format("%.2f", grossWage));
            net_wage.setText(String.format("%.2f", netWage));
            storeGross = String.valueOf(grossWage);
            storeNet = String.valueOf(netWage);
        }

        purchase = purchase_cost.getText().toString();

        editor.putInt("hoursWorked", hoursWorked);
        editor.putString("storeGross", storeGross);
        editor.putString("storeNet", storeNet);
        editor.apply();
    }

    private void realPrice() {
        SharedPreferences pref = getSharedPreferences("Pref", 0);
        SharedPreferences.Editor editor = pref.edit();

        int hoursWorked = pref.getInt("hoursWorked", 0);
        String storeGross = pref.getString("storeGross", "");
        String storeNet = pref.getString("storeNet", "");
        postExpenses = pref.getInt("postExpenses", 0);

        hours_cost.setTextColor(getResources().getColor(R.color.darkGreen));
        real_hours_cost.setTextColor(getResources().getColor(R.color.Red_Alt));
        actual_pct.setTextColor(getResources().getColor(R.color.Red_Alt));

        double grossWage = 0;
        double netWage = 0;

        if (!storeGross.equals("")) {
            grossWage = Double.parseDouble(storeGross);
        }
        if (!storeNet.equals("")) {
            netWage = Double.parseDouble(storeNet);
        }

        double cost = 0;
        String hours = "";
        double hours_needed = 0;
        double realHours_needed = 0;

        purchase = purchase_cost.getText().toString();

        if (!purchase.equals("")) {
            cost = Double.parseDouble(purchase);
        }

        hours_cost.setText("");
        real_hours_cost.setText("");
        actual_pct.setText("");

        if (postExpenses >0) {
            if (!purchase.equals("")) {
                percentage = (cost / postExpenses) * 100;
            } else {
                percentage = 0;
            }

            if (grossWage >0 && netWage >0) {
                hours_needed = (cost / grossWage);
                realHours_needed = (cost / netWage);
            }

            if (hours_needed >0) {
                hours_cost.setText(String.format("%.2f", hours_needed));
            }
            if (realHours_needed >0) {
                real_hours_cost.setText(String.format("%.2f", realHours_needed));
            }
            if (percentage >0) {
                actual_pct.setText(getString(R.string.two_part_ns, String.format("%.2f", percentage), getString(R.string.pct)));
            }
        } else if (!purchase.equals("")){
            hours_cost.setTextColor(getResources().getColor(R.color.black));
            real_hours_cost.setTextColor(getResources().getColor(R.color.black));
            actual_pct.setTextColor(getResources().getColor(R.color.black));
            hours_cost.setText(R.string.broke);
            real_hours_cost.setText(R.string.broke);
            actual_pct.setText(R.string.broke);
        } else {
            hours_cost.setText("");
            real_hours_cost.setText("");
            actual_pct.setText("");
        }

        if (cost > postExpenses && postExpenses > 0) {
            purchase_cost.setText(String.valueOf(postExpenses));
        }
    }
}