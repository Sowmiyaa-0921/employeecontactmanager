package com.example.employeecontactmanager;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etName, etId;
    Spinner spDepartment;
    GridLayout gridLayout;

    String[] departments = {"Sales", "IT", "HR", "Marketing"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etId = findViewById(R.id.etId);
        spDepartment = findViewById(R.id.spDepartment);
        gridLayout = findViewById(R.id.gridLayout);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, departments);
        spDepartment.setAdapter(adapter);

        findViewById(R.id.btnSave).setOnClickListener(v -> addEmployee());

        findViewById(R.id.btnDial).setOnClickListener(v ->
                Toast.makeText(this, "Dialing Accountant...", Toast.LENGTH_SHORT).show());

        findViewById(R.id.btnEmail).setOnClickListener(v ->
                Toast.makeText(this, "Opening Email...", Toast.LENGTH_SHORT).show());
    }

    private void addEmployee() {

        String name = etName.getText().toString().trim();
        String id = etId.getText().toString().trim();
        String dept = spDepartment.getSelectedItem().toString();

        if(name.isEmpty() || id.isEmpty()){
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show();
            return;
        }

        LayoutInflater inflater = LayoutInflater.from(this);
        View card = inflater.inflate(R.layout.card_employee, null);

        ((TextView)card.findViewById(R.id.tvName)).setText(name);
        ((TextView)card.findViewById(R.id.tvId)).setText("ID: " + id);
        ((TextView)card.findViewById(R.id.tvDept)).setText("Department " + dept);

        Button btnCall = card.findViewById(R.id.btnCall);
        Button btnAction = card.findViewById(R.id.btnAction);

        if(dept.equals("IT") || dept.equals("Marketing")){
            btnAction.setText("Send Email");
        }

        btnCall.setOnClickListener(v -> showDialog(name));

        // Force equal 2-column width
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int cardWidth = (screenWidth / 2) - 40;

        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = cardWidth;
        params.setMargins(8,8,8,8);
        card.setLayoutParams(params);

        gridLayout.addView(card);

        etName.setText("");
        etId.setText("");
    }

    private void showDialog(String name){
        new AlertDialog.Builder(this)
                .setTitle("Confirm Call")
                .setMessage("Do you want to call " + name + "?")
                .setPositiveButton("Call", null)
                .setNegativeButton("Cancel", null)
                .show();
    }
}